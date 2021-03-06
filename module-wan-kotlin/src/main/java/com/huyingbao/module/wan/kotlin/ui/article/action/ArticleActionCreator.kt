package com.huyingbao.module.wan.kotlin.ui.article.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.wan.kotlin.action.WanActionCreator
import com.huyingbao.module.wan.kotlin.action.WanApi
import com.huyingbao.module.wan.kotlin.ui.article.action.ArticleAction.Companion.GET_ARTICLE_LIST
import com.huyingbao.module.wan.kotlin.ui.article.action.ArticleAction.Companion.GET_BANNER_LIST
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * rxAction创建发送管理类
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class ArticleActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager, private val mWanApi: WanApi) : WanActionCreator(rxDispatcher, rxActionManager), ArticleAction {

    override fun getArticleList(page: Int) {
        val rxAction = newRxAction(GET_ARTICLE_LIST)
        //延迟5s调用接口，测试取消操作
        val httpObservable = Observable
                .timer(5, TimeUnit.SECONDS)
                .flatMap { s -> mWanApi.getArticleList(page) }
        postHttpLoadingAction(rxAction, httpObservable)
    }

    override fun getBannerList() {
        val rxAction = newRxAction(GET_BANNER_LIST)
        //接口调用失败，自动重复调用5次，每次间隔3s
        val httpObservable = mWanApi
                .bannerList
                .retryWhen(retryAction(5, 3))
                .flatMap(verifyResponse())
        postHttpAction(rxAction, httpObservable)
    }
}
