package com.bjw.studentmessagesystem.domain;

import java.util.UUID;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class Student {
    private String mName;
    private String mSex;
    private int mAge;
    private UUID mId;

    @Override
    public String toString() {
        return "Student{" +
                "mAge=" + mAge +
                ", mName='" + mName + '\'' +
                ", mSex='" + mSex + '\'' +
                '}';
    }

    public Student(String name, int age, String sex) {
        mAge = age;
        mName = name;
        mSex = sex;
    }

    public Student() {
        mId = UUID.randomUUID();
    }

    public UUID getid() {
        return mId;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }


}
