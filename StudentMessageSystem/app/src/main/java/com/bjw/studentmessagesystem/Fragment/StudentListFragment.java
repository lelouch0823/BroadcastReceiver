package com.bjw.studentmessagesystem.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjw.studentmessagesystem.R;
import com.bjw.studentmessagesystem.activity.DataControlActivity;
import com.bjw.studentmessagesystem.db.dao.StudentDAO;
import com.bjw.studentmessagesystem.domain.Student;

import java.util.List;

/**
 *  需求在页面recyclerview列表中显示数据库内容
 *  1.关联资源文件
 *  2.创建初始化recyclerview.adapter
 *  3.创建初始化recyclerview.hodler
 *  4.设置显示出recyclerview
 */


public class StudentListFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private StudentDAO mDao;

    private List<Student> mStudents;

    private RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.student_list_menu,menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDao = new StudentDAO(getActivity());
        mStudents = mDao.findAll();
        updateUi();
        return view;
    }

    private void updateUi() {
        if (mAdapter == null) {
            mAdapter = new StudentAdapter(mStudents);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mStudent;
        private ImageView mItemImage;
        private ImageView mDeleteImage;
        public StudentHolder(View itemView) {
            super(itemView);
            //mStudent = (TextView) itemView.findViewById(R.id.item_tv);
            mStudent = (TextView) itemView.findViewById(R.id.item_tv);
            mItemImage = (ImageView) itemView.findViewById(R.id.item_image);
            mDeleteImage = (ImageView) itemView.findViewById(R.id.delete_image);
            itemView.setOnClickListener(this);
            mDeleteImage.setOnClickListener(this);
        }

        public void bindStudent(Student student, final int position) {
            String name = student.getName();
            int age = student.getAge();
            String sex = student.getSex();
            if ("男".equals(sex)) {
                mItemImage.setImageResource(R.drawable.nan);
            } else {
                mItemImage.setImageResource(R.drawable.nv);
            }
            mDeleteImage.setImageResource(R.drawable.ic_delete_forever_black_24dp);
            mStudent.setText("姓名:" + name +"年龄" + "性别:" + sex);
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("提醒");
                    builder.setMessage("是否删除这条学生信息?");
                    builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Student student = mStudents.get(position);
                            String name = student.getName();
                            // 从数据库删除数据.
                            mDao.delete(name);
                            mStudents.remove(position);
                            Toast.makeText(getActivity(), "数据被删除了", Toast.LENGTH_SHORT).show();
                            updateUi();
                        }
                    }).show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(),DataControlActivity.class);
            startActivity(intent);
        }
    }

    class StudentAdapter extends RecyclerView.Adapter<StudentHolder> {
        private List<Student> mStudents;

        @Override
        public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.student_list_item, parent,false);
            StudentHolder holder = new StudentHolder(view);
            return holder;
        }

        public StudentAdapter( List<Student> students) {
            super();
            mStudents = students;
        }

        @Override
        public void onBindViewHolder(StudentHolder holder, final int position) {
            Student student = mStudents.get(position);
            holder.bindStudent(student,position);
        }
        @Override
        public int getItemCount() {
            return mStudents.size();
        }
    }
}
