package camera.hj.cameracontroller.ui.activity.pushUp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.PageConstant;
import camera.hj.cameracontroller.constant.UrlConstant;
import camera.hj.cameracontroller.controller.entity.SportItemEntity;
import camera.hj.cameracontroller.controller.event.PushUpFinishEvent;
import camera.hj.cameracontroller.ui.activity.BaseActivity;
import camera.hj.cameracontroller.utils.FileUtil;

/**
 * Created by NC040 on 2017/12/26.
 */
@Route(path = UrlConstant.UI_PATH_PLAY, name = "播放视频页面")
public class PushUp_VideoActivity extends BasePushUP_Activity {
    /**
     * 横竖屏工具
     */
    /**
     * 当前是否播放
     */
    private boolean isPlay = false;

    private SportItemEntity sportItemEntity;
    /**
     * 目录
     */
    private File outFile;
    @BindView(R.id.aps_videoView)
    VideoView videoView;
    @BindView(R.id.aps_btn_play)
    Button btnPlay;
//第一次播放的页面信息
    @BindView(R.id.aps_iv_init)
    ImageView ivFirstPlay;
    @BindView(R.id.aps_iv_play)
    ImageView ivPlayBtn;
    @BindView(R.id.aps_tv_title)
    TextView tvTitle;
    @BindView(R.id.aps_iv_back)
    ImageView ivBack;


    @Override
    public int getLayoutId() {
        return R.layout.pushup_video;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);
    }


    @Override
    public void initToolBar() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            sportItemEntity = (SportItemEntity) bundle.getSerializable(PageConstant.SPROT_ITEM);
        }
        tvTitle.setText(sportItemEntity.getSportItemName());
    }

    @Override
    public void loadData() {
        initPlay();
    }


    @OnClick({R.id.aps_btn_play,R.id.aps_iv_init,R.id.aps_iv_back})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.aps_btn_play:
                ARouter.getInstance()
                        .build(UrlConstant.UI_PATH_SPORT)
                        .withString(PageConstant.NAME_SPORT, sportItemEntity.getSportItemName())
                        .navigation();
                break;
            case R.id.aps_iv_init:
                ivFirstPlay.setVisibility(View.GONE);
                ivPlayBtn.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                isPlay = true;
                videoView.start();
                break;
            case R.id.aps_iv_back:
                finish();
            default:
                break;
        }
    }


    private void initPlay() {

        //添加播放控制条,还是自定义好点
        videoView.setMediaController(new MediaController(this));

        //设置视频源播放res/raw中的文件,文件名小写字母,格式: 3gp,mp4等,flv的不一定支持;
        String playPath = "android.resource://" + getPackageName() + "/" + R.raw.pushup;
        Uri rawUri = Uri.parse(playPath);
        videoView.setVideoURI(rawUri);

        boolean flag = FileUtil.copyAssets(getApplicationContext(), "raw", PageConstant.FILE_MEDIA);
        if (flag) {
            outFile = new File(Environment.getExternalStorageDirectory(), PageConstant.FILE_MEDIA);
            File file = new File(outFile, "pushup.mp4");
            String path = file.getAbsolutePath();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            //获取第一帧图片
            Bitmap bitmap = mmr.getFrameAtTime();
            ivFirstPlay.setImageBitmap(bitmap);
            //释放资源
            mmr.release();

        }
    }


    // ---------重写事件---------
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        FileUtil.onDel(outFile, false);
    }


    public void onEventMainThread(PushUpFinishEvent event){
        this.finish();
    }
}
