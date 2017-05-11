package com.example.tomi.databasegoles.DBHandler;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.tomi.databasegoles.Data.Partido;
import com.example.tomi.databasegoles.Data.Temporada;

/**
 * Created by TOMI on 04/05/2017.
 */


public class PartidoHandler {

    private Partido partido;

    public PartidoHandler(){

    }
    public static String createTable() {
        return "CREATE TABLE " + Partido.TABLE_NAME + "("
                + Partido.KEY_ID + " INTEGER PRIMARY KEY ,"
                + Partido.KEY_AÑO + " TEXT ,"
                + Partido.KEY_RIVAL + " TEXT ,"
                + Partido.KEY_COMPETENCIA + " TEXT ,"
                + Partido.KEY_GOLES + " TEXT )";

    }
    public int insert(Partido partido) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int courseId;
        ContentValues values = new ContentValues();
        values.put(Partido.KEY_ID, partido.get_id());
        values.put(Partido.KEY_AÑO, partido.getAño());
        values.put(Partido.KEY_RIVAL, partido.getRival());
        values.put(Partido.KEY_COMPETENCIA, partido.getCompetencia());
        values.put(Partido.KEY_GOLES, partido.getGoles());
        courseId = (int) db.insert(Partido.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return courseId;
    }
    public int delete(){
        return 2;
    }

}