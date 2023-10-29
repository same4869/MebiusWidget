package com.pokemon.mebius.mebius_widget_recycleview.loadmore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.pokemon.mebius.commlib.utils.dp2px
import com.pokemon.mebius.commlib.utils.gone
import com.pokemon.mebius.commlib.utils.show
import com.pokemon.mebius.mebius_widget_recycleview.loadmore.IFootView
import com.pokemon.mebius.widget.recycleview.R

class DefaultFootView(val context: Context) : IFootView {
    var mFootView: View? = null

    override fun getFootView(): View {
        if (mFootView == null) {
            mFootView =
                LayoutInflater.from(context).inflate(R.layout.m_defalut_footer_item, null)
                    .apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            30.dp2px
                        )
                    }
        }
        return mFootView!!
    }

    override fun showFootViewWithType(type: String) {
        mFootView?.show()
    }

    override fun hideFootView() {
        mFootView?.gone()
    }

}