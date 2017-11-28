package camera.hj.cameracontroller.rtsp.model;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by koaloffice on 2017/11/25.
 */

public class RtpResponseModel extends AbstractModel {
    protected int state;
    protected int code;
    protected String stateLine;

    public String getStateLine() {
        return this.stateLine;
    }

    public void setStateLine(String stateLine) {
        this.stateLine = stateLine;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.version).append(" ").append(this.code).append(" ").append(this.stateLine).append("\r\n");
        Iterator i$ = this.headers.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry head = (Map.Entry)i$.next();
            sb.append((String)head.getKey()).append(": ").append((String)head.getValue()).append("\r\n");
        }

        sb.append("\r\n");
        return sb.toString();
    }
}
