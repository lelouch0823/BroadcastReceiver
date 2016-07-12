package com.bjw.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {
    private MyDataBaseHelper helper;
    private Button button;
    private Button ibutton;
    private Button database;
    private Button updata;
    private Button delete;
    private Button search;
    private Button replace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.replace = (Button) findViewById(R.id.replace);
        this.search = (Button) findViewById(R.id.search);
        this.delete = (Button) findViewById(R.id.delete);
        this.updata = (Button) findViewById(R.id.updata);
        this.database = (Button) findViewById(R.id.database);
        this.ibutton = (Button) findViewById(R.id.i_button);
        button = (Button) findViewById(R.id.database);
        helper = new MyDataBaseHelper(this, "My.db", null, 1);
        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.beginTransaction();
                try {
                    db.delete("book", null, null);
                    /*if (true) {
                        throw new IOException();
                    }*/
                    ContentValues values = new ContentValues();
                    values.put("name","Game of thrones");
                    values.put("author","xixi");
                    values.put("pages",8000);
                    values.put("price",66.6);
                    db.insert("book", null, values);
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    db.endTransaction();
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.query("book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Logger.d(name + author + pages + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("book", "pages > ?", new String[]{"1000"});
            }
        });
        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 0.5);
                db.update("book", values, "name = ?", new String[]{"hehe"});
            }
        });
        ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "haha");
                values.put("author", "zhangsan");
                values.put("pages", 1000);
                values.put("price", 16.95);
                db.insert("book", null, values);
                values.clear();
                values.put("name", "hehe");
                values.put("author", "lisi");
                values.put("pages", 1600);
                values.put("price", 11.11);
                db.insert("book", null, values);
                Toast.makeText(MainActivity.this, "insert succse", Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.getWritableDatabase();
            }
        });
    }
}
