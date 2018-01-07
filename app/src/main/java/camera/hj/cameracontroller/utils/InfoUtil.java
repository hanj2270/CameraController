package camera.hj.cameracontroller.utils;

import android.text.TextUtils;

import camera.hj.cameracontroller.CameraApplication;
import camera.hj.cameracontroller.constant.Settings;
import camera.hj.cameracontroller.constant.SpConstant;

/**
 * @author by hansong
 * @date on 2017/12/20 18:49
 */

public class InfoUtil {

    /**
     * 获取当前登录的用户名
     *
     * @return
     */
    public static String getLogonAccountName() {
        return (String) SpUtil.getData(CameraApplication.getInstance(), SpConstant.LOGIN_ACCOUNT, "");
    }

    /**
     * 校验当前登录的用户名是否为空
     *
     * @return
     */
    public static boolean validateIsLogin() {
        return TextUtils.isEmpty(getLogonAccountName());
    }
}
