package camera.hj.cameracontroller.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.ui.fragment.MyTrainFragment;
import camera.hj.cameracontroller.ui.fragment.SingleTrainFragment;
import camera.hj.cameracontroller.ui.fragment.TrainPlanFragment;
import camera.hj.cameracontroller.utils.AppManager;

/**
 * Created by NC040 on 2017/12/21.
 */

public class MainPageActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private Class[] mFragmentArrays = {MyTrainFragment.class, SingleTrainFragment.class
            , TrainPlanFragment.class};

    private String[] mTextArrays = {"首页", "证书", "应用", "设置"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_mainPage);

        setTabHost();
    }

    private void setTabHost() {
            mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
            mTabHost.setup(this, getSupportFragmentManager(), R.id.content_frame);

            int count = 0;
            count = mFragmentArrays.length;
            for (int i = 0; i < count; i++) {
                //cancel the divided line among tabhosts
                mTabHost.getTabWidget().setDividerDrawable(null);

                // 将Tab按钮添加进Tab选项卡中
                // 给每个Tab按钮设置图标、文字和内容
                TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArrays[i])
                        .setIndicator(getTabItemView(i));
                mTabHost.addTab(tabSpec, mFragmentArrays[i], null);
                //todo: 设置Tab按钮背景

            }

    }

    private View getTabItemView(int index) {
        //todo:绘制图标背景
        return null;
    }
}
