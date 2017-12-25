package camera.hj.cameracontroller.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import camera.hj.cameracontroller.R;

/**
 * Created by NC040 on 2017/12/21.
 */

public class WelcomeActivity extends BaseActivity {
    private WelcomeHandler mHandler;
    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void loadData() {
        mHandler=new WelcomeHandler();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final long start= System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(System.currentTimeMillis()-start>2000) {
                        Message startNext = new Message();
                        startNext.what = 1;
                        mHandler.sendMessage(startNext);
                        break;
                    }
                }
            }
        }).start();

    }


    private class WelcomeHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Intent i=new Intent(WelcomeActivity.this,RegisterActivity.class);
                    startActivity(i);
            }
        }
    }
}
