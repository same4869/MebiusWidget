package com.pokemon.mebius.mebius_widget_recycleview.loadmore

import android.view.View

object FootViewType {
    /**
     * 加载更多中
     */
    const val LOAD_MORE = "load_more"

    /**
     * 没有更多数据了
     */
    const val END_VIEW = "end_view"

    /**
     * 默认状态
     */
    const val DEFAULT = "default"
}

interface IFootView {
    fun getFootView(): View

    fun showFootViewWithType(type: String)

    fun hideFootView()
}