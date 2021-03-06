package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBusEvent;

/**
 * ActionCreator直接发送过程进度事件,通知View响应,不经过RxStore
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxLoading extends EventBusEvent {
    /**
     * true：操作进行中，用于显示操作进度提示
     * false：操作结束，用于隐藏操作进度提示
     */
    private final boolean mIsLoading;

    private RxLoading(@NonNull String tag, boolean isLoading) {
        super(tag);
        mIsLoading = isLoading;
    }

    public static RxLoading newInstance(@NonNull String tag, boolean isLoading) {
        return new RxLoading(tag, isLoading);
    }

    public boolean isLoading() {
        return mIsLoading;
    }
}
