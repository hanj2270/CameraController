package camera.hj.cameracontroller.ui.activity.pushUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.ui.activity.BaseActivity;

/**
 * Created by NC040 on 2017/12/26.
 */

public class PushUp_VideoActivity extends BaseActivity {
    @BindView(R.id.VideoPlayView)
    SurfaceView VideoPlayView;

    @BindView(R.id.startButton)
    Button startButton;


    @Override
    public int getLayoutId() {
        return R.layout.pushup_video;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void loadData() {

    }


    @OnClick({R.id.startButton})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.startButton:
                Intent i1=new Intent(this,PushUpActivity.class);
                startActivity(i1);
                break;
            case R.id.VideoPlayView:
                //todo:播放视频逻辑
                break;
            default:
                break;
        }
    }
}
