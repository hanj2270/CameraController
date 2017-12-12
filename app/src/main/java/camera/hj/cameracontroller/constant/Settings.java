package camera.hj.cameracontroller.constant;

import android.util.DisplayMetrics;

import camera.hj.cameracontroller.CameraApplication;
import camera.hj.cameracontroller.ui.activity.MainActivity;

/**
 * Created by NC040 on 2017/11/19.
 */

public class Settings {


    //码率
    public final static int FrameRate = 15;

    public static void setVideoWidth(int videoWidth) {
        VIDEO_WIDTH = videoWidth;
    }

    public static void setVideoHeight(int videoHeight) {
        VIDEO_HEIGHT = videoHeight;
    }

    //视频分辨率
    public static int VIDEO_WIDTH = 800;
    public static int VIDEO_HEIGHT = 600;

    //Pattern使用的Bitmap高宽
    public static int PATTERRN_WIDTH = 800;
    public static int PATTERRN_HEIGHT = 600;

    //每组数据（pose+kcf）数量
    public final static int DATA_GROUP=9;

}
