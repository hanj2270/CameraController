package camera.hj.cameracontroller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.Settings;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.LocalTestButton)
    Button localTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("test");

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        localTest.setOnClickListener(this);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void loadData() {
        initSettings();
    }

    private void initSettings() {
        DisplayMetrics metrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        Settings.setVideoHeight(metrics.widthPixels);
        Settings.setVideoWidth(metrics.heightPixels);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.LocalTestButton:
                Intent i=new Intent(this,LocalActivity.class);
                startActivity(i);
                break;
        }
    }
}
