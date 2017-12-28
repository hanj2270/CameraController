package camera.hj.cameracontroller.decoder;

/**
 * Created by wurui on 17-12-18.
 */

public class CountResult {
    public int countNum;
    public double angle;
    public boolean isLegalWrist = true;
    public boolean isLegalElbow = true;

    private ResultListener mResultListener;

    public CountResult(int num, double ang, boolean isLegalWrist, boolean isLegalElbow){
        this.angle = ang;
        this.countNum = num;
        this.isLegalElbow = isLegalElbow;
        this.isLegalWrist = isLegalWrist;
    }

    private void isfinish(){
        if(countNum>=2){
            mResultListener.countFinish();
        }
    }

    public interface ResultListener{
        public void countFinish();
    }
}
