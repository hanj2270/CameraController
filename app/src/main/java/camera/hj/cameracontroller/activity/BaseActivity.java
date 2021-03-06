package camera.hj.cameracontroller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;

import butterknife.ButterKnife;
import camera.hj.cameracontroller.utils.AppManager;

/**
 * Created by NC040 on 2017/11/19.
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Camera Activity","######### onCreate:" + this.getClass().getName());
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        loadData();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();

    public abstract void loadData();


}
