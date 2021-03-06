package com.huyingbao.module.gan.ui.main.view;

import android.os.Bundle;

import com.huyingbao.core.base.view.BaseFragment;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.R2;
import com.huyingbao.module.gan.ui.main.action.MainAction;
import com.huyingbao.module.gan.ui.main.action.MainActionCreator;
import com.huyingbao.module.gan.ui.main.store.MainStore;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class MainFragment extends BaseFragment<MainStore> {
    @Inject
    MainActionCreator mActionCreator;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.gan_fragment_category;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTitle(R.string.gan_label_main, false);
    }

    @OnClick(R2.id.btn_category_gan)
    public void toGanModule() {
        mActionCreator.postLocalChange(MainAction.TO_GAN_MODULE);
    }

    @OnClick(R2.id.btn_category_wan)
    public void toWanModule() {
        mActionCreator.postLocalChange(MainAction.TO_WAN_MODULE);
    }

    @OnClick(R2.id.btn_category_wan_kotlin)
    public void toWanKotlinModule() {
        mActionCreator.postLocalChange(MainAction.TO_WAN_KOTLIN_MODULE);
    }
}
