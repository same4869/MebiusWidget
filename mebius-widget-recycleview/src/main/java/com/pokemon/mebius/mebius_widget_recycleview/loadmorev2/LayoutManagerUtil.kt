package com.pokemon.mebius.mebius_widget_recycleview.loadmorev2

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.util.*

class LayoutManagerUtil {

    companion object {
        fun getFirstVisibleItemPosition(layoutManager: RecyclerView.LayoutManager): Int {
            return when (getLayoutManagerType(layoutManager)) {
                LAYOUT_MANAGER_TYPE.LINEAR, LAYOUT_MANAGER_TYPE.GRID ->
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                LAYOUT_MANAGER_TYPE.STAGGERED_GRID ->
                    caseStaggeredGrid(layoutManager, true)
                else -> -1
            }
        }

        fun getLastVisibleItemPosition(layoutManager: RecyclerView.LayoutManager): Int {
            return when (getLayoutManagerType(layoutManager)) {
                LAYOUT_MANAGER_TYPE.LINEAR, LAYOUT_MANAGER_TYPE.GRID ->
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                LAYOUT_MANAGER_TYPE.STAGGERED_GRID ->
                    caseStaggeredGrid(layoutManager, false)
                else -> -1
            }
        }

        private fun getLayoutManagerType(layoutManager: RecyclerView.LayoutManager): LAYOUT_MANAGER_TYPE? {
            return when (layoutManager) {
                is GridLayoutManager -> {
                    LAYOUT_MANAGER_TYPE.GRID
                }
                is LinearLayoutManager -> {
                    LAYOUT_MANAGER_TYPE.LINEAR
                }
                is StaggeredGridLayoutManager -> {
                    LAYOUT_MANAGER_TYPE.STAGGERED_GRID
                }
                else -> {
                    throw RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager " +
                                "and StaggeredGridLayoutManager"
                    )
                }
            }
        }

        private fun caseStaggeredGrid(
            layoutManager: RecyclerView.LayoutManager,
            findFirst: Boolean
        ): Int {
            val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
            val positions = IntArray(staggeredGridLayoutManager.spanCount)
            return if (findFirst) {
                staggeredGridLayoutManager.findFirstVisibleItemPositions(positions)
                Arrays.sort(positions)
                positions[0]
            } else {
                staggeredGridLayoutManager.findLastVisibleItemPositions(positions)
                Arrays.sort(positions)
                positions[positions.size - 1]
            }
        }
    }

    private enum class LAYOUT_MANAGER_TYPE {
        LINEAR, GRID, STAGGERED_GRID
    }
}