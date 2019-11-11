package com.example.viewmodel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FavouritiesDBHelper extends SQLiteOpenHelper {


    public FavouritiesDBHelper(@Nullable Context context) {
        super(context,DbSettings.DB_NAME ,null,DbSettings.DB_VERSION);
    }
    String createTable = "CREATE TABLE " + DbSettings.DBEntry.TABLE + " ( " +
            DbSettings.DBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DbSettings.DBEntry.COL_FAV_URL + " TEXT NOT NULL, " +
            DbSettings.DBEntry.COL_FAV_DATE + " INTEGER NOT NULL);";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbSettings.DBEntry.TABLE);
        onCreate(db);
    }
}
