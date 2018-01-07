package camera.hj.cameracontroller.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by user on 2017/12/31.
 */

public class FileUtil {
    public static boolean copyAssets(Context context, String assetDir, String dstPath) {
        boolean isSuccess=false;
        try {
            String fileNames[] = context.getAssets().list(assetDir);
            if (fileNames.length > 0) {
                File file = new File(Environment.getExternalStorageDirectory(), dstPath);
                if (!file.exists()){
                    file.mkdirs();
                }
                for (String fileName : fileNames) {
                    if (!assetDir.equals("")) { // assets 文件夹下的目录
                        copyAssets(context, assetDir + File.separator + fileName, dstPath + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssets(context, fileName, dstPath + File.separator + fileName);
                    }
                }
            } else {
                File outFile = new File(Environment.getExternalStorageDirectory(), dstPath);
                InputStream is = context.getAssets().open(assetDir);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    /**
     * 删除文件或者文件夹
     *
     * @param directory 目标文件
     * @param keepRoot  是否保留根目录
     */
    public static void onDel(File directory, boolean keepRoot) {
        if (directory != null && directory.exists()) {
            if (directory.isDirectory()) {
                for (File subDirectory : directory.listFiles()) {
                    onDel(subDirectory, false);
                }
            }else{
                directory.delete();
            }
            if (!keepRoot) {
                directory.delete();
            }
        }
    }

}
