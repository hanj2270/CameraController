package camera.hj.cameracontroller.ui.activity.pushUp;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.PageConstant;
import camera.hj.cameracontroller.constant.Settings;
import camera.hj.cameracontroller.constant.UrlConstant;
import camera.hj.cameracontroller.controller.event.PushUpFinishEvent;
import camera.hj.cameracontroller.controller.event.PushUpToastEvent;
import camera.hj.cameracontroller.dataSource.CameraManager;
import camera.hj.cameracontroller.decoder.CountResult;
import camera.hj.cameracontroller.decoder.WorkLine;
import camera.hj.cameracontroller.ui.activity.BaseActivity;
import de.greenrobot.event.EventBus;

import static camera.hj.cameracontroller.controller.event.IEvent.EVENT_TAG;
import static camera.hj.cameracontroller.ui.activity.pushUp.PushUP_ResultActivity.GRADE_RESULT;
import static camera.hj.cameracontroller.ui.activity.pushUp.PushUP_ResultActivity.PROGRESS_RESULT;
import static camera.hj.cameracontroller.ui.activity.pushUp.PushUP_ResultActivity.TIMER_RESULT;
@Route(path = UrlConstant.UI_PATH_SPORT, name = "开始运动")
public class PushUpActivity extends BaseActivity implements CountResult.ResultListener{
    @BindView(R.id.cameraSurface)
    SurfaceView cameraSurface;

    @BindView(R.id.BitmapSurface)
    SurfaceView BitmapSurface;

    @BindView(R.id.square)
    ImageView square;

    @BindView(R.id.test_result_bt)
    Button test_result_bt;

    private CameraManager cameraManager;
    private static boolean CurrentStatus=false;
    public final static String CALCULATE_RESULT="CALCULATE_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void loadData() {
        initSettings();
        WorkLine workLine=WorkLine.getInstance();
        cameraManager=new CameraManager(this,cameraSurface,workLine);
        cameraManager.setBitmapSurface(BitmapSurface);
        Log.d("###count###","queue size is init");
    }

    private void initSettings() {
        DisplayMetrics metrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        Settings.setVideoHeight(metrics.widthPixels);
        Settings.setVideoWidth(metrics.heightPixels);
    }

    @Override
    protected void onDestroy() {
        WorkLine.getInstance().clear();
        super.onDestroy();
    }

    @Override
    public void countFinish() {
        Intent i=new Intent(this,PushUP_ResultActivity.class);
        //传入展示在resultActivity上的数据
        i.putExtra(TIMER_RESULT,"");
        i.putExtra(GRADE_RESULT,"");
        i.putExtra(PROGRESS_RESULT,"");
        startActivity(i);
    }

    //传给result活动的数据
    public void skipToResult(String time,String grade,String progress){
        ARouter.getInstance()
                .build(UrlConstant.UI_PATH_RS)
                .withString(TIMER_RESULT, time)
                .withString(GRADE_RESULT,grade)
                .withString(PROGRESS_RESULT,progress)
                .navigation();
    }

    //获取到PlayThread的结果并展示
    public void onEventMainThread(PushUpToastEvent event) {
        String msg = "计数："+event.getMsg();
        Log.d(EVENT_TAG, "Toast From Play Thread:"+msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(PushUpFinishEvent event){
        this.finish();
    }


    @OnClick({R.id.test_result_bt})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.test_result_bt:
                skipToResult("2:34","99","45");
                break;
            default:
                break;
        }
    }
}
