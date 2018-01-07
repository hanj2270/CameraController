package camera.hj.cameracontroller.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import camera.hj.cameracontroller.R;
import camera.hj.cameracontroller.constant.NumConstant;
import camera.hj.cameracontroller.constant.PageConstant;
import camera.hj.cameracontroller.constant.UrlConstant;
import camera.hj.cameracontroller.controller.adapter.MainItemAdapter;
import camera.hj.cameracontroller.controller.entity.SportItemEntity;
import camera.hj.cameracontroller.ui.activity.pushUp.PushUpActivity;
import camera.hj.cameracontroller.ui.activity.pushUp.PushUp_VideoActivity;

/**
 * Created by NC040 on 2017/12/22.
 */

public class SingleTrainFragment extends BaseFragment {

    @BindView(R.id.fg_main_recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;


    /**
     * 点击每个元素的时间
     */
    private long clickItemTime = 0;


    /**
     * 当前的适配器
     */
    private MainItemAdapter adapter;
    /**
     * 当前的数据信息集合
     */
    List<SportItemEntity> mDataList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.frag_single_train;
    }

    @Override
    public void initViews() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void loadData() {
        SportItemEntity sportItemEntity0 = new SportItemEntity(0, "窄距俯卧撑 力竭训练", R.drawable.train_pic_1, UrlConstant.VIDEO_URL);
        SportItemEntity sportItemEntity1 = new SportItemEntity(1, "标准深蹲", R.drawable.train_pic_2, UrlConstant.VIDEO_URL);

        mDataList.add(sportItemEntity0);
        mDataList.add(sportItemEntity1);

        adapter = new MainItemAdapter(R.layout.view_main_item, mDataList);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - clickItemTime > NumConstant.QUICK_CLICK_VALUE) {
                    clickItemTime = currentTime;
                    ARouter.getInstance()
                            .build(UrlConstant.UI_PATH_PLAY)
                            .withSerializable(PageConstant.SPROT_ITEM, mDataList.get(position))
                            .navigation();
                }
            }
        });
        //给recyclerView添加适配器
        recyclerView.setAdapter(adapter);
    }

}
