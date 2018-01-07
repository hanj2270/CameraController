package camera.hj.cameracontroller.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.NumConstant;
import camera.hj.cameracontroller.utils.RxCountDown;

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
        setBarStatus(true);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void loadData() {
        RxCountDown.countDown(NumConstant.LOGO_SHOW_TIME).compose(this.<Integer>bindToLifecycle()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Message startNext = new Message();
                startNext.what = 1;
                mHandler.sendMessage(startNext);
            }
        });
        mHandler=new WelcomeHandler();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        final long start= System.currentTimeMillis();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    if(System.currentTimeMillis()-start>2000) {
//                        Message startNext = new Message();
//                        startNext.what = 1;
//                        mHandler.sendMessage(startNext);
//                        break;
//                    }
//                }
//            }
//        }).start();

    }


    private class WelcomeHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Intent i=new Intent(WelcomeActivity.this,RegisterActivity.class);
                    startActivity(i);
                    finish();
            }
        }
    }
}
