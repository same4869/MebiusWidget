package com.pokemon.mebius.mebius_widget_recycleview.loadmorev2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pokemon.mebius.mebius_widget_recycleview.loadmorev2.IMFootContainer
import com.pokemon.mebius.mebius_widget_recycleview.loadmorev2.MDefFootContainer
import kotlin.properties.Delegates

/**
 * 加载更多adapter wrapper
 */
class MLoadMoreAdapter<T : RecyclerView.Adapter<RecyclerView.ViewHolder>>(
    val adapter: T,
    /**
     * 数据映射将[MLoadMoreAdapter]数据映射到包裹的adapter中
     */
    val adapterDataMap: (adapter: T, data: MutableList<Any>) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), IMLoadMoreAction {

    var items by Delegates.observable(mutableListOf<Any>()) { _, _, new ->
        adapterDataMap(adapter, new)
    }

    init {
        adapterDataMap(adapter, items)
    }

    companion object {
        const val TYPE_FOOT = 233333
    }

    private lateinit var footContainer: IMFootContainer
    private lateinit var recyclerView: RecyclerView
    private lateinit var context: Context

    /**
     * 是否允许加载更多
     */
    private var enableLoadMore = true

    /**
     * 脚部默认隐藏 by 朱利源
     */
    private var footVisibleOrGone = false
    private var mStatus by Delegates.observable(IMLoadMoreAction.Status.READY) { _, _, new ->
        setFootVisibleOrGone(new != IMLoadMoreAction.Status.READY) // by 朱利源
        if (::footContainer.isInitialized) footContainer.updateStatus(new)
    }
    private var footClickListener: ((IMLoadMoreAction.Status) -> Unit)? = null
    private var loadMoreListener: (() -> Unit)? = null
    private var preloadNum = 0

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            TYPE_FOOT
        } else {
            adapter.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::recyclerView.isInitialized) {
            recyclerView = parent as RecyclerView
            initLoadMoreListener(recyclerView)
        }
        if (!::context.isInitialized)
            context = parent.context

        if (viewType == TYPE_FOOT) {
            return FootHolder(footContainer.getView(context).also {
                initFootContainer()
            })
        }
        return adapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is FootHolder) adapter.onBindViewHolder(holder, position)
    }

    override fun getItemCount() = 1 + items.size

    /**
     * 初始化footContainer
     */
    private fun initFootContainer() {
        if (!::footContainer.isInitialized) {
            footContainer = MDefFootContainer()
        }
        footContainer.updateStatus(mStatus)//footView刚创建后需要调用updateStatus同步状态
        footContainer.setVisibleOrGone(footVisibleOrGone)//控制底部是否显示
        footContainer.setOnFootClickListener {
            footClickListener?.invoke(it)
        }
    }

    /**
     * 初始化加载更多监听
     */
    private fun initLoadMoreListener(recyclerView: RecyclerView) {
        RecyclerViewLoadMoreHelper(recyclerView) {
            mStatus == IMLoadMoreAction.Status.READY && enableLoadMore
        }
            .setPreloadNum(preloadNum)
            .addLoadMoreListener {
                updateStatus(IMLoadMoreAction.Status.LOADING)
                loadMoreListener?.invoke()
            }
    }

    override fun getStatus() = mStatus

    override fun updateStatus(status: IMLoadMoreAction.Status) {
        mStatus = status
    }

    override fun setOnFootClickListener(listener: (IMLoadMoreAction.Status) -> Unit) {
        footClickListener = listener
    }

    override fun setFoot(foot: IMFootContainer) {
        footContainer = foot
    }

    override fun getFoot(): IMFootContainer? {
        return if (!::footContainer.isInitialized) null else footContainer
    }

    override fun setOnLoadMoreListener(listener: () -> Unit) {
        loadMoreListener = listener
    }

    override fun setPreloadNum(num: Int) {
        preloadNum = num
    }

    override fun setFootVisibleOrGone(isShow: Boolean) {
        footVisibleOrGone = isShow
        if (::footContainer.isInitialized) footContainer.setVisibleOrGone(isShow)
    }

    override fun footerVisibleInScreen(): Boolean {
        if (!::recyclerView.isInitialized) return false
        return when (val layoutManager = recyclerView.layoutManager) {
            is LinearLayoutManager -> {
                layoutManager.findLastVisibleItemPosition() == itemCount - 1 && footVisibleOrGone
            }
            else -> false
        }
    }

    /**
     * 尝试进行加载更多
     * 目前用在有删除功能列表上，
     * 删除的时候调用该方法，
     * 当删除到列表底部foot可见时候触发加载更多，
     * 避免底部状态显示不正常情况
     */
    override fun retryLoadMoreData() {
        if (!::footContainer.isInitialized) return
        val needCheckFooterStatus = footerVisibleInScreen()
        val isReady = footContainer.getStatus() == IMLoadMoreAction.Status.READY
        if (needCheckFooterStatus && isReady) {
            loadMoreListener?.invoke()
        }
    }

    override fun setEnableLoadMore(enableLoadMore: Boolean) {
        this.enableLoadMore = enableLoadMore
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        try {
            adapter.onViewAttachedToWindow(holder)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        try {
            adapter.onViewDetachedFromWindow(holder)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        try {
            adapter.onAttachedToRecyclerView(recyclerView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        try {
            adapter.onDetachedFromRecyclerView(recyclerView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class FootHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
