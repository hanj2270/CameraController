package camera.hj.cameracontroller.decoder;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

/**
 * Created by NC040 on 2017/12/12.
 */

public class WorkLine {
    ArrayBlockingQueue<Bitmap> ResDataQueue = new ArrayBlockingQueue<Bitmap>(1000);
    ArrayBlockingQueue<Bitmap> ProductDataQueue = new ArrayBlockingQueue<Bitmap>(1000);

    ArrayList<IPattern> patterns=new ArrayList<>();

    private static WorkLine ourInstance = null;

    private WorkLine() {
    }

    public synchronized static WorkLine getInstance() {
        if (ourInstance == null) {
            ourInstance = new WorkLine();
        }
        return ourInstance;
    }

    public void addSource(Bitmap data){
        if(data!=null) {
            ResDataQueue.offer(data);
        }
    }


    public void addProduct(Bitmap data){
        if(data!=null) {
            ProductDataQueue.offer(data);
        }
    }

    public Bitmap getSource(Bitmap data){
        try {
            return ResDataQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap[] getSources(int size){
        return getAllBitmap(size,ResDataQueue);
    }

    private Bitmap[] getAllBitmap(int size, ArrayBlockingQueue<Bitmap> line) {
        ArrayList<Bitmap> mapList=new ArrayList<>();
        for(int i=0;i<size;i++){
            try {
                mapList.add(line.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("Controller error:",e.toString());
            }
        }
        return (Bitmap[])mapList.toArray(new Bitmap[0]);
    }


    public Bitmap play(){
        try {
            if(ProductDataQueue.size()>0){
                    return ProductDataQueue.take();
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
      return null;
    }

}
