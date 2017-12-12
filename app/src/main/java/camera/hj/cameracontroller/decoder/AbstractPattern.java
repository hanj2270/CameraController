package camera.hj.cameracontroller.decoder;

import android.graphics.Bitmap;
import android.net.IpPrefix;

/**
 * Created by NC040 on 2017/12/12.
 */

public abstract class AbstractPattern extends Thread implements IPattern {
    private WorkLine mWorkLine;
    public AbstractPattern(WorkLine workLine) {
        this.mWorkLine=workLine;
    }


}
