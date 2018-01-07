package camera.hj.cameracontroller.ui.activity.navigation;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.UrlConstant;
import camera.hj.cameracontroller.ui.activity.BaseActivity;
import camera.hj.cameracontroller.utils.InfoUtil;

/**
 * 基础的信息页面
 */
@Route(path = UrlConstant.UI_PATH_INFO, name = "个人信息页面")
public class PersonInfoActivity extends BaseActivity {

    @BindView(R.id.api_toolBar)
    Toolbar toolBar;
    @BindView(R.id.api_tv_accountValue)
    TextView tvAccountValue;
    @BindView(R.id.api_tv_email)
    TextView tvEmail;
    @BindView(R.id.api_tv_roleValue)
    TextView tvRoleValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void loadData() {
        String account= InfoUtil.getLogonAccountName();
        tvAccountValue.setText(account);
    }

}
