package com.huyingbao.module.wan.ui.view;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.wan.ui.action.WanAction;
import com.huyingbao.module.wan.ui.store.WanStore;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Route(path = "/git/GitActivity")
public class GitActivity extends CommonRxActivity<WanStore> {
    @Inject
    Lazy<GitRepoFragment> mGitRepoFragmentLazy;
    @Inject
    Lazy<GitUserFragment> mGitUserFragmentLazy;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Nullable
    @Override
    public WanStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(WanStore.class);
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {
            case WanAction.TO_GIT_USER:
                addFragmentHideExisting(mGitUserFragmentLazy.get());
                break;
        }
    }

    @Override
    protected Fragment createFragment() {
        return mGitRepoFragmentLazy.get();
    }
}
