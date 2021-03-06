package com.huyingbao.module.wan.module;

import com.huyingbao.module.wan.action.WanApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * mock注入器,为测试代码提供,方便测试的全局对象
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = {MockModule.class})
public interface MockComponent {
    WanApi getWanApi();
}
