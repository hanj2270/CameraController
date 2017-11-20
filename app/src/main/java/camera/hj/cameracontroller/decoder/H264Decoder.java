package camera.hj.cameracontroller.decoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.audiofx.BassBoost;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import camera.hj.cameracontroller.constant.Settings;
import camera.hj.cameracontroller.utils.IOUtils;
import camera.hj.cameracontroller.utils.KMPUtils;


/**
 * Created by NC040 on 2017/11/19.
 */

public class H264Decoder extends AbstractDecoder implements IDecoder {

    private byte[] h264Header = new byte[]{0, 0, 0, 1};

    public H264Decoder(InputStream in, SurfaceHolder holder) {
        super(in, holder);
    }

    @Override
    public void init() throws Exception{
        mCodec= MediaCodec.createDecoderByType("video/avc");
        final MediaFormat mediaformat = MediaFormat.createVideoFormat("video/avc", Settings.VIDEO_WIDTH, Settings.VIDEO_HEIGHT);
        mediaformat.setInteger(MediaFormat.KEY_FRAME_RATE, Settings.FrameRate);
        mCodec.configure(mediaformat, surfaceHolder.getSurface(), null, 0);
    }

    @Override
    public Boolean start() throws Exception{
        mCodec.start();
        byte[] FileBuffer= IOUtils.getBytes(inputStream);
        if(FileBuffer.length==0){
            throw new IOException("File is Empty;");
        }
        display(FileBuffer);
        return true;
    }

    @Override
    public void stop() {
        mCodec.stop();
        mCodec.release();
    }



    private void display(byte[] buffer){
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        long starTime=System.currentTimeMillis();
        ByteBuffer[] inputBuffers = mCodec.getInputBuffers();
        int start=0;
        while(start<buffer.length){
            int nextFrameStart = KMPUtils.KMPMatch(h264Header, buffer, start+4, buffer.length);
            if (nextFrameStart == -1) {
                nextFrameStart = buffer.length;
            }
            int inIndex = mCodec.dequeueInputBuffer(3000);
            if(inIndex>=0){
                ByteBuffer byteBuffer = inputBuffers[inIndex];
                byteBuffer.clear();
                byteBuffer.put(buffer, start, nextFrameStart - start);
                //在给指定Index的inputbuffer[]填充数据后，调用这个函数把数据传给解码器
                mCodec.queueInputBuffer(inIndex, 0, nextFrameStart - start, 0, 0);
                start = nextFrameStart;
            }else{
                //如果缓冲占满，跳过这一帧
                continue;
            }
            controlTime(starTime,info);
        }
    }


    private void controlTime(long TimeStart,MediaCodec.BufferInfo info){
        int outIndex = mCodec.dequeueOutputBuffer(info, 3000);
        if (outIndex >= 0) {
            //帧控制是不在这种情况下工作，因为没有PTS H264是可用的
            while (info.presentationTimeUs / 1000 > System.currentTimeMillis() - TimeStart) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean doRender = (info.size != 0);
            //对outputbuffer的处理完后，调用这个函数把buffer重新返回给codec类。
            mCodec.releaseOutputBuffer(outIndex, doRender);
        }
    }
}
