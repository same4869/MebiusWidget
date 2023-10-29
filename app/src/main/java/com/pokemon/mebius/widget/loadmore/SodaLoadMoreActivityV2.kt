package com.pokemon.mebius.widget.loadmore

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pokemon.mebius.widget.PersonView2
import com.pokemon.mebius.mebius_widget_recycleview.adapter.MSimpleAdapter
import com.pokemon.mebius.mebius_widget_recycleview.loadmorev2.IMLoadMoreAction
import com.pokemon.mebius.mebius_widget_recycleview.loadmorev2.MLoadMoreAdapter
import com.pokemon.mebius.widget.PersonBean
import com.pokemon.mebius.widget.PersonBean2
import com.pokemon.mebius.widget.PersonView
import com.pokemon.mebius.widget.R
import com.pokemon.mebius.widget.databinding.ActivitySodaLoadMoreV2Binding
import com.pokemon.mebius.widget.databinding.ActivitySodaRecyclerBaseAdapterBinding

/**
 * User: Rocket
 * Date: 2021/4/2
 * Time: 10:36 AM
 */
class MLoadMoreActivityV2 : AppCompatActivity() {

    private lateinit var adapter: MLoadMoreAdapter<MSimpleAdapter<Any>>
    private var loadMoreCount = 0
    private lateinit var mBinding: ActivitySodaLoadMoreV2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySodaLoadMoreV2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    private fun init() {
        adapter = MLoadMoreAdapter(MSimpleAdapter<Any>(applicationContext).apply {
            registerMapping(PersonBean::class.java, PersonView::class.java)
            registerMapping(PersonBean2::class.java, PersonView2::class.java)
        }, { adapter, data -> adapter.data = data })

        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.adapter = adapter

        adapter.items.addAll(initData())
        adapter.setPreloadNum(4)//提前4条进行加载更多
        adapter.setOnLoadMoreListener {//加载更多监听
            adapter.updateStatus(IMLoadMoreAction.Status.LOADING)//加载中状态
            addData()
        }
        adapter.setOnFootClickListener {//底部foot点击
            if (IMLoadMoreAction.Status.ERROR == it) {
                adapter.updateStatus(IMLoadMoreAction.Status.LOADING)//加载中状态
                addData()
            }
        }
    }

    private fun initData(): List<Any> {
        val list = mutableListOf<Any>()
        val personBean1 =
            PersonBean("zhangsan", 1, 18, "你好啊")
        val personBean2 =
            PersonBean("李四", 1, 17, "你好啊1")
        val personBean3 =
            PersonBean("王五", 0, 16, "你好啊2")
        val personBean4 =
            PersonBean2("赵柳", 0, 15, "你好啊3")
        val personBean5 =
            PersonBean2("孙⑦", 1, 14, "你好啊4")
        list.add(personBean1)
        list.add(personBean2)
        list.add(personBean3)
        list.add(personBean4)
        list.add(personBean5)
        list.add(personBean1)
        list.add(personBean2)
        list.add(personBean3)
        list.add(personBean4)
        list.add(personBean5)
        list.add(personBean1)
        list.add(personBean2)
        list.add(personBean3)
        list.add(personBean4)
        list.add(personBean5)
        list.add(personBean1)
        list.add(personBean2)
        list.add(personBean3)
        list.add(personBean4)
        list.add(personBean5)
        return list
    }

    private fun addData() {
        Handler().postDelayed({
            loadMoreCount++

            if (loadMoreCount == 6) {
                adapter.updateStatus(IMLoadMoreAction.Status.NO_MORE)//没有更多数据
                return@postDelayed
            }

            if (loadMoreCount % 2 == 0) {
                adapter.updateStatus(IMLoadMoreAction.Status.ERROR)//发生错误
                return@postDelayed
            }

            val list = mutableListOf<Any>()
            val personBean1 =
                PersonBean("zhangsa111n", 1, 18, "你好啊")
            val personBean2 =
                PersonBean("李四111", 1, 17, "你好啊1")
            val personBean3 =
                PersonBean("王五111", 0, 16, "你好啊2")
            val personBean4 =
                PersonBean2("赵柳111", 0, 15, "你好啊3")
            val personBean5 =
                PersonBean2("孙⑦111", 1, 14, "你好啊4")
            list.add(personBean1)
            list.add(personBean2)
            list.add(personBean3)
            list.add(personBean4)
            list.add(personBean5)
            adapter.items.addAll(list)
            adapter.notifyDataSetChanged()
            adapter.updateStatus(IMLoadMoreAction.Status.READY)//复位回到可以加载更多状态
        }, 2000)
    }
}