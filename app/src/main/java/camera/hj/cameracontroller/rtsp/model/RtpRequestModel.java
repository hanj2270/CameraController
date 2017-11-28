package camera.hj.cameracontroller.rtsp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by koaloffice on 2017/11/21.
 */

public abstract class RtpRequestModel extends AbstractModel {
    protected String method;
    protected String uri;



    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
