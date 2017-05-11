package com.example.tomi.databasegoles.DBHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tomi.databasegoles.Data.Partido;
import com.example.tomi.databasegoles.Data.Temporada;

/**
 * Created by TOMI on 03/05/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBGoles2";
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(TemporadaHandler.createTable());

        sqLiteDatabase.execSQL(PartidoHandler.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + Temporada.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + Partido.TABLE_NAME);
        onCreate(db);
    }

}
