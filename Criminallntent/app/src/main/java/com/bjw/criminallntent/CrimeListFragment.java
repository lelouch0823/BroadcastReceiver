package com.bjw.criminallntent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class CrimeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;
    private static final int REQUEST_CRIME = 1;
    private static final String EXTRA_CRIME_ID = "this is uuid";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //关联布局文件
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        //关联RecyclerView控件
        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //将RecyclerView设为垂直列表形式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //调用方法创建视图
        UpdateUI();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CRIME) {
            if (resultCode == CrimePagerActivity.RESULT_OK) {
                int extra = data.getIntExtra(CrimePagerActivity.CRIME_POSTION, 0);
                mAdapter.notifyItemChanged(extra);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在回到栈顶显示时刷新视图
        UpdateUI();
    }

    private void UpdateUI() {
        //获取CrimeLab对象
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        //获取Crime对象集合
        List<Crime> crimes = crimeLab.getCrimes();
        //判断Adapter是否为空
        if (mAdapter == null) {
            //创建Adapter对象
            mAdapter = new CrimeAdapter(crimes);
            //向RecyclerView设置Adapter
            mRecyclerView.setAdapter(mAdapter);
        } else {

        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        public void bindCrime(Crime crime) {
            //获取单个crime对象的属性并对控件进行UI刷新
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate());
            mSolvedCheckBox.setChecked(crime.isSolved());
        }

        public CrimeHolder(View itemView) {
            super(itemView);
            //初始化itemview内控件
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_solved_check_box);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //设置点击事件
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            //startActivity(intent);
            startActivityForResult(intent,REQUEST_CRIME);
        }



    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter (List<Crime> crimes) {
            //将创建CrimeAdapter对象是传入的list赋值给成员变量
            mCrimes = crimes;
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //加载布局
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime,parent,false);
            //创建ViewHolder
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            //将对象的数据绑定
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
