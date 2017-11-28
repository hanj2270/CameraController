package camera.hj.cameracontroller.rtsp.model;

/**
 * Created by koaloffice on 2017/11/25.
 */

public class OptionsModel extends RtpRequestModel {
    public OptionsModel() {
        this.setMethod("OPTIONS");
        this.setVersion("RTSP/1.0");
    }
}
