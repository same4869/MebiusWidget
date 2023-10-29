package com.pokemon.mebius.mebius_widget_recycleview.loadmore

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * Created by susion on 17/12/15.
 * 更精准的判断 recycler 是应该 下拉刷新 还是加载更多
 */
internal class ListenerHelper {

    var isDragToDown = false
    var lastY = 0f
    var layoutChangerListener: View.OnLayoutChangeListener? = null
    var triggerOnLastPos = 3

    @SuppressLint("ClickableViewAccessibility")
    fun listenLoadMoreEvent(loadMoreRecyclerView: LoadMoreRecycleView) {
        loadMoreRecyclerView.addOnScrollListener(object :
            androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.adapter != null && isDragToDown) {
                    val layoutManager = recyclerView.layoutManager

                    if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
                        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

                        if (lastVisiblePosition >= recyclerView.adapter!!.itemCount - triggerOnLastPos) {
                            loadMoreRecyclerView.dispatchLoadMoreEvent()
                        }
                    } else if (layoutManager is androidx.recyclerview.widget.StaggeredGridLayoutManager) {

                        val last = IntArray(layoutManager.spanCount)
                        layoutManager.findLastVisibleItemPositions(last)
                        for (i in last.indices) {
                            if (last[i] >= recyclerView.adapter!!.itemCount - triggerOnLastPos) {
                                loadMoreRecyclerView.dispatchLoadMoreEvent()
                                break
                            }
                        }

                    } else if (layoutManager is androidx.recyclerview.widget.GridLayoutManager) {
                        val last = IntArray(layoutManager.spanCount)
                        if (last[0] >= recyclerView.adapter!!.itemCount - triggerOnLastPos) {
                            loadMoreRecyclerView.dispatchLoadMoreEvent()
                        }
                    }
                }
            }
        })


        loadMoreRecyclerView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> lastY = event.y
                MotionEvent.ACTION_MOVE -> {
                    isDragToDown = (event.y - lastY) < 0
                    lastY =
                        event.y //在down action中确定lastY是不正确的，因为down事件会被dispatchTouchEvent传递到子View，子View会消耗这个事件，进而不会回传到 onTouch 。
                }
            }
            false
        }
    }


    //当数据小于一屏时，调用 onLastItemVisible(), 由于双列有bug， 目前只对 列表生效
    fun listenRecyclerViewPageSizeLessScreen(rv: LoadMoreRecycleView) {
        layoutChangerListener = object : View.OnLayoutChangeListener {
            var lastRecyclerViewHeight = -1
            val screenHeight = Resources.getSystem().displayMetrics?.heightPixels ?: 0
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {

                if (rv.layoutManager !is androidx.recyclerview.widget.LinearLayoutManager) {
                    rv.removeOnLayoutChangeListener(this)
                    return
                }

                val itemHeight = measureItemHeight(rv, rv.childCount)
                if (itemHeight < screenHeight && lastRecyclerViewHeight != itemHeight) {  // 在数据为空的情况下，recycleview默认有一个条目：LoadMore条目
                    lastRecyclerViewHeight = itemHeight
                    rv.removeOnLayoutChangeListener(this) //调用一次后删除
                    rv.dispatchLoadMoreEvent()
                } else {

                }

                //fix 不及时移除可能导致主工程一些双列 decoration 渲染出现问题
                if (itemHeight > screenHeight) {
                    rv.removeOnLayoutChangeListener(this)
                }
            }
        }
        rv.addOnLayoutChangeListener(layoutChangerListener)
    }

    fun removeAutoLoadingMoreListener(rv: LoadMoreRecycleView) {
        if (layoutChangerListener != null) {
            rv.removeOnLayoutChangeListener(layoutChangerListener)
        }
    }

    private fun measureItemHeight(rv: androidx.recyclerview.widget.RecyclerView, childCount: Int): Int {
        var itemHeight = 0
        val widthSpec = View.MeasureSpec.makeMeasureSpec(rv.width, View.MeasureSpec.AT_MOST)
        for (m in 0 until childCount) {
            val listItem = rv.layoutManager?.getChildAt(m)
            if (listItem?.layoutParams == null) {
                listItem?.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            listItem?.measure(widthSpec, 0)
            itemHeight += listItem?.measuredHeight ?: 0
        }
        return itemHeight
    }


}