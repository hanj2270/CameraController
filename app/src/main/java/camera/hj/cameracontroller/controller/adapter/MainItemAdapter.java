package camera.hj.cameracontroller.controller.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import camera.hj.cameracontroller.CameraApplication;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.controller.entity.SportItemEntity;


/**
 * Created by user on 2017/12/30.
 */

public class MainItemAdapter extends BaseQuickAdapter<SportItemEntity,BaseViewHolder> {

    public MainItemAdapter(int layoutResId, @Nullable List<SportItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SportItemEntity item) {
        helper.setText(R.id.vmi_tv_sportName,item.getSportItemName());
        Drawable image=CameraApplication.getInstance().getResources().getDrawable(item.getSportImgIcon());
        helper.setImageDrawable(R.id.vmi_image,image);
    }
}
