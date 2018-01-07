package camera.hj.cameracontroller.utils;


import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2017/5/17.
 * 倒计时
 */

public class RxCountDown {

    private RxCountDown() {
        throw new AssertionError();
    }


    /**
     * 倒计时的展现
     * @param time
     * @return
     */
    public static Flowable<Integer> countDown(int time) {
        if (time < 0) {
            time = 0;
        }
        final int countTime = time;
        return Flowable.interval(0, 1, TimeUnit.SECONDS)
                .take(countTime + 1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                });


    }


}
