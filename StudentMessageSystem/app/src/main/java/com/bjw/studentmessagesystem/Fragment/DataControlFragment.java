package com.bjw.studentmessagesystem.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bjw.studentmessagesystem.R;
import com.bjw.studentmessagesystem.activity.StudentListActivity;
import com.bjw.studentmessagesystem.db.dao.StudentDAO;
import com.bjw.studentmessagesystem.domain.Student;
import com.bjw.studentmessagesystem.util.StudentLab;

import java.util.List;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class DataControlFragment extends Fragment implements View.OnClickListener {
    private EditText mEtName;
    private EditText mEtAge;
    private Button mUpdateDatabaseBt;
    private Button mShowDatabaseBt;
    private RadioGroup mSexRg;
    private ListView mStLv;
    private List<Student> mStudents;
    private MyAdapter mMyAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_control_fragment, container, false);
        mStudents = StudentLab.getStudentLab(getActivity()).getStudents();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mEtName = (EditText) view.findViewById(R.id.et_name);
        mEtAge = (EditText) view.findViewById(R.id.et_age);
        mUpdateDatabaseBt = (Button) view.findViewById(R.id.update_database_bt);
        mShowDatabaseBt = (Button) view.findViewById(R.id.show_database_bt);

        mUpdateDatabaseBt.setOnClickListener(this);
        mShowDatabaseBt.setOnClickListener(this);
        mSexRg = (RadioGroup) view.findViewById(R.id.sex_rg);
        mSexRg.setOnClickListener(this);
        mStLv = (ListView) view.findViewById(R.id.st_lv);
        if (mMyAdapter == null) {
            mMyAdapter = new MyAdapter(getActivity(), R.layout.student_list_item, mStudents);
            mStLv.setAdapter(mMyAdapter);
        }
        mMyAdapter.notifyDataSetChanged();
        /*mStLv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mStudents.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView textView = new TextView(getActivity());
                textView.setText(mStudents.get(i).toString());
                return textView;
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_database_bt:
                String name = mEtName.getText().toString().trim();
                String age = mEtAge.getText().toString().trim();
                String sex = "男";
                if (mSexRg.getCheckedRadioButtonId() == R.id.female_rb) {
                    sex = "女";
                }
                //判断输入框是否为空
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age)) {
                    Toast.makeText(getActivity(), "姓名或年龄不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                StudentDAO dao = new StudentDAO(getActivity());
                for (int i = 0; i < 5000; i++) {
                    dao.add(name, age, sex);
                }
                break;
            case R.id.show_database_bt:
                Intent intent = new Intent(getActivity(), StudentListActivity.class);
                startActivity(intent);
                break;
        }
    }

    class MyAdapter extends ArrayAdapter<Student> {
        private int mReresourceId;

        public MyAdapter(Context context, int resource, List<Student> objects) {
            super(context, resource, objects);
            mReresourceId = resource;
            mStudents = objects;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Student student = mStudents.get(position);
            ViewHolder holder;
            View view;
            if (convertView == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(getContext()).inflate(mReresourceId, null);
                holder.mTextView = (TextView) view.findViewById(R.id.item_tv);
                holder.mImageView = (ImageView) view.findViewById(R.id.item_image);
                holder.mDeleteView = (ImageView) view.findViewById(R.id.delete_image);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            String name = student.getName();
            int age = student.getAge();
            String sex = student.getSex();
            holder.mTextView.setText("姓名:" + name +"年龄" + "性别:" + sex);
            if ("男".equals(student.getSex())) {
                holder.mImageView.setImageResource(R.drawable.nan);
            } else {
                holder.mImageView.setImageResource(R.drawable.nv);
            }
            holder.mDeleteView.setImageResource(R.drawable.ic_delete_forever_black_24dp);
            return view;
        }

        class ViewHolder {
            TextView mTextView;
            ImageView mImageView;
            ImageView mDeleteView;
        }
    }
}
