package com.bjw.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bjw.quiz.answer_is_true";

    private static final String EXTRA_ANSWER_SHOWN =
            "com.bjw.quiz.answer_shown";
    //保存是否作弊的键
    private static final String CHEAT = "cheat!";
    //判断是否作弊的值
    private boolean mAnswerIsTrue;
    private TextView mAnswer;
    private Button mShowAnswerButton;
    private boolean mIsCheater;
    private static final String SAVE_ANSWER = "the answer is";

    public static Intent newIntent(Context context, boolean answerIsTrue) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_ANSWER,mAnswerIsTrue);
        outState.putBoolean(CHEAT,mIsCheater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        initView();
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(SAVE_ANSWER);
            if (mAnswerIsTrue) {
                mAnswer.setText("Answer Is True");
            } else {
                mAnswer.setText("Answer Is false");
            }
        }
    }

    private void initView() {
        mAnswer = (TextView) findViewById(R.id.answer);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);

        mShowAnswerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_answer_button:
                if (mAnswerIsTrue) {
                    mAnswer.setText("Answer Is True");
                } else {
                    mAnswer.setText("Answer Is false");
                }
                mIsCheater = true;
                setAnswerShownResult(mIsCheater);
                break;
        }
    }

    public static boolean wasAnswerShown(Intent intent) {
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, true);
    }

    private void setAnswerShownResult(boolean b) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, b);
        setResult(RESULT_OK,intent);
    }
}
