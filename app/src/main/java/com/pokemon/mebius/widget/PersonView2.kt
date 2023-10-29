package com.pokemon.mebius.widget

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.pokemon.mebius.mebius_widget_recycleview.interf.AdapterItemView
import com.pokemon.mebius.widget.databinding.ViewRecyclerPersonItem2Binding
import com.pokemon.mebius.widget.databinding.ViewRecyclerPersonItemBinding

/**
 * @Description:
 * @Author:         xwang
 * @CreateDate:     2020/8/7
 */

class PersonView2(context: Context) : LinearLayout(context), AdapterItemView<PersonBean2> {
    private var mBinding: ViewRecyclerPersonItem2Binding

    init {
        mBinding =
            ViewRecyclerPersonItem2Binding.inflate((context as Activity).layoutInflater, this)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL
    }

    override fun bindData(data: PersonBean2, position: Int) {
        mBinding.mNameTv.text = data.name
        mBinding.mSexTv.text = data.sex.toString()
        mBinding.mContentTv.text = data.tips + "aaa  position:$position"
    }
}