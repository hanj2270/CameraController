package camera.hj.cameracontroller.decoder;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by T470S on 2017/12/12.
 */

public class PosePattern extends AbstractPattern {
    private static int[] result;
    private PoseListener mListener;
    public PosePattern(WorkLine workLine,WorkingFlag flag) {
        super(workLine,flag);
    }

    public void setOnPoseListener(PoseListener PoseListener) {
        this.mListener = PoseListener;
    }

    @Override
    public int[] init(Bitmap resource, int[] eigen) {
        return new int[0];
    }

    @Override
    public int[] update(Bitmap resource) {
        try {
            Thread.sleep(100);
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
                if (!flag.isPosWorking) {
                    try {
                        flag.wait();
                    } catch (Exception e) {
                        Log.e("Pattern error","flag wait error.");
                    }
                }else {
                    Bitmap temp = mWorkLine.getSource();
                    flag.isPosWorking = false;
                    flag.notifyAll();
                    result = update(temp);
                    draw(temp,result);
                    mWorkLine.addProduct(temp);
                    mListener.GetResult(result);
                }
            }
        }
    }


    interface PoseListener{
        public void GetResult(int[] result);
    }
}
