package com.bjw.criminallntent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bjw.criminallntent.db.CrimeDbSchema.CrimeTable;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class CrimeDataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      sqLiteDatabase.execSQL("create table " + CrimeTable.NAME + "(" +
                                                    "_id integer primary key autoincrement, " +
                                                    CrimeTable.Cols.UUID + ", " +
                                                    CrimeTable.Cols.TITLE + ", " +
                                                    CrimeTable.Cols.DATE + ", " +
                                                    CrimeTable.Cols.SOLVED + ", "+
                                                    CrimeTable.Cols.SUSPECT + ")");
        //sqLiteDatabase.execSQL(CREATE_BOOK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
