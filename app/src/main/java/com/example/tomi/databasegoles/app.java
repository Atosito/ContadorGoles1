package com.example.tomi.databasegoles;

import android.app.Application;
import android.content.Context;

import com.example.tomi.databasegoles.DBHandler.DBHandler;
import com.example.tomi.databasegoles.DBHandler.DatabaseManager;

/**
 * Created by TOMI on 04/05/2017.
 */

public class  app extends Application {
    private static Context context;
    private static DBHandler dbHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHandler(this);
        DatabaseManager.initializeInstance(dbHelper);

    }

    public static Context getContext(){
        return context;
    }

}