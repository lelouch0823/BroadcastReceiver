package com.bjw.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mText;
    //回答true
    private Button mLeft;
    //回答false
    private Button mRight;
    //显示下一题
    private Button mNextButton;
    //显示上一题
    private Button mPrevButton;
    //跳至偷看答案的Activity
    private Button mCheatButton;
    //判断是否作弊
    private boolean mIsCheater;
    //在题库中当前题目的指针
    private int mCurrentIndex = 0;
    //startActivity的Result标识符
    private static final int REQUEST_CODE_CHEAT = 0;
    //保存当前Activity的数据的标识符
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEAT = "cheat!";
    //初始化题库
    public Question[] mQuestions = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, false),
            new Question(R.string.question_4, true),
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(KEY_CHEAT, mIsCheater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT, false);
        }
        initView();
        updateQuestion();
    }

    private void initView() {
        mText = (TextView) findViewById(R.id.text);
        mLeft = (Button) findViewById(R.id.left);
        mRight = (Button) findViewById(R.id.right);
        mNextButton = (Button) findViewById(R.id.next_button);

        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(this);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                checkAnswer(true);
                break;
            case R.id.right:
                checkAnswer(false);
                break;
            case R.id.next_button:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                mIsCheater = false;
                updateQuestion();
                break;
            case R.id.prev_button:
                if (mCurrentIndex != 0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestions.length;
                } else {
                    mCurrentIndex = mQuestions.length - 1;
                }
                updateQuestion();
                break;
            case R.id.cheat_button:
                boolean answerTrue = mQuestions[mCurrentIndex].getAnswerTrue();
                Intent intent = CheatActivity.newIntent(this, answerTrue);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);

                //只要按了cheat按钮就会给当前Question对象的
                // mIsCheat成员变量赋值为true表示这题已经偷看过了
                mQuestions[mCurrentIndex].setCheat(true);
                break;
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestions[mCurrentIndex].getAnswerTrue();
        int messageResId = 0;
        //检查答案前先获取当前Question对象的
        // 成员变量mIsCheat的值并赋给mIsCheater判断是否作弊
        mIsCheater = mQuestions[mCurrentIndex].getCheat();
        if (mIsCheater) {
            messageResId = R.string.judgement;
        } else {
            if (answerIsTrue == userPressedTrue) {
                messageResId = R.string.right;
            } else {
                messageResId = R.string.wrong;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResId();
        mText.setText(question);
    }
}
