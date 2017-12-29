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
import camera.hj.cameracontroller.controller.event.PushUpFinishEvent;
import camera.hj.cameracontroller.ui.activity.BaseActivity;
import camera.hj.cameracontroller.utils.AppManager;
import de.greenrobot.event.EventBus;

/**
 * Created by NC040 on 2017/12/26.
 */

public class PushUP_ResultActivity extends BasePushUP_Activity {
    public final static String RESULT_TITLE="RESULT_TITLE";
    public final static String TIMER_RESULT="TIMER_RESULT";
    public final static String PROGRESS_RESULT="PROGRESS_RESULT";
    public final static String GRADE_RESULT="GRADE_RESULT";
    @BindView(R.id.return_bt)
    Button return_bt;

    @BindView(R.id.push_up_result_num)
    TextView push_up_result_num;

    @BindView(R.id.timer_text)
    TextView timer_text;

    @BindView(R.id.grade_text)
    TextView grade_text;

    @BindView(R.id.progress_text)
    TextView progress_text;


    @Override
    public int getLayoutId() {
        return R.layout.pushup_result;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String grade=getIntent().getStringExtra(GRADE_RESULT);
        grade_text.setText(grade);
        push_up_result_num.setText(grade);
        timer_text.setText(getIntent().getStringExtra(TIMER_RESULT));
        progress_text.setText(getIntent().getStringExtra(PROGRESS_RESULT));
    }


    @Override
    public void initToolBar() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(new PushUpFinishEvent("PushUpResult Finish"));
        super.onDestroy();
    }

    @OnClick({R.id.return_bt})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.return_bt:
                this.finish();
                break;
            default:
                break;
        }
    }
}
