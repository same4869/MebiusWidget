package com.pokemon.mebius.widget

import android.content.Context
import com.pokemon.mebius.mebius_widget_recycleview.adapter.MBaseAdapter
import com.pokemon.mebius.mebius_widget_recycleview.interf.AdapterItemView
import com.pokemon.mebius.mebius_widget_recycleview.interf.AdapterUIMappingProtocol.Companion.ERROR_ITEM_TYPE
import com.pokemon.mebius.mebius_widget_recycleview.views.MRvErrorView

/**
 * @Description:
 * @Author:         xwang
 * @CreateDate:     2020/8/7
 */

class PersonAdapter(
    data: MutableList<Any>,
    private val mContext: Context
) : MBaseAdapter<Any>(data) {
    private val TYPE_PERSON_1 = 1
    private val TYPE_PERSON_2 = 2

    override fun getItemType(data: Any): Int {
        return when (data) {
            is PersonBean -> TYPE_PERSON_1
            is PersonBean2 -> TYPE_PERSON_2
            else -> ERROR_ITEM_TYPE
        }
    }

    override fun createItem(type: Int): AdapterItemView<*>? {
        return when (type) {
            TYPE_PERSON_1 -> PersonView(mContext)
            TYPE_PERSON_2 -> PersonView2(mContext)
            else -> MRvErrorView(mContext)
        }
    }
}