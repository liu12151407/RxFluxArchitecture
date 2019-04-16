package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * 通知View响应事件变化
 * <p>
 * 1:RxStore接收到ActionCreator方法调用完成之后发送的RxAction,处理数据之后,发送的UI响应事件,
 * RxStore通过该{@link com.huyingbao.core.arch.store.RxActivityStore#postChange(RxChange)}或者
 * {@link com.huyingbao.core.arch.store.RxFragmentStore#postChange(RxChange)}方法发送,通知View响应事件变化.
 * 2:ActionCreator通过{@link com.huyingbao.core.arch.action.RxActionCreator#postLocalChange(String)}方法直接发送,
 * 不经过RxStore,通知View响应事件变化
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxChange extends BaseEvent {
    public RxChange(@NonNull String tag) {
        super(tag);
    }

    public static RxChange newInstance(@NonNull BaseEvent baseEvent) {
        return new RxChange(baseEvent.getTag());
    }
}
