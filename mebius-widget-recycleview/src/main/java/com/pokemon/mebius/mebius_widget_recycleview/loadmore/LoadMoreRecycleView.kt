package com.pokemon.mebius.mebius_widget_recycleview.loadmore

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.pokemon.mebius.log.MLog
import com.pokemon.mebius.mebius_widget_recycleview.loadmore.FootViewType
import com.pokemon.mebius.mebius_widget_recycleview.loadmore.IFootView

open class LoadMoreRecycleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var mLoadMoreAdapter: LoadMoreAdapter<Adapter<ViewHolder>>? = null
    private var mFootView: IFootView? = null
    private val mListenerHelper = ListenerHelper() //辅助监听 onLastItemVisible 事件
    private var mLastItemVisibleListener: OnLastItemVisibleListener? = null

    /**
     * 自动显示 loading 动画。即onLastItemVisible回调时
     * */
    private var mAutoShowLoadingAnim = true

    /**
     * 是否在数据小于一屏时自动调用 onLastItemVisible
     *
     * 为false的话，用户需要一个滑动操作
     * */
    private var mEnableAutoCallLastItem = true

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        if (adapter == null) return
        if (mFootView == null) mFootView = DefaultFootView(context)
        mLoadMoreAdapter = LoadMoreAdapter(context, adapter, this, mFootView!!)
        super.setAdapter(adapter)
    }

    fun setOnLastItemVisibleListener(loadMoreListener: OnLastItemVisibleListener) {
        mLastItemVisibleListener = loadMoreListener
        mListenerHelper.listenLoadMoreEvent(this)
        if (mEnableAutoCallLastItem) {
            mListenerHelper.listenRecyclerViewPageSizeLessScreen(this)
        }
    }

    fun showFootView(type: String) {
        mLoadMoreAdapter?.showFootView(type)
    }

    fun hideFootView() {
        mLoadMoreAdapter?.hideFootView()
    }

    internal fun dispatchLoadMoreEvent() {
        MLog.d("dispatchLoadMoreEvent isLoading:${mLoadMoreAdapter?.getIsLoading()}")
        if (mLoadMoreAdapter?.getIsLoading() != false) return

        if (mAutoShowLoadingAnim) mLoadMoreAdapter?.showFootView(FootViewType.LOAD_MORE)
        mLastItemVisibleListener?.onLastItemVisible()
    }

}

interface OnLastItemVisibleListener {
    fun onLastItemVisible()
}