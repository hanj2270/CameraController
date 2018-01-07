package camera.hj.cameracontroller.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * @date on 2017/10/27 10:54
 * Toast工具类
 */
public class ToastUtil {

    private static Toast toast;
    public static void showToast(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }

    public static void showToast(Context context, String info) {
        if (toast == null) {
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
        } else {
            toast.setText(info);
        }
        toast.show();
    }

}
