package camera.hj.cameracontroller.utils;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by T470S on 2017/12/12.
 */

public class GCUtils {
    public static void BitmapGC(Bitmap[] dataArray) {
        for(int i=0;i<dataArray.length;i++){
            if(!dataArray[i].isRecycled()){
                dataArray[i].recycle();
                dataArray[i]=null;
            }
        }
        dataArray=null;
    }
    public static void BitmapGC(ArrayList<Bitmap> dataArray) {
        for(int i=0;i<dataArray.size();i++){
            if(!dataArray.get(i).isRecycled()){
                dataArray.get(i).recycle();
            }
            dataArray.clear();
        }
        dataArray=new ArrayList<Bitmap>();
    }
    public static void BitmapGC(Bitmap bitmap) {
       if(!bitmap.isRecycled()){
           bitmap.recycle();
           bitmap=null;
       }
    }
}
