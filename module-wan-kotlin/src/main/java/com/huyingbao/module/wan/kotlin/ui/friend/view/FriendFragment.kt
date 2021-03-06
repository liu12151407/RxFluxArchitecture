package com.huyingbao.module.wan.kotlin.ui.friend.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.huyingbao.core.base.view.BaseFragment
import com.huyingbao.core.common.R2
import com.huyingbao.module.wan.kotlin.R
import com.huyingbao.module.wan.kotlin.ui.friend.action.FriendActionCreator
import com.huyingbao.module.wan.kotlin.ui.friend.adapter.WebSiteAdapter
import com.huyingbao.module.wan.kotlin.ui.friend.model.WebSite
import com.huyingbao.module.wan.kotlin.ui.friend.store.FriendStore
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class FriendFragment : BaseFragment<FriendStore>() {
    @Inject
    lateinit var mActionCreator: FriendActionCreator

    @BindView(R2.id.rv_content)
    lateinit var mRvContent: RecyclerView

    private var mDataList: List<WebSite>? = null
    private var mAdapter: WebSiteAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_friend, true)
        initRecyclerView()
        initAdapter()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore!!.isCreated) {
            return
        }
        refresh()
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        mRvContent.layoutManager = LinearLayoutManager(activity)
        mRvContent.setHasFixedSize(true)
        //硬件加速
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        mDataList = ArrayList()
        mAdapter = WebSiteAdapter(mDataList)
        //view设置适配器
        mRvContent.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore!!.webSiteListData.observe(this, androidx.lifecycle.Observer { products ->
            if (products != null) {
                setData(products.data)
            }
        })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        mActionCreator.getFriendList()
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private fun setData(data: List<WebSite>?) {
        mAdapter!!.setNewData(data)
    }

    companion object {

        fun newInstance(): FriendFragment {
            return FriendFragment()
        }
    }
}
