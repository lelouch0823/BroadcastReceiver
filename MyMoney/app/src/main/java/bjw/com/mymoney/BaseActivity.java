package bjw.com.mymoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
public class BaseActivity extends AppCompatActivity {
    protected void onCreate(Bundle savaInstanceState) {
        super.onCreate(savaInstanceState);
        Logger.d(getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


}
