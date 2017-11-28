package camera.hj.cameracontroller.rtsp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by koaloffice on 2017/11/25.
 */

public abstract class AbstractModel {
    protected String version;
    protected int state;
    protected Map<String, String> headers = new HashMap();
    protected byte[] body;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
