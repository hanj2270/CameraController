package camera.hj.cameracontroller.ui.activity.pushUp;

import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.ui.activity.BaseActivity;

/**
 * Created by NC040 on 2017/12/26.
 */

public class PushUP_ResultActivity extends BaseActivity {

    @BindView(R.id.return_bt)
    Button return_bt;

    @Override
    public int getLayoutId() {
        return R.layout.pushup_result;
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
}
