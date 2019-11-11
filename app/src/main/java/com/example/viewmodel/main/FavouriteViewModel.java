package com.example.viewmodel.main;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.viewmodel.db.DbSettings;
import com.example.viewmodel.db.FavouritiesDBHelper;
import com.example.viewmodel.model.Favourites;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class FavouriteViewModel extends AndroidViewModel {
    List<Favourites> mFavs;
    FavouritiesDBHelper mFavHelper;
    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        mFavHelper = new FavouritiesDBHelper(application);

    }

    public List<Favourites> getFavs() {
        if(mFavs==null)
            mFavs=new ArrayList<>();
        createDummyList();
        LoadFavs();
        List<Favourites> Clone_mFavs=new ArrayList<>();
        for (int i = 0; i < mFavs.size(); i++) {
            Clone_mFavs.add(new Favourites(mFavs.get(i)));
        }
        return Clone_mFavs;
    }

    private void LoadFavs() {
        mFavs.clear();
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
      Cursor crs = db.query(DbSettings.DBEntry.TABLE,new String[]{ DbSettings.DBEntry._ID,
                DbSettings.DBEntry.COL_FAV_URL,
                DbSettings.DBEntry.COL_FAV_DATE},null,null,null,null,null);
    while (crs.moveToNext()){
        int idxId = crs.getColumnIndex(DbSettings.DBEntry._ID);
        int idxUrl = crs.getColumnIndex(DbSettings.DBEntry.COL_FAV_URL);
        int idxDate = crs.getColumnIndex(DbSettings.DBEntry.COL_FAV_DATE);
        mFavs.add(new Favourites(crs.getLong(idxId), crs.getString(idxUrl), crs.getLong(idxDate)));
    }
    crs.close();
    db.close();
    }
    public void removeFav(long id) {
        SQLiteDatabase db = mFavHelper.getWritableDatabase();
        db.delete(
                DbSettings.DBEntry.TABLE,
                DbSettings.DBEntry._ID + " = ?",
                new String[]{Long.toString(id)}
        );
        db.close();

        int index = -1;
        for (int i = 0; i < mFavs.size(); i++) {
            Favourites favourites = mFavs.get(i);
            if (favourites.mId == id) {
                index = i;
            }
        }
        if (index != -1) {
            mFavs.remove(index);
        }
    }

    private void createDummyList() {
        addFav("https://www.journaldev.com", (new Date()).getTime());
        addFav("https://www.medium.com", (new Date()).getTime());
        addFav("https://www.reddit.com", (new Date()).getTime());
        addFav("https://www.github.com", (new Date()).getTime());
        addFav("https://www.hackerrank.com", (new Date()).getTime());
        addFav("https://www.developers.android.com", (new Date()).getTime());
    }
    public Favourites addFav(String url, long date) {

        Log.d("API123", "addFav " + url);

        SQLiteDatabase db = mFavHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbSettings.DBEntry.COL_FAV_URL, url);
        values.put(DbSettings.DBEntry.COL_FAV_DATE, date);
        long id = db.insertWithOnConflict(DbSettings.DBEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        Favourites fav = new Favourites(id, url, date);
        mFavs.add(fav);
        return new Favourites(fav);
    }
}
