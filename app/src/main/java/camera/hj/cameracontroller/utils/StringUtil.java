package camera.hj.cameracontroller.utils;

/**
 * Created by user on 2017/12/30.
 * 字符串相关的类
 */

public class StringUtil {


    public static String convertTimeValue(int countValue) {
        StringBuffer sb = new StringBuffer();
        int  second= countValue % 60;
        int min = countValue / 60;
        sb.append(min).append(" min ").append(second).append(" s ");

        return sb.toString();

    }
}
