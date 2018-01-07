package camera.hj.cameracontroller.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.PageConstant;
import camera.hj.cameracontroller.constant.SpConstant;
import camera.hj.cameracontroller.constant.UrlConstant;
import camera.hj.cameracontroller.ui.view.DeleteEtView;
import camera.hj.cameracontroller.utils.SpUtil;
import camera.hj.cameracontroller.utils.ToastUtil;

/**
 * Created by NC040 on 2017/12/21.
 * 登录信息页
 */

@Route(path = UrlConstant.UI_PATH_LOGIN, name = "登录信息")
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.al_et_account)
    DeleteEtView etAccount;
    @BindView(R.id.al_et_pwd)
    DeleteEtView etPwd;
    @BindView(R.id.al_toolBar)
    Toolbar toolBar;

    /**
     * 目标的目录
     */
    private String targetPath = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
        //判断是否需要显示没有登录的提示
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            boolean isNoLogin = bundle.getBoolean(PageConstant.SHOW_NO_LOGIN, false);
            targetPath = bundle.getString(PageConstant.TARGET_PAGE_PATH, "");
            if (isNoLogin) {
                ToastUtil.showToast(getApplicationContext(), "尚未登录,请先登录");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.al_btn_login})
    public void onClick(View view) {
        clickLogin();
    }

    /**
     * 点击登录
     */
    private void clickLogin() {
//        String accountValue = etAccount.getText().toString().trim();
//        String pwdValue = etPwd.getText().toString().trim();
        String accountValue = "ss";
       String pwdValue = "ss";
        if (TextUtils.isEmpty(accountValue)) {
            ToastUtil.showToast(getApplicationContext(), "用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(pwdValue)) {
            ToastUtil.showToast(getApplicationContext(), "密码不能为空");
            return;
        }

        SpUtil.putData(getApplicationContext(), SpConstant.LOGIN_ACCOUNT,accountValue);
        //登录成功，进入主页面
        ARouter.getInstance().build(targetPath).navigation();
        finish();
    }
}
