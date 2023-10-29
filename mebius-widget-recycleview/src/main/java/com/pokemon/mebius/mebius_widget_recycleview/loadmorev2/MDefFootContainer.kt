package com.pokemon.mebius.mebius_widget_recycleview.loadmorev2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pokemon.mebius.commlib.utils.onClick
import com.pokemon.mebius.commlib.utils.setVisibleOrGone
import com.pokemon.mebius.mebius_widget_recycleview.loadmorev2.IMLoadMoreAction
import com.pokemon.mebius.widget.recycleview.databinding.ViewMDefFootViewBinding

class MDefFootContainer : MFootBaseContainer() {

    private var binding: ViewMDefFootViewBinding? = null

    override fun updateStatusActual(status: IMLoadMoreAction.Status) {
        binding?.loading?.setVisibleOrGone(false)
        binding?.noMore?.setVisibleOrGone(false)
        binding?.error?.setVisibleOrGone(false)
        when (status) {
            IMLoadMoreAction.Status.READY, IMLoadMoreAction.Status.LOADING -> {
                binding?.loading?.setVisibleOrGone(true)
            }
            IMLoadMoreAction.Status.NO_MORE -> {
                binding?.noMore?.setVisibleOrGone(true)
            }
            IMLoadMoreAction.Status.ERROR -> {
                binding?.error?.setVisibleOrGone(true)
            }
        }
    }

    override fun getViewActual(context: Context): View {
        //创建view
        val newBinding = ViewMDefFootViewBinding.inflate(
            LayoutInflater.from(context), null, false
        )
        newBinding.root.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        //初始化foot点击
        newBinding.error.onClick {
            footClickListener?.invoke(currentStatus)
        }
        binding = newBinding
        return newBinding.root
    }
}