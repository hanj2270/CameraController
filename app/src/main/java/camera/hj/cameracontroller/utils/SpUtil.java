package camera.hj.cameracontroller.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @author by hansong
 * @date on 2017/10/27 10:54
 * SharePreference工具类
 */
public class SpUtil {

    private static final String FILE_NAME = "gshopperSp";

    /**
     * 存入偏好数据
     *
     * @param context
     * @param key
     * @param object
     * @date 2017-09-25
     */
    public static void putData(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        }
        editor.apply();
    }


    /**
     * 根据key值获取编号数据
     * 2017-09-25
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     * @date 2017-09-25
     */
    public static Object getData(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }


    /**
     * 根据key值移除数据
     *
     * @param context
     * @param key
     * @date 2017-09-25
     */
    public static void removeData(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

}
