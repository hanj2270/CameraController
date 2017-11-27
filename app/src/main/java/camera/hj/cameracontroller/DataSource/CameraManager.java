package camera.hj.cameracontroller.dataSource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.Image;
import android.media.ImageReader;
import android.os.Trace;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import camera.hj.cameracontroller.decoder.FaceCalculator;
import camera.hj.cameracontroller.ui.activity.MainActivity;
import camera.hj.cameracontroller.ui.view.AutoFitTextureView;
import camera.hj.cameracontroller.utils.SizeUtils;
import camera.hj.cameracontroller.utils.YUVToRGBHelper;

/**
 * Created by NC040 on 2017/11/27.
 */

public class CameraManager implements SurfaceHolder.Callback, Camera.PreviewCallback  {
   private Context ctx;
    private SurfaceView cameraSurface;
    private Camera camera;
    private YUVToRGBHelper mConvertHelper;
    private Size optionSize;

    private FaceCalculator faceCalculator;

    public CameraManager(Context ctx,SurfaceView cameraSurface,FaceCalculator faceCalculator) {
        this.ctx=ctx;
        this.cameraSurface=cameraSurface;
        this.faceCalculator = faceCalculator;
        Camera mCamera = Camera.open();
        Camera.Parameters p = mCamera.getParameters();
        p.setPreviewFormat(ImageFormat.NV21);
        mConvertHelper=new YUVToRGBHelper(ctx);
        faceCalculator.start();
        cameraSurface.getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(surfaceHolder);
        } catch (Exception e) {
            if (null != camera) {
                camera.release();
                camera = null;
            }
            e.printStackTrace();
            Toast.makeText(ctx, "启动摄像头失败,请开启摄像头权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Camera.Parameters parameters = camera.getParameters();//获取camera的parameter实例
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
        Size[] sizes = new Size[sizeList.size()];
        int ii = 0;
        for (Camera.Size size : sizeList) {
            sizes[ii++] = new Size(size.width, size.height);
        }
        optionSize = SizeUtils.chooseOptimalSize(sizes, cameraSurface.getWidth(), cameraSurface.getHeight());//获取一个最为适配的camera.size
        mConvertHelper.setOptionSize(optionSize);
        parameters.setPreviewSize(optionSize.getWidth(),optionSize.getHeight());//把camera.size赋值到parameters
        camera.setParameters(parameters);
        camera.setPreviewCallback(this);
        camera.startPreview();//开始预览
        camera.setDisplayOrientation(90);//将预览旋转90度

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (null != camera) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Bitmap bmpout= mConvertHelper.getBitmap(bytes);
        faceCalculator.add(bmpout);
//        Canvas canvas=bitmapSurface.getHolder().lockCanvas();
//        canvas.drawBitmap(bmpout,0.0f,0.0f,new Paint());
//        bitmapSurface.getHolder().unlockCanvasAndPost(canvas);
    }
}
