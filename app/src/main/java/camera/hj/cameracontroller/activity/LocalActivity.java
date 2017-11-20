package camera.hj.cameracontroller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;

import butterknife.BindView;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.decoder.H264Decoder;
import camera.hj.cameracontroller.utils.AppManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NC040 on 2017/11/19.
 */

public class LocalActivity extends BaseActivity {

    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            final H264Decoder h264Decoder=new H264Decoder(getAssets().open("tc10.264"),surfaceview.getHolder());

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> b) throws Exception {
                h264Decoder.init();
                boolean ret=h264Decoder.start();
                b.onNext(ret);
                b.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean b) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {// do nothing
                    }

                    @Override
                    public void onComplete() {
                        h264Decoder.stop();
                        AppManager.getAppManager().removeActivity(LocalActivity.this);
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_local;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void loadData() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
