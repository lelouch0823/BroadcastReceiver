package com.bjw.providertest;

import android.app.Notification;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {
    private String newId;
    private android.widget.Button adddata;
    private android.widget.Button deletedata;
    private android.widget.Button querydata;
    private android.widget.Button updatedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.updatedata = (Button) findViewById(R.id.update_data);
        this.querydata = (Button) findViewById(R.id.query_data);
        this.deletedata = (Button) findViewById(R.id.delete_data);
        this.adddata = (Button) findViewById(R.id.add_data);

        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification notification = new Notification()
                Uri uri = Uri.parse("content://com.bjw.databasetest.provider/book/" + newId);
                ContentValues values = new ContentValues();
                values.put("name","你好好");
                values.put("pages","1500");
                values.put("price","150.5");
                values.put("author","大发放");
                getContentResolver().update(uri, values, null, null);
            }
        });
        querydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.bjw.databasetest.provider/book/");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Logger.d(name + author + pages + pages);
                    }
                    cursor.close();
                }
            }
        });
        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.bjw.databasetest.provider/book/");
                ContentValues values = new ContentValues();
                values.put("name","我是谁");
                values.put("author","东方");
                values.put("price",17.2);
                values.put("pages",1888);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
            }
        });
        deletedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.bjw.databasetest.provider/book/" + newId);
                getContentResolver().delete(uri, null, null);
            }
        });
    }
}
