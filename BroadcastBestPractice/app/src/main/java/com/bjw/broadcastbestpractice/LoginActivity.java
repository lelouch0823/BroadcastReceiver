package com.bjw.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private android.widget.EditText account;
    private android.widget.EditText password;
    private android.widget.CheckBox remember;
    private Button loginbutton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean rememberOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.loginbutton = (Button) findViewById(R.id.login_button);
        this.remember = (CheckBox) findViewById(R.id.remember);
        this.password = (EditText) findViewById(R.id.password);
        this.account = (EditText) findViewById(R.id.account);
        loginButton = (Button) findViewById(R.id.login_button);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        password.setText(sharedPreferences.getString("password", ""));
        account.setText(sharedPreferences.getString("account", ""));
       rememberOn = sharedPreferences.getBoolean("remember", false);
/*         BufferedReader reader = null;
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput("account");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            account.setText(reader.readLine());
            password.setText(reader.readLine());
            Logger.d(reader);
        } catch (Exception e) {
            Toast.makeText(this, "No account and password saved", Toast.LENGTH_SHORT).show();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String passText = password.getText().toString();
                String accText = account.getText().toString();
                Logger.d(passText);
                if (remember.isChecked()) {
                    rememberOn = true;
                    save(passText, accText, rememberOn);
                } else {
                    rememberOn = false;
                    save("", "", rememberOn);
                }
                if ("admin".equals(accText) && "123456".equals(passText)) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                }
                }
        });
    }

    private void save(String password, String account, boolean rememberOn) {
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("account", account);
        editor.putString("password", password);
        editor.putBoolean("rememberOn", rememberOn);
        editor.commit();
        Logger.d("saved");
        /*
        BufferedWriter writer = null;
        FileOutputStream outputStream =null;
        try {
            outputStream = openFileOutput("account", MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(taccount);
            writer.write(tpassword);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}
