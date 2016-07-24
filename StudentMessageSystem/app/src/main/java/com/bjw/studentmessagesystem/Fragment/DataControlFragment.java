package com.bjw.studentmessagesystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    public static List<Student> mStudents;
    private MyAdapter mMyAdapter;
    private StudentDAO mDao;

    private static final int REQUEST_DATE = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_control_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            int delete = data.getIntExtra(DeleteFragment.EXTRA_POSTION, 0);
            mStudents.remove(delete);
            updateUI();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDao = new StudentDAO(getActivity());
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
        updateUI();
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

    public void updateUI() {
        mStudents = mDao.findAll();
        if (mMyAdapter == null) {
            mMyAdapter = new MyAdapter(getActivity(), R.layout.student_list_item, mStudents);
            mStLv.setAdapter(mMyAdapter);
        }
        mMyAdapter.notifyDataSetChanged();
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
                    updateUI();
                    return;
                }
                StudentDAO dao = new StudentDAO(getActivity());
                    dao.add(name, age, sex);
                updateUI();
                break;
            case R.id.show_database_bt:
                Intent intent = new Intent(getActivity(), StudentListActivity.class);
                startActivity(intent);
                break;
        }
    }

    class MyAdapter extends ArrayAdapter<Student> {
        private int mReresourceId;

        private Student mStudent;

        private int mPosition;

        public MyAdapter(Context context, int resource, List<Student> objects) {
            super(context, resource, objects);
            mReresourceId = resource;
            mStudents = objects;
        }

        @Override
        public int getCount() {
            return mStudents.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            mStudent = mStudents.get(position);
            mPosition = position;
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
            final String name = mStudent.getName();
            int age = mStudent.getAge();
            String sex = mStudent.getSex();
            holder.mTextView.setText("姓名:" + name +"年龄" + "性别:" + sex);
            if ("男".equals(mStudent.getSex())) {
                holder.mImageView.setImageResource(R.drawable.nan);
            } else {
                holder.mImageView.setImageResource(R.drawable.nv);
            }
            holder.mDeleteView.setImageResource(R.drawable.ic_delete_forever_black_24dp);
            holder.mDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = getFragmentManager();
                    DeleteFragment fragment = DeleteFragment.newIstance(name,mPosition);
                    fragment.setTargetFragment(DataControlFragment.this,REQUEST_DATE);
                    fragment.show(manager,"aa");
                }
            });
            return view;
        }

        class ViewHolder {
            TextView mTextView;
            ImageView mImageView;
            ImageView mDeleteView;
        }
    }
}
