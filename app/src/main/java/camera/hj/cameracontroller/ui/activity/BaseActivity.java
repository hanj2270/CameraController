package camera.hj.cameracontroller.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import camera.hj.cameracontroller.controller.event.IEvent;
import camera.hj.cameracontroller.utils.AppManager;
import de.greenrobot.event.EventBus;

/**
 * Created by NC040 on 2017/11/19.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Camera Activity","######### onCreate:" + this.getClass().getName());
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        ButterKnife.bind(this);
        //注册eventBus
        EventBus.getDefault().register(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //加载控制数据
        loadData();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();

    public abstract void loadData();

    public void onEvent(IEvent event) {
        //处理不在ui上的事件时复写此方法
    }


    /**
     * 是否透明标题
     * @param isTransparent
     */
    protected void setBarStatus(boolean isTransparent){
        if(isTransparent){
            initBarStatus();
        }
    }
    /**
     * 实现状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initBarStatus() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
