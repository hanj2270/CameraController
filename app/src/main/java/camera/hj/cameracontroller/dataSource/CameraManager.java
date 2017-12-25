package camera.hj.cameracontroller.dataSource;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.List;

import camera.hj.cameracontroller.constant.Settings;
import camera.hj.cameracontroller.decoder.KCFPattern;
import camera.hj.cameracontroller.decoder.PosePattern;
import camera.hj.cameracontroller.decoder.WorkLine;
import camera.hj.cameracontroller.decoder.WorkingFlag;
import camera.hj.cameracontroller.utils.IOUtils;
import camera.hj.cameracontroller.utils.SizeUtils;
import camera.hj.cameracontroller.utils.YUVToRGBHelper;

import static camera.hj.cameracontroller.utils.GCUtils.BitmapGC;

/**
 * Created by NC040 on 2017/11/27.
 */

public class CameraManager implements SurfaceHolder.Callback, Camera.PreviewCallback  {
   private Context ctx;
    private SurfaceView cameraSurface;
    private SurfaceView bitmapSurface;
    private Camera camera;
    private YUVToRGBHelper mConvertHelper;
    private Size optionSize;
    private PlayThread mPlayThread;
    private static long timerBefore=System.currentTimeMillis();

    private WorkLine WorkLine;

    public CameraManager(Context ctx,SurfaceView cameraSurface,WorkLine WorkLine) {
        this.ctx=ctx;
        this.cameraSurface=cameraSurface;
        this.WorkLine = WorkLine;
        Camera mCamera = Camera.open();
        Camera.Parameters p = mCamera.getParameters();
        p.setPreviewFormat(ImageFormat.NV21);
        mConvertHelper=new YUVToRGBHelper(ctx);
        WorkingFlag wf=new WorkingFlag();
        PosePattern posPattern=new PosePattern(WorkLine,wf);
        KCFPattern kcfPattern=new KCFPattern(WorkLine,wf);
        posPattern.setOnPoseListener(kcfPattern);
        posPattern.start();
        kcfPattern.start();
        mPlayThread=new PlayThread();
        mPlayThread.start();
        cameraSurface.getHolder().addCallback(this);
    }

    public void setBitmapSurface(SurfaceView surface){
        bitmapSurface=surface;
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
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        //todo:GC全部未处理数据
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Bitmap bmpout= mConvertHelper.getBitmap(bytes);
        WorkLine.addSource(bmpout);
    }

    public void stop(){
        //去除预览监听，防止在摄像头释放后调用崩溃
        while(!mPlayThread.isInterrupted()){
            mPlayThread.interrupt();
        }
    }

    private class PlayThread extends Thread{
        @Override
        public void run() {
            while(true){
                if(WorkLine.ready()) {
                    Bitmap temp=WorkLine.play();
                    if(!temp.isRecycled()) {
                        Canvas canvas = bitmapSurface.getHolder().lockCanvas();
                        IOUtils.TimeBlance(timerBefore);
                        canvas.drawBitmap(temp, 0.0f, 0.0f, new Paint());
                        timerBefore=System.currentTimeMillis();
                        bitmapSurface.getHolder().unlockCanvasAndPost(canvas);
                        BitmapGC(temp);
                    }
                }
            }
        }
    }
}
