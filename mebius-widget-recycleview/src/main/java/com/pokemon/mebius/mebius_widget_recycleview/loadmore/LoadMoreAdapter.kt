package com.pokemon.mebius.mebius_widget_recycleview.loadmore

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pokemon.mebius.log.MLog
import com.pokemon.mebius.mebius_widget_recycleview.loadmore.FootViewType
import com.pokemon.mebius.mebius_widget_recycleview.loadmore.IFootView

class LoadMoreAdapter<out T : RecyclerView.Adapter<ViewHolder>>(
    context: Context,
    val adapter: T,
    val recycleView: LoadMoreRecycleView,
    val footview: IFootView? = null
) : RecyclerView.Adapter<ViewHolder>() {

    private val ITEM_FOOT = 1009527
    private var mFootView: IFootView? = null
    private var mIsLoading = false

    init {
        mFootView = footview
        if (mFootView == null) {
            mFootView = DefaultFootView(context)
        }
    }

    fun getIsLoading(): Boolean {
        return mIsLoading
    }

    fun showFootView(type: String) {
        footview?.showFootViewWithType(type)
        mIsLoading = type == FootViewType.LOAD_MORE
    }

    fun hideFootView() {
        footview?.hideFootView()
        mIsLoading = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        MLog.d("onCreateViewHolder viewType:$viewType ITEM_FOOT:${ITEM_FOOT}")
        return if (viewType == ITEM_FOOT) {
            FootViewVH(mFootView!!)
        } else {
            adapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        MLog.d("getItemViewType position:$position com.mihoyo.M.adapter.itemCount:${adapter.itemCount}")
        return if (position == adapter.itemCount) ITEM_FOOT else adapter.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        MLog.d("onBindViewHolder position:$position com.mihoyo.M.adapter.itemCount:${adapter.itemCount}")
        if (position < adapter.itemCount) adapter.onBindViewHolder(holder, position)

        if (position == adapter.itemCount && holder is LoadMoreAdapter<*>.FootViewVH) {
            if (recycleView.layoutManager is StaggeredGridLayoutManager) {
                val layoutParams =
                    (recycleView.layoutManager as StaggeredGridLayoutManager).generateDefaultLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                layoutParams.isFullSpan = true
                holder.itemView.layoutParams = layoutParams
            }
            holder.renderView()
        }
    }

    override fun getItemCount(): Int {
        return adapter.itemCount + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
        adapter.setHasStableIds(hasStableIds)
    }

    override fun getItemId(position: Int): Long {
        return adapter.getItemId(position)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        adapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: ViewHolder): Boolean {
        return adapter.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        adapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        adapter.onViewDetachedFromWindow(holder)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        adapter.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        adapter.unregisterAdapterDataObserver(observer)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        adapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        adapter.onDetachedFromRecyclerView(recyclerView)
    }

    inner class FootViewVH(private val footView: IFootView) :
        RecyclerView.ViewHolder(footView.getFootView()) {
        fun renderView() {
            footView.showFootViewWithType("")
        }
    }
}