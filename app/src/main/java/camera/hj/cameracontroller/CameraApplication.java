package camera.hj.cameracontroller;

import android.app.Application;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import com.alibaba.android.arouter.launcher.ARouter;

import camera.hj.cameracontroller.utils.AppUtil;

/**
 * Created by NC040 on 2017/11/19.
 */

public class CameraApplication extends Application {
    private static CameraApplication mInstance;
    public static CameraApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //阿里路由初始化
        initARouter();
        //崩溃日志记录，开发测试中需要注释掉
//        CrashHandler crashHandler=CrashHandler.getInstance();
//        crashHandler.initChecker(getApplicationContext());
    }

    private void initARouter() {
        if (AppUtil.isAppDebug(this)) {
            //开启阿里路由
            ARouter.openDebug();
            //打印日志
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}
