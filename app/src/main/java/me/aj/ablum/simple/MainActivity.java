package me.aj.ablum.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;

import me.aj.ablum.SelectImageActivity;
import me.aj.ablum.SelectType;
import me.aj.ablum.utils.AblumConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MyHandler(this).sendEmptyMessageDelayed(0, 10);
    }

    static class MyHandler extends Handler {

        WeakReference<Activity> contextWeakReference;

        public MyHandler(Activity activity) {
            contextWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            if (contextWeakReference == null) return;

            Intent intent = new Intent(contextWeakReference.get(), SelectImageActivity.class);
            intent.putExtra(AblumConstants.EXTRA_SELECT_TYPE, SelectType.SINGLE);
            intent.putExtra(AblumConstants.EXTRA_IS_CROP, true);
            contextWeakReference.get().startActivityForResult(intent, 123);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == AblumConstants.REQUEST_CODE_SELECT_IMAGE) {// 选择图片返回
            Log.e("-------------", "uri:" + getIntent().getParcelableExtra(AblumConstants.EXTRA_URI).toString());
        }
    }
}
