package com.huyingbao.test.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.util.Checks;

import org.junit.rules.ExternalResource;

/**
 * Created by liujunfeng on 2019/4/3.
 *
 * @param <A> 容纳Fragment的Activity
 * @param <F> 需要测试的Fragment
 */
public final class FragmentScenarioRule<A extends FragmentActivity, F extends Fragment> extends ExternalResource {

    private final FragmentScenarioRule.Supplier<FragmentScenario<A, F>> scenarioSupplier;
    @Nullable
    private FragmentScenario<A, F> scenario;

    /**
     * Same as {@link java.util.function.Supplier} which requires API level 24.
     *
     * @hide
     */
    interface Supplier<T> {
        T get();
    }

    public FragmentScenarioRule(
            Class<A> activityClass,
            Class<F> fragmentClass,
            Bundle args,
            FragmentFactory fragmentFactory) {
        scenarioSupplier = () -> FragmentScenario.launchInContainer(
                Checks.checkNotNull(activityClass),
                Checks.checkNotNull(fragmentClass),
                args,
                R.style.FragmentScenarioEmptyFragmentActivityTheme,
                fragmentFactory);
    }


    @Override
    protected void before() throws Throwable {
        scenario = scenarioSupplier.get();
    }

    @Override
    protected void after() {
        scenario.recreate();
    }

    /**
     * Returns {@link ActivityScenario} of the given activity class.
     *
     * @return a non-null {@link ActivityScenario} instance
     * @throws NullPointerException if you call this method while test is not running
     */
    public FragmentScenario<A, F> getScenario() {
        return Checks.checkNotNull(scenario);
    }
}
