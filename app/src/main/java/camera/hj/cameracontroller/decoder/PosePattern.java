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
            Thread.sleep(200);
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
            Bitmap temp=null;
            synchronized (flag) {
                if (flag.isPosWorking!=true) {
                    try {
                        flag.wait();
                    } catch (Exception e) {
                        Log.e("Pattern error","flag wait error.");
                    }
                }
                temp = mWorkLine.getSource();
                flag.isPosWorking=false;
                flag.notifyAll();
            }
            if(temp!=null) {
                result = update(temp);
                mListener.GetResult(temp,result);
            }
        }
    }


    interface PoseListener{
        public void GetResult(Bitmap posRes,int[] result);
    }
}
