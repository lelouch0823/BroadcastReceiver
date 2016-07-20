package com.bjw.studentmessagesystem.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bjw.studentmessagesystem.db.StudentDataBaseHelper;
import com.bjw.studentmessagesystem.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class StudentDAO {
    private StudentDataBaseHelper mHelper;

    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String SEX = "sex";
    public static final String TABLE_NAME = "student";


    public StudentDAO(Context context) {
        mHelper = new StudentDataBaseHelper(context, "My.db", null, 1);
    }

/*    public void add(String name,String age,String sex) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert into student (name,age,sex) values (?,?,?)", new Object[]{name,age,sex});
        db.close();
    }*/

    public long add(String name, String age, String sex) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(AGE, age);
        values.put(SEX, sex);
        long insert = db.insert(TABLE_NAME, null, values);
        db.close();
        return insert;
    }

    public int delete(String name) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int delete = db.delete(TABLE_NAME, NAME + "= ?", new String[]{name});
        return delete;

    }

    public int update(String name) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AGE,1);
        int update = db.update(TABLE_NAME, values, NAME + "=?", new String[]{name});
        return update;
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{NAME, AGE, SEX},null,null,null,null,null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int age = Integer.parseInt(cursor.getString(1));
            String sex = cursor.getString(2);
            Student student = new Student(name, age, sex);
            students.add(student);
        }
        cursor.close();
        db.close();
        return students;
    }
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name, age, sex from student", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int age = Integer.parseInt(cursor.getString(1));
            String sex = cursor.getString(2);
            Student student = new Student(name, age, sex);
            students.add(student);
        }
        cursor.close();
        db.close();
        return students;
    }
}
