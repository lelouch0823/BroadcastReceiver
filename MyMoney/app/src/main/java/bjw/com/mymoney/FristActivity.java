package bjw.com.mymoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public class FristActivity extends BaseActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1 :
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");
                    Logger.d(returnData);
                }
                break;
            default:
        }
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, FristActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2", data2);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Logger.d(getTaskId());
        setContentView(R.layout.frist_activity);

        Button back = (Button) findViewById(R.id.erb);
        final Button finish = (Button) findViewById(R.id.fb);
        //进入第三页面
        Button sb = (Button) findViewById(R.id.sb);


        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FristActivity.this, SecondActivity.class);
                startActivityForResult(intent,1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FristActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
