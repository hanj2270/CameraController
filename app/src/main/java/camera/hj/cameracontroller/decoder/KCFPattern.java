package camera.hj.cameracontroller.decoder;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import camera.hj.cameracontroller.constant.Settings;
import camera.hj.cameracontroller.utils.GCUtils;
import static camera.hj.cameracontroller.constant.Settings.KCF_DATA_GROUP;

/**
 * Created by T470S on 2017/12/12.
 */

public class KCFPattern extends AbstractPattern implements PosePattern.PoseListener{

    private class kcfFlag{
        boolean isKCFinitReady=false;
    }
    public static ArrayBlockingQueue<Bitmap> tempResblock=new ArrayBlockingQueue<Bitmap>(KCF_DATA_GROUP*2);
    public static ArrayBlockingQueue<Bitmap> tempProblock=new ArrayBlockingQueue<Bitmap>(KCF_DATA_GROUP*2);

    private kcfFlag mkcfFlag;
    public KCFPattern(WorkLine workLine,WorkingFlag flag) {
        super(workLine,flag);
        mkcfFlag=new kcfFlag();
    }

    @Override
    public int[] init(Bitmap resource, int[] eigen) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new int[0];
    }

    @Override
    public int[] update(Bitmap resource) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new int[0];
    }

    @Override
    public void draw(Bitmap resource, int[] eigen) {
    }

    @Override
    public void run() {
        while(true){

            synchronized (flag) {
                if (flag.isPosWorking) {
                    try {
                        mkcfFlag.wait();
                    } catch (Exception e) {
                        Log.e("Pattern error","flag wait error.");
                    }
                }
                Collections.addAll(tempResblock,mWorkLine.getSources(Settings.KCF_DATA_GROUP));
                flag.isPosWorking=false;
                flag.notifyAll();
            }

            synchronized (mkcfFlag) {
                if (!mkcfFlag.isKCFinitReady) {
                    try {
                        mkcfFlag.wait();
                    } catch (Exception e) {
                        Log.e("Pattern error","flag wait error.");
                    }
                }
                mkcfFlag.isKCFinitReady=false;
                mkcfFlag.notifyAll();
            }

            try{
                Log.d("size","kcf update work start,res size="+tempResblock.size()+";pro size="+tempProblock.size());
                for(int i=0;i<tempResblock.size();i++){
                    Bitmap b=tempResblock.take();
                    int[] result = update(b);
                    draw(b,result);
                    tempProblock.offer(b);
                }
                Log.d("size","kcf update work over size="+tempResblock.size()+";pro size="+tempProblock.size());
            }catch (Exception e){
//                      TODO:异常处理待优化，可能回收已处理数据
                Log.e("Pattern error","kcf update interrupt.");
                GCUtils.BitmapGC(tempResblock);
                GCUtils.BitmapGC(tempProblock);
            }
        }
    }

    @Override
    public void GetResult(Bitmap posRes,int[] result) {
        if (tempProblock.size() > 0) {
            Log.d("size","kcf Add to Workline"+tempProblock.size());
            mWorkLine.addProducts(tempProblock);
        }

        synchronized (mkcfFlag) {
            if (posRes != null) {
                init(posRes, result);
            }
            mkcfFlag.isKCFinitReady=true;
            mkcfFlag.notifyAll();
        }
    }
}
