package com.bjw.myappprotfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View
        .OnClickListener {


    private Button mBtnStartMoviesApp;
    private Button mBtnStartStockApp;
    private Button mBtnStartBuildApp;
    private Button mBtnStartMakeApp;
    private Button mBtnStartGoApp;
    private Button mBtnStartCapstoneApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnStartMoviesApp = (Button) findViewById(R.id.btn_start_movies_app);
        mBtnStartStockApp = (Button) findViewById(R.id.btn_start_stock_app);
        mBtnStartBuildApp = (Button) findViewById(R.id.btn_start_build_app);
        mBtnStartMakeApp = (Button) findViewById(R.id.btn_start_make_app);
        mBtnStartGoApp = (Button) findViewById(R.id.btn_start_go_app);
        mBtnStartCapstoneApp = (Button) findViewById(R.id
                .btn_start_capstone_app);

        mBtnStartMoviesApp.setOnClickListener(this);
        mBtnStartStockApp.setOnClickListener(this);
        mBtnStartBuildApp.setOnClickListener(this);
        mBtnStartMakeApp.setOnClickListener(this);
        mBtnStartGoApp.setOnClickListener(this);
        mBtnStartCapstoneApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_movies_app:
                showToast(mBtnStartMoviesApp);
                break;
            case R.id.btn_start_stock_app:
                showToast(mBtnStartStockApp);
                break;
            case R.id.btn_start_build_app:
                showToast(mBtnStartBuildApp);
                break;
            case R.id.btn_start_make_app:
                showToast(mBtnStartMakeApp);
                break;
            case R.id.btn_start_go_app:
                showToast(mBtnStartGoApp);
                break;
            case R.id.btn_start_capstone_app:
                showToast(mBtnStartCapstoneApp);
                break;
        }
    }

    private void showToast(Button btn) {
        Toast.makeText(MainActivity.this, "This button will launch my" +
                " " + btn.getText().toString().toLowerCase() + " app", Toast
                .LENGTH_SHORT)
                .show();
    }


}
