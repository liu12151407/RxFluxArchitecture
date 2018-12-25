package com.huyingbao.core.common.view;

import android.os.Bundle;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxStoreForActivity;
import com.huyingbao.core.arch.view.RxFluxActivity;
import com.huyingbao.core.arch.view.RxFluxView;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.model.CommonHttpException;
import com.huyingbao.core.common.util.ActivityUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 带有toolbar的Activity父类
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonRxActivity<T extends RxStoreForActivity> extends RxFluxActivity implements CommonView, RxFluxView {
    static {//Vector使用
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R2.id.tv_top_title)
    protected TextView mTvTopTitle;
    @BindView(R2.id.tlb_top)
    protected Toolbar mToolbarTop;
    @BindView(R2.id.abl_top)
    protected AppBarLayout mAppBarLayoutTop;

    @Nullable
    @Override
    public abstract T getRxStore();

    /**
     * 提供activity需要显示的fragment
     *
     * @return
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("RxFlux", "1.1-onCreateActivity");
        setContentView(getLayoutId());
        addFragmentNoExisting(createFragment());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_base;
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        Logger.e("2");
        EventBus.getDefault().removeStickyEvent(rxChange);
    }

    /**
     * 接收RxError，粘性
     * 该方法不经过store,
     * 由activity直接处理
     * rxflux中对错误的处理
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxError(@NonNull RxError error) {
        EventBus.getDefault().removeStickyEvent(error);
        Throwable throwable = error.getThrowable();
        // 自定义异常
        if (throwable instanceof CommonHttpException) {
            String message = ((CommonHttpException) throwable).message();
            showShortToast(message);
        } else if (throwable instanceof retrofit2.HttpException) {
            showShortToast(((retrofit2.HttpException) throwable).code() + ":服务器问题");
        } else if (throwable instanceof SocketException) {
            showShortToast("连接服务器失败，请检查网络状态和服务器地址配置！");
        } else if (throwable instanceof SocketTimeoutException) {
            showShortToast("连接服务器超时，请检查网络状态和服务器地址配置！");
        } else if (throwable instanceof UnknownHostException) {
            showShortToast("请输入正确的服务器地址！");
        } else if (throwable instanceof MalformedJsonException) {
            showShortToast("请检查网络状态！");
        } else {
            showShortToast(throwable == null ? "未知错误" : throwable.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    return true;
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTvTopTitle.setText(title == null ? getTitle() : title);
    }

    /**
     * 设置toolbar
     *
     * @param backAble true：显示返回按钮
     */
    private void setToolbar(boolean backAble) {
        //取代原本的actionbar
        setSupportActionBar(mToolbarTop);
        //设置actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        //显示右侧返回图标
        actionBar.setDisplayHomeAsUpEnabled(backAble);
        if (backAble) actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material);
        //不显示home图标
        actionBar.setDisplayShowHomeEnabled(false);
        //不显示标题
        actionBar.setDisplayShowTitleEnabled(false);
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，不显示新的Fragment
     *
     * @param fragment
     */
    protected void addFragmentNoExisting(Fragment fragment) {
        ActivityUtils.setFragment(this, R.id.fl_content, fragment,
                false, false);
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，隐藏旧的Fragment，添加新的Fragment
     *
     * @param fragment
     */
    protected void addFragmentHideExisting(Fragment fragment) {
        ActivityUtils.setFragment(this, R.id.fl_content, fragment,
                true, false);
    }

    /**
     * 无旧的Fragment，添加新的Fragment
     * 有旧的Fragment，用新的Fragment替换旧的Fragment
     *
     * @param fragment
     */
    protected void addFragmentReplactExisting(Fragment fragment) {
        ActivityUtils.setFragment(this, R.id.fl_content, fragment,
                false, true);
    }


    /**
     * @param title    需要显示的标题
     * @param backAble true:带有返回按钮，可以返回
     */
    protected void initActionBar(String title, boolean backAble) {
        //设置标题
        setTitle(title);
        // 设置toolbar
        setToolbar(backAble);
    }

    /**
     * @param title
     */
    protected void initActionBar(String title) {
        initActionBar(title, true);
    }

    protected void initActionBar() {
        initActionBar(null, true);
    }

    /**
     * 显示短暂的Toast
     *
     * @param text
     */
    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}