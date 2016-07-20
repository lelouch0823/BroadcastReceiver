package com.bjw.studentmessagesystem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class StudentDataBaseHelper extends SQLiteOpenHelper{
    public static final String CREATE = "create table student("
            + "id integer primary key autoincrement,"
            + "name varchar,"
            + "age varchar,"
            + "sex varchar)";

    public StudentDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
