package com.pokemon.mebius.mebius_widget_recycleview.loadmorev2

import androidx.recyclerview.widget.RecyclerView

/**
 * 辅助recyclerview添加加载更多监听
 */
class RecyclerViewLoadMoreHelper(
    private val recyclerView: RecyclerView,
    private val canLoadMore: () -> Boolean
) {

    private var preloadNum = 0

    /**
     * 添加加载更多监听
     */
    fun addLoadMoreListener(listener: () -> Unit): RecyclerViewLoadMoreHelper {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.adapter == null || recyclerView.layoutManager == null) return

                val itemCount: Int = recyclerView.adapter!!.itemCount
                val lastVisibleItemPosition: Int =
                    LayoutManagerUtil.getLastVisibleItemPosition(recyclerView.layoutManager!!)
                if (dy > 0 &&
                    itemCount != 0 &&
                    lastVisibleItemPosition >= itemCount - 2 - preloadNum &&
                    canLoadMore()
                ) {
                    listener.invoke()
                }
            }
        })
        return this
    }

    /**
     * 设置提前最后一个条目多少开始加载更多
     */
    fun setPreloadNum(num: Int): RecyclerViewLoadMoreHelper {
        preloadNum = num
        return this
    }

}