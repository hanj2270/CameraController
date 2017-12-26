package camera.hj.cameracontroller.ui.activity.pushUp;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.Settings;
import camera.hj.cameracontroller.dataSource.CameraManager;
import camera.hj.cameracontroller.decoder.WorkLine;
import camera.hj.cameracontroller.ui.activity.BaseActivity;

public class PushUpActivity extends BaseActivity {
    @BindView(R.id.cameraSurface)
    SurfaceView cameraSurface;

    @BindView(R.id.BitmapSurface)
    SurfaceView BitmapSurface;

    @BindView(R.id.square)
    ImageView square;

    @BindView(R.id.result_text)
    TextView result_text;

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
}
