package com.huyingbao.core.arch.module;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.huyingbao.core.arch.store.RxStoreFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * 依赖注入仓库
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */

@Module
public abstract class RxFluxModule {
    /**
     * 提供Application对象
     *
     * @param application
     * @return
     */
    @Singleton
    @Binds
    abstract Context bindApplication(Application application);

    /**
     * 提供ViewModelProvider.Factory的实现类RxStoreFactory,
     * RxStoreFactory构造方法中传入需要生产的RxStore列表,
     * 对外提供可供使用的RxStore对象,
     *
     * @param rxStoreFactory
     * @return
     */
    @Singleton
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(RxStoreFactory rxStoreFactory);
}

