package camera.hj.cameracontroller.decoder;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import static camera.hj.cameracontroller.constant.Settings.DATA_GROUP;
import static camera.hj.cameracontroller.utils.GCUtils.BitmapGC;

/**
 * Created by T470S on 2017/12/12.
 */

public class KCFPattern extends AbstractPattern implements PosePattern.PoseListener{

    private boolean isReady=false;
    private ArrayList<Bitmap> tempArray=new ArrayList<Bitmap>();
    public KCFPattern(WorkLine workLine,WorkingFlag flag) {
        super(workLine,flag);
    }

    @Override
    public int[] init(Bitmap resource, int[] eigen) {
        return new int[0];
    }

    @Override
    public int[] update(Bitmap resource) {
        return new int[0];
    }

    @Override
    public void draw(Bitmap resource, int[] eigen) {
    }

    @Override
    public void run() {
        while(true){
                if (!isReady) {
                    continue;
                }else {
                    Bitmap temp = mWorkLine.getSource();
                    int[] result = update(temp);
                    draw(temp,result);
                    tempArray.add(temp);
                }
        }
    }

    @Override
    public void GetResult(int[] result) {
        synchronized (flag) {
            if (tempArray.size() > 0) {
                Log.d("size","kcf Add"+tempArray.size());
                mWorkLine.addProducts(tempArray);
            }
            Bitmap temp = mWorkLine.getSource();
            flag.isPosWorking=true;
            flag.notifyAll();
            init(mWorkLine.getSource(), result);
            draw(temp, result);
            mWorkLine.addProduct(temp);
            isReady = true;
            tempArray.clear();
        }
    }
}
