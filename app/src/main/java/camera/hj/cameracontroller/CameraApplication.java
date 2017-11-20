package camera.hj.cameracontroller;

import android.app.Application;

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
        //崩溃日志记录，开发测试中需要注释掉
//        CrashHandler crashHandler=CrashHandler.getInstance();
//        crashHandler.initChecker(getApplicationContext());
    }
}
