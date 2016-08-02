package com.bjw.providerdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MyProvider extends ContentProvider {
    public static final String TAG = "hello";
    public static final String AUTHORITY = "com.bjw.ProviderDemo.provider";
    private static UriMatcher sMatcher;
    private static MyDatabaseHepler sHepler;

    private static final int SUCCESS = 8;

    static {
        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sMatcher.addURI(AUTHORITY, "book", SUCCESS);
    }

    @Override
    public boolean onCreate() {
        sHepler = new MyDatabaseHepler(getContext(), "My.db", null, 1);
        Log.d(TAG, "onCreate: hh");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        if (sMatcher.match(uri) == SUCCESS) {
            Log.d(TAG, "query: ok");
            SQLiteDatabase db = sHepler.getWritableDatabase();
            Cursor c = db.query("book", strings, s, strings1, null, null, s1);
            if (c != null) {
                return c;
            }
        }else{
            throw new IllegalArgumentException("口令 不正确,滚犊子");
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (sMatcher.match(uri) == SUCCESS) {
            Log.d(TAG, "insert: ok");
            SQLiteDatabase db = sHepler.getWritableDatabase();
            db.insert("book", null, contentValues);
        }else{
            throw new IllegalArgumentException("口令 不正确,滚犊子");
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        if (sMatcher.match(uri) == SUCCESS) {
            Log.d(TAG, "delete: ok");
            SQLiteDatabase db = sHepler.getWritableDatabase();
            db.delete("book", s, strings);
        }else{
            throw new IllegalArgumentException("口令 不正确,滚犊子");
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        if (sMatcher.match(uri) == SUCCESS) {
            Log.d(TAG, "update: ok");
            SQLiteDatabase db = sHepler.getWritableDatabase();
            db.update("book", contentValues, s, strings);
        }else{
            throw new IllegalArgumentException("口令 不正确,滚犊子");
        }
        return 0;
    }
}
