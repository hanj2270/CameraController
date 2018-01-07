package camera.hj.cameracontroller.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.NumConstant;
import camera.hj.cameracontroller.constant.UrlConstant;
import camera.hj.cameracontroller.ui.fragment.MyTrainFragment;
import camera.hj.cameracontroller.ui.fragment.SingleTrainFragment;
import camera.hj.cameracontroller.ui.fragment.TrainPlanFragment;
import camera.hj.cameracontroller.utils.AppManager;
import camera.hj.cameracontroller.utils.ToastUtil;
import io.reactivex.functions.Consumer;

/**
 * Created by NC040 on 2017/12/21.
 */
@Route(path = UrlConstant.UI_PATH_MAIN,name = "首页")
public class MainPageActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationBar.OnTabSelectedListener {


    @BindView(R.id.am_toolbar)
    Toolbar toolbar;
    @BindView(R.id.am_nav_view)
    NavigationView navigationView;
    @BindView(R.id.am_drawerLayout)
    DrawerLayout drawerLayout;
    /**
     * 顶部导航栏
     */
    @BindView(R.id.am_bottomNavigation)
    BottomNavigationBar bottomNavigation;
    /**
     * 当前点击返回的时间
     */
    private long currentBackTime = 0;

    /**
     * 侧滑的监听事件
     */
    private ActionBarDrawerToggle mToggle;

    /**
     * 主页的fragment
     */
    private  SingleTrainFragment fragmentMain;

    /**
     * 运动计划的fragment
     */
    private TrainPlanFragment fragmentPlan;

    /**
     * 设置的fragment
     */
    private MyTrainFragment fragmentSetting;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
        setScrollableText(0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mainpage;
    }

    @Override
    public void initToolBar() {
        setBarStatus(true);
        //设置侧滑菜单信息
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawerLayout_open, R.string.drawerLayout_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        BottomNavigationItem homeItem = new BottomNavigationItem(R.drawable.ic_bottom_2, R.string.text_menu_main)
                .setActiveColorResource(R.color.colorScreen2);
        BottomNavigationItem typeItem = new BottomNavigationItem(R.drawable.ic_bottom_3, R.string.text_menu_plan)
                .setActiveColorResource(R.color.colorScreen3);
        BottomNavigationItem infoItem = new BottomNavigationItem(R.drawable.ic_bottom_4, R.string.text_menu_info)
                .setActiveColorResource(R.color.colorScreen4);
        //设置模式
        bottomNavigation.setMode(2);
        //设置背景颜色
        bottomNavigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigation.addItem(homeItem)
                .addItem(typeItem)
                .addItem(infoItem)
                .setFirstSelectedPosition(0)
                .initialise();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigation.setTabSelectedListener(this);
    }


    @Override
    public void loadData() {

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 初始化权限
     */
    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean request) throws Exception {
                    if (request) {
                        ToastUtil.showToast(getApplicationContext(), "获取权限成功");
                    }
                }
            });
        }
    }

    /**
     * 退出系统
     */
    private void exitSystem() {
        AppManager.getAppManager().finishAllActivity();
        finish();
        System.exit(0);
    }

    /**
     * 点击返回
     */
    private void clickExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否退出当前系统");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                exitSystem();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }


    /**
     * 设置选择的tab
     * @param position
     */
    private void setScrollableText(int position) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (position) {
            case 0:
                if (fragmentMain == null) {
                    fragmentMain = new SingleTrainFragment();
                    transaction.add(R.id.am_frameLayout, fragmentMain);
                } else {
                    transaction.show(fragmentMain);
                }
                break;
            case 1:
                if (fragmentPlan == null) {
                    fragmentPlan = new TrainPlanFragment();
                    transaction.add(R.id.am_frameLayout, fragmentPlan);
                } else {
                    transaction.show(fragmentPlan);
                }
                break;
            case 2:
                if (fragmentSetting == null) {
                    fragmentSetting = new MyTrainFragment();
                    transaction.add(R.id.am_frameLayout, fragmentSetting);
                } else {
                    transaction.show(fragmentSetting);
                }
                break;
            default:
                break;
        }

        transaction.commitAllowingStateLoss();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (fragmentMain != null) {
            transaction.hide(fragmentMain);
        }
        if (fragmentPlan != null) {
            transaction.hide(fragmentPlan);
        }
        if (fragmentSetting != null) {
            transaction.hide(fragmentSetting);
        }
    }

    // ---------导航栏跳转事件---------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_myInfo:
                ARouter.getInstance().build(UrlConstant.UI_PATH_INFO).navigation();
                break;
            case R.id.menu_exitApp:
                clickExit();
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
    // ---------OnTabSelectedListener事件-----------
    @Override
    public void onTabSelected(int position) {
        setScrollableText(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    // ---------后退按钮-----------
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - currentBackTime > NumConstant.BACK_TIME_VALUE) {
                currentBackTime = currentTime;
                ToastUtil.showToast(getApplicationContext(), "再按一次退出");
                return false;
            } else {
                exitSystem();
            }
        }
        return true;
    }
}
