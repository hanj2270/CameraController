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

    private boolean isReady=false;
    private BlockingQueue<Bitmap> tempResblock=new ArrayBlockingQueue<Bitmap>(10);
    private BlockingQueue<Bitmap> tempProblock=new ArrayBlockingQueue<Bitmap>(10);
    public KCFPattern(WorkLine workLine,WorkingFlag flag) {
        super(workLine,flag);
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
                if (flag.isPosWorking||!isReady) {
                    try {
                        flag.wait();
                    } catch (Exception e) {
                        Log.e("Pattern error","flag wait error.");
                    }
                }else {
                    if(tempResblock.size()<5) {
                        Collections.addAll(tempResblock, mWorkLine.getSources(Settings.KCF_DATA_GROUP));
                    }
                    flag.isPosWorking = true;
                    flag.notifyAll();
                    try{
                        for(int i=0;i<KCF_DATA_GROUP;i++){
                            Bitmap b=tempResblock.take();
                            int[] result = update(b);
                            draw(b,result);
                            tempProblock.offer(b);
                    }
                    }catch (Exception e){
//                      TODO:异常处理待优化，可能回收已处理数据
                        Log.e("Pattern error","kcf update interrupt.");
                        GCUtils.BitmapGC(tempResblock);
                    }
                }
            }
        }
    }

    @Override
    public void GetResult(int[] result) {
        Bitmap temp=null;
        synchronized (flag) {
            if (tempProblock.size() > 0) {
                Log.d("size","kcf Add to Workline"+tempProblock.size());
                mWorkLine.addProducts(tempProblock);
            }
            temp = mWorkLine.getSource();
            flag.isPosWorking=true;
            flag.notifyAll();
        }
        if(temp!=null) {
            init(temp, result);
            draw(temp, result);
            // TODO: 此处锁已经释放，直接将结果加入workline结果队列，依赖kcf速度快于pos特性，可能不安全
            mWorkLine.addProduct(temp);
            isReady = true;
        }
    }
}
