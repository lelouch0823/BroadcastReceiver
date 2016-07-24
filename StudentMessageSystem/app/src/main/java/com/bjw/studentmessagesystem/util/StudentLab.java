package com.bjw.studentmessagesystem.util;

import android.content.Context;

import com.bjw.studentmessagesystem.db.dao.StudentDAO;
import com.bjw.studentmessagesystem.domain.Student;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class StudentLab {
    private static StudentLab sStudentLab;
    private List<Student> mStudents;

    private StudentLab(Context context) {
        StudentDAO dao = new StudentDAO(context);
        mStudents = dao.getAll();
    }

    public Student getStudent(UUID uuid) {
        for (Student student : mStudents) {
            if (student.getid().equals(uuid)) {
                return student;
            }
        }
        return null;
    }

    public static StudentLab getStudentLab(Context context) {
        //if (sStudentLab == null) {
            sStudentLab = new StudentLab(context);
        //}
        return sStudentLab;
    }

    public List<Student> getStudents() {
        return mStudents;
    }

    public static StudentLab getStudentLab() {
        return sStudentLab;
    }
}
