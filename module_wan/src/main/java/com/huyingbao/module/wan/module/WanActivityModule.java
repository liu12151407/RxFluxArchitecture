package com.huyingbao.module.wan.module;


import com.huyingbao.core.arch.scope.FragmentScope;
import com.huyingbao.module.wan.ui.view.ArticleListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class WanActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract ArticleListFragment injectGitRepoListFragment();
}
