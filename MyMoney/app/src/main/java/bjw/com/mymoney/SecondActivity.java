package bjw.com.mymoney;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/6/28 0028.
 */

public class SecondActivity extends BaseActivity {
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return", "我是通过返回键返回的数据");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Logger.d(getTaskId());
        setContentView(R.layout.second_activity);

        Button button = (Button) findViewById(R.id.ibutton);
        Button backButton = (Button) findViewById(R.id.back);
        Button dateButton = (Button) findViewById(R.id.date_button);
        Button exitButton = (Button) findViewById(R.id.exit_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.finishAll();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, FristActivity.class);
                String date = "你好啊我是SecondActivity传过来的数据";
                intent.putExtra("data_return", date);
                //startActivity(intent);
                setResult(RESULT_OK,intent);
                finish();
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
    }

    public static void actionStart(Context context, String data1, String data2) {
        Logger.d(data1,data2);
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }
}
