package com.example.tomi.databasegoles.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.HideReturnsTransformationMethod;

import com.example.tomi.databasegoles.Data.Partido;
import com.example.tomi.databasegoles.Data.Temporada;
import com.example.tomi.databasegoles.MainActivity;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TOMI on 03/05/2017.
 */

public class TemporadaHandler {

    private Temporada temporada;

    public TemporadaHandler(){

    }
    public static String createTable() {
        return "CREATE TABLE " + Temporada.TABLE_NAME + "("
                + Temporada.KEY_ID + " INTEGER PRIMARY KEY ,"
                + Temporada.KEY_AÑO + " TEXT NOT NULL UNIQUE,"
                + Temporada.KEY_TOTAL_GOLES + " TEXT ,"
                + Temporada.KEY_TOTAL_PARTIDOS + " TEXT ,"
                + Temporada.KEY_META + " TEXT )";

    }
    public int insert(Temporada temporada) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int courseId;
        ContentValues values = new ContentValues();
        values.put(Temporada.KEY_ID, temporada.get_id());
        values.put(Temporada.KEY_TOTAL_GOLES, "0");
        values.put(Temporada.KEY_TOTAL_PARTIDOS, "0");
        values.put(Temporada.KEY_AÑO, temporada.getAño());
        values.put(Temporada.KEY_META, temporada.getMeta());
        courseId = (int) db.insert(Temporada.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return courseId;
    }


    public int edit(Temporada temporada){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int courseId;
        ContentValues values = new ContentValues();
        values.put(Temporada.KEY_AÑO, temporada.getAño());
        values.put(Temporada.KEY_META, temporada.getMeta());
        courseId = (int) db.update(Temporada.TABLE_NAME, values, "_id = ?",new String[]{temporada.get_id()});
        DatabaseManager.getInstance().closeDatabase();

        return courseId;
    }
    public int delete(Temporada temporada){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int courseId;
        courseId = (int) db.delete(Temporada.TABLE_NAME, "_id =?", new String[]{temporada.get_id()});
        DatabaseManager.getInstance().closeDatabase();

        return courseId;

    }
    public int updateTotales(Temporada temporada){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int courseId;
        ContentValues values = new ContentValues();
        values.put(Temporada.KEY_TOTAL_GOLES, temporada.getTotalgoles());
        values.put(Temporada.KEY_TOTAL_PARTIDOS, temporada.getTotalpartidos());
        courseId = (int) db.update(Temporada.TABLE_NAME, values, "_id = ?",new String[]{temporada.get_id()});
        DatabaseManager.getInstance().closeDatabase();

        return courseId;
    }

}
