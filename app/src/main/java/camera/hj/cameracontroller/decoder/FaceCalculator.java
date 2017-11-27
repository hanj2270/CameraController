package camera.hj.cameracontroller.decoder;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by NC040 on 2017/11/27.
 */

public class FaceCalculator extends Thread {
    ArrayBlockingQueue<Bitmap> DataQueue = new ArrayBlockingQueue<Bitmap>(1000);

    FaceCalListener mFaceCalListener;


    @Override
    public void run() {
        while(true){
            //目前为持续循环，如果运算效率较高，这里采用每秒执行一次
            //Thread.sleep(500);
            int size=DataQueue.size();
            if(size>0){
                Bitmap[] DataArray= getAllBitmap(size);
                mFaceCalListener.onChange(calculate(DataArray));
                BitmapGC(DataArray);
            }
        }
    }

    public void add(Bitmap data){
        if(data!=null) {
            DataQueue.offer(data);
        }
    }


    private boolean calculate(Bitmap[] bitmaps){
        //这里写计算人脸识别逻辑
        return true;
    }

    private Bitmap[] getAllBitmap(int size) {
        ArrayList<Bitmap> mapList=new ArrayList<>();
        for(int i=0;i<size;i++){
            try {
                mapList.add(DataQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("Controller error:",e.toString());
            }
        }
        return (Bitmap[])mapList.toArray(new Bitmap[0]);
    }

    private void BitmapGC(Bitmap[] dataArray) {
        for(int i=0;i<dataArray.length;i++){
            if(!dataArray[i].isRecycled()){
                dataArray[i].recycle();
                dataArray[i]=null;
            }
        }
        dataArray=null;
    }

    public void setOnFaceCalListener(FaceCalListener mFaceCalListener) {
        this.mFaceCalListener = mFaceCalListener;
    }

     public interface FaceCalListener{
        public void onChange(boolean CalResult);
    }
}
