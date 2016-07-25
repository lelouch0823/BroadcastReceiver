package com.bjw.criminallntent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.bjw.criminallntent.Crime;
import com.bjw.criminallntent.CrimeLab;
import com.bjw.criminallntent.R;
import com.bjw.criminallntent.activity.TimePickerActivity;
import com.bjw.criminallntent.fragment.dialog.DatePickerFragment;
import com.bjw.criminallntent.fragment.dialog.TimePickerDialog;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class CrimeFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CONTACT = 2;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mSetTimeButton;
    private Button mSetTimeButton2;
    private CheckBox mSolvedCheckBox;
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private Button mCrimeSuspect;
    private Button mCrimeReport;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }
        String report = getString(R.string.crime_report,
                mCrime.getTitle(), dateString, solvedString, suspect);
        return report;
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initView(View view) {
        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mTitleField.setText(mCrime.getTitle());
        mDateButton = (Button) view.findViewById(R.id.crime_date);
        //mDateButton.setText(updateDate());
        mDateButton.setText(mCrime.getDate().toString());
        mSetTimeButton = (Button) view.findViewById(R.id.crime_time);
        mSetTimeButton.setOnClickListener(this);
        mSetTimeButton2 = (Button) view.findViewById(R.id.crime_time2);
        mSetTimeButton2.setOnClickListener(this);
        mDateButton.setOnClickListener(this);
        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnClickListener(this);
        mCrimeSuspect = (Button) view.findViewById(R.id.crime_suspect);
        mCrimeSuspect.setOnClickListener(this);
        if (mCrime.getSuspect() != null) {
            mCrimeSuspect.setText(mCrime.getSuspect());
        }
        mCrimeReport = (Button) view.findViewById(R.id.crime_report);
        mCrimeReport.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String title = mTitleField.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getContext(), "title不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateButton.setText(updateDate());
        } else if (requestCode == REQUEST_TIME) {

        } else if (requestCode == REQUEST_CONTACT) {

        }
    }

    private String updateDate() {
        String dateFormat = "EE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();
        return dateString;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crime_date:
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
                break;
            case R.id.crime_time:
                Intent intent = TimePickerActivity
                        .newIntent(getActivity(), mCrime.getDate());
                startActivityForResult(intent, REQUEST_TIME);
                break;
            case R.id.crime_time2:
                FragmentManager manager1 = getFragmentManager();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(mCrime.getDate());
                timePickerDialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                timePickerDialog.show(manager1, DIALOG_TIME);
                break;
            case R.id.crime_suspect:
                Intent pickContact = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pickContact,REQUEST_CONTACT);
                break;
            case R.id.crime_report:
                Intent reportIntent = new Intent(Intent.ACTION_SEND);
                reportIntent.setType("text/plain");
                reportIntent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                reportIntent.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_repoty_subject));
                reportIntent = Intent.createChooser(reportIntent, getString(R.string.send_report));
                startActivity(reportIntent);
                break;
            default:
                break;
        }
    }
}
