package com.bjw.studentmessagesystem.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.bjw.studentmessagesystem.db.dao.StudentDAO;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class DeleteFragment extends DialogFragment{
    private static final String SET_ARGMENT = "save";
    private static final String SET_ARGMENT_INT = "int";
    public static final String EXTRA_POSTION = "com.bjw.studentmessagesystem.postion";

    private String mName;

    private int mPostion;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mName = getArguments().getString(SET_ARGMENT);
        mPostion = getArguments().getInt(SET_ARGMENT_INT);
        return new AlertDialog.Builder(getActivity())
                .setTitle("提醒")
                .setMessage("确定要删除这条记录吗?")
                .setNegativeButton(android.R.string.cancel,null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int delete = StudentDAO.delete(mName);
                        Logger.d(mName);
                        if (delete > 0) {
                            //DataControlFragment.mStudents.remove(mPostion);
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                            sendResult(Activity.RESULT_OK,mPostion);
                        }
                    }
                }).create();
    }

    private void sendResult(int resultCode, int postion) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_POSTION, postion);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

    public static DeleteFragment newIstance(String name, int position) {
        Bundle args = new Bundle();
        args.putString(SET_ARGMENT,name);
        args.putInt(SET_ARGMENT_INT,position);
        DeleteFragment fragment = new DeleteFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
