package camera.hj.cameracontroller.decoder;

import java.io.InputStream;

/**
 * Created by NC040 on 2017/11/19.
 */

public interface IDecoder {
    public void init()throws Exception;
    public Boolean start()throws Exception;
    public void stop();
}
