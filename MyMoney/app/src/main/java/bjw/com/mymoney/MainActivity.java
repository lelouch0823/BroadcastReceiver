package bjw.com.mymoney;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    private Button addButton;
    private Button cutButton;
    private TextView textView;
    private EditText et;
    private int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        addButton = (Button) findViewById(R.id.addButton);
        cutButton = (Button) findViewById(R.id.cutButton);
        textView = (TextView) findViewById(R.id.textView);
        et = (EditText) findViewById(R.id.myET);
        // 进入Frist的按钮
        Button finishButton = (Button)findViewById(R.id.finish);
        Button bow = (Button) findViewById(R.id.bow);
        // 进入Second的按钮
        Button sb1 = (Button) findViewById(R.id.sb1);

        sb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.addCategory("android.intent.category.SEC");
                startActivity(intent);*/
                SecondActivity.actionStart(MainActivity.this,"data1","data2");
            }
        });
/*        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent new Intent(Intent.CATEGORY_APP_EMAIL);

            }
        });*/

        bow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputMoney = et.getText().toString().trim();
                try {
                    int iMoney = Integer.parseInt(inputMoney);
                    if (iMoney <= money) {
                        Toast.makeText(MainActivity.this,"恭喜达成目标金额",Toast.LENGTH_LONG).show();
                    }else {
                        money += 100;
                        textView.setText("我有" + money + "元钱");
                    }
                }catch(Exception e) {
                    Toast.makeText(MainActivity.this,"先输金额在按",Toast.LENGTH_SHORT).show();
                }
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FristActivity.class);
                startActivity(intent);
            }
        });
        cutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputMoney = et.getText().toString().trim();
                try {
                    int iMoney = Integer.parseInt(inputMoney);
                    if (money == 0) {
                        Toast.makeText(MainActivity.this, "没钱了别按了啊", Toast.LENGTH_SHORT).show();
                    } else {
                        money -= 100;
                        textView.setText("我有" + money + "元钱");
                    }
                }catch(Exception e) {
                    Toast.makeText(MainActivity.this,"先输金额在按",Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(MainActivity.this,"你点了添加",Toast.LENGTH_SHORT).show();
                break;
            case R.id.removed_item:
                Toast.makeText(MainActivity.this, "你点了删除", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
