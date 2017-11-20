package camera.hj.cameracontroller.decoder;

import android.media.MediaCodec;
import android.view.SurfaceHolder;

import java.io.InputStream;

/**
 * Created by NC040 on 2017/11/19.
 */

public class AbstractDecoder implements IDecoder{
    protected static MediaCodec mCodec;
    protected static InputStream inputStream;
    protected static SurfaceHolder surfaceHolder;

    public AbstractDecoder(InputStream in,SurfaceHolder holder) {
        inputStream=in;
        surfaceHolder=holder;
    }




    @Override
    public void init()throws Exception {

    }

    @Override
    public Boolean start() throws Exception{
        return false;
    }

    @Override
    public void stop(){

    }
}
