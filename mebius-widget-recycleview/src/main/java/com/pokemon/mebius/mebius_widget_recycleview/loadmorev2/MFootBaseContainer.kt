package com.pokemon.mebius.mebius_widget_recycleview.loadmorev2

import android.content.Context
import android.view.View
import com.pokemon.mebius.commlib.utils.setVisibleOrInvisible
import com.pokemon.mebius.log.MLog
import com.pokemon.mebius.mebius_widget_recycleview.loadmorev2.IMLoadMoreAction

/**
 * FootContainer的简单封装
 * 简化listener&status持有逻辑
 * 简化底部显示隐藏逻辑
 */
abstract class MFootBaseContainer : IMFootContainer {
    /**
     * 存储当前最新的状态
     */
    protected lateinit var currentStatus: IMLoadMoreAction.Status

    /**
     * foot点击监听
     */
    protected var footClickListener: ((IMLoadMoreAction.Status) -> Unit)? = null

    /**
     * 持有的footView
     */
    protected lateinit var footView: View

    final override fun updateStatus(status: IMLoadMoreAction.Status) {
        this.currentStatus = status
        if (!::footView.isInitialized) {
            MLog.i("the view not been created，cannot update status")
            return
        }
        updateStatusActual(status)
    }

    final override fun setOnFootClickListener(listener: (IMLoadMoreAction.Status) -> Unit) {
        this.footClickListener = listener
    }

    /**
     * getView只能viewHolder自己创建的时候调用一次，否则会产生页面展示的footer和container持有的footer不是同一个的问题，导致后续操作失效
     */
    final override fun getView(context: Context): View {
        footView = getViewActual(context)
        return footView
    }

    override fun setVisibleOrGone(isShow: Boolean) {
        if (!::footView.isInitialized) {
            MLog.i("the view not been created，cannot update display")
            return
        }
        footView.setVisibleOrInvisible(isShow)
    }

    abstract fun updateStatusActual(status: IMLoadMoreAction.Status)

    abstract fun getViewActual(context: Context): View

    override fun getStatus(): IMLoadMoreAction.Status {
        return currentStatus
    }
}
