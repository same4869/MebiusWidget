package com.pokemon.mebius.mebius_widget_recycleview.loadmorev2

import android.content.Context
import android.view.View
import com.pokemon.mebius.mebius_widget_recycleview.loadmorev2.IMLoadMoreAction

interface IMFootContainer {
    /**
     * 更新footView状态
     */
    fun updateStatus(status: IMLoadMoreAction.Status)

    /**
     * 获取footView
     * getView只能viewHolder自己创建的时候调用一次，否则会产生页面展示的footer和container持有的footer不是同一个的问题，导致后续操作失效
     */
    fun getView(context: Context): View

    /**
     * footView点击监听，具体点击哪块需要自己实现
     */
    fun setOnFootClickListener(listener: (IMLoadMoreAction.Status) -> Unit)

    /**
     * 设置显示或者隐藏
     */
    fun setVisibleOrGone(isShow: Boolean)

    /**
     * 获取footView状态
     */
    fun getStatus(): IMLoadMoreAction.Status
}