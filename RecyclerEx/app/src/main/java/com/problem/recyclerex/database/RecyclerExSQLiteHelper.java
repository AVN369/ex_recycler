package com.problem.recyclerex.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by avin on 03/03/16.
 */
public class RecyclerExSQLiteHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private final Context mContext;
    private static String DB_NAME = "recyclerex.sql";
    private static RecyclerExSQLiteHelper mSingletonRecyclerExSQLiteHelper;

    public RecyclerExSQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.mContext = context;
    }

    public static SQLiteDatabase getDatabase(Context aContext) throws Exception {
        SQLiteDatabase db = getInstance(aContext)
                .getWritableDatabase();
        if (db != null) {
            return db;
        } else {
            throw new Exception("Database is null");
        }
    }

    public static RecyclerExSQLiteHelper getInstance(
            Context aContext) {
        if (mSingletonRecyclerExSQLiteHelper == null)
            synchronized (RecyclerExSQLiteHelper.class) {
                if (mSingletonRecyclerExSQLiteHelper == null)
                    mSingletonRecyclerExSQLiteHelper = new RecyclerExSQLiteHelper(
                            aContext.getApplicationContext());
            }
        return mSingletonRecyclerExSQLiteHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                " CREATE TABLE IF NOT EXISTS " + DatabaseConstants.Tables.IMAGE_LIST + " (" +
                        DatabaseConstants.ImageList.ID + " VARCHAR(100), " +
                        DatabaseConstants.ImageList.URI + " VARCHAR(500), " +
                        DatabaseConstants.ImageList.TITLE + " VARCHAR(500), " +
                        "UNIQUE (" + DatabaseConstants.ImageList.ID + ") ON CONFLICT IGNORE" +
                        ") "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

