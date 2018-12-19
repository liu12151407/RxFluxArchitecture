package com.huyingbao.core.lifecycle;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.FragmentActivity;

import com.huyingbao.core.view.RxViewDispatch;
import com.huyingbao.core.store.RxStore;

import java.util.List;

/**
 * Created by liujunfeng on 2017/12/13.
 */
public class ActivityLifecycleObserver implements LifecycleObserver {
    private final Activity mActivity;

    public ActivityLifecycleObserver(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * activity创建成功之后调用,
     * 若activity是RxViewDispatch的子类,
     * 获取需要关联的RxStoreList
     * 将RxStoreList同activity生命周期关联
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        if (mActivity instanceof RxViewDispatch) {
            List<RxStore> rxStoreList = ((RxViewDispatch) mActivity).getLifecycleRxStoreList();
            if (rxStoreList != null && rxStoreList.size() > 0)
                for (RxStore rxStore : rxStoreList)
                    ((FragmentActivity) mActivity).getLifecycle().addObserver(rxStore);
        }
    }
}
