package camera.hj.cameracontroller.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.Settings;
import camera.hj.cameracontroller.dataSource.CameraManager;
import camera.hj.cameracontroller.decoder.FaceCalculator;
import camera.hj.cameracontroller.utils.SizeUtils;
import camera.hj.cameracontroller.utils.YUVToRGBHelper;

public class MainActivity extends BaseActivity implements FaceCalculator.FaceCalListener {
    @BindView(R.id.cameraSurface)
    SurfaceView cameraSurface;

    @BindView(R.id.square)
    ImageView square;

    @BindView(R.id.result_text)
    TextView result_text;

    private CameraManager cameraManager;
    private static boolean CurrentStatus=false;
    private final MyHandler mHandler = new MyHandler();
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
        FaceCalculator faceCalculator=FaceCalculator.getInstance();
        faceCalculator.setOnFaceCalListener(this);
        cameraManager=new CameraManager(this,cameraSurface,faceCalculator);
        Log.d("###count###","queue size is init");
    }

    private void initSettings() {
        DisplayMetrics metrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        Settings.setVideoHeight(metrics.widthPixels);
        Settings.setVideoWidth(metrics.heightPixels);
    }


    @Override
    public void onChange(boolean CalResult) {
        if(CurrentStatus!=CalResult) {
            Message msg = new Message();
            msg.getData().putBoolean(CALCULATE_RESULT, CalResult);
            mHandler.sendMessage(msg);
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            boolean result=msg.getData().getBoolean(CALCULATE_RESULT);
            CurrentStatus=result;
            if(result){
                result_text.setText(getString(R.string.FoundFace));
                result_text.setTextColor(getColor(R.color.green));
                square.setImageDrawable(getDrawable(R.drawable.face_green));
            }else{
                result_text.setText(getString(R.string.NoFace));
                result_text.setTextColor(getColor(R.color.red));
                square.setImageDrawable(getDrawable(R.drawable.face_white));
            }
        }
    }
}
