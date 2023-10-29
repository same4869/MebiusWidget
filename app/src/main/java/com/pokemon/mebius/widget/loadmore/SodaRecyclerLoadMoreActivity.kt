package com.pokemon.mebius.widget.loadmore

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pokemon.mebius.mebius_widget_recycleview.adapter.MSimpleAdapter
import com.pokemon.mebius.mebius_widget_recycleview.loadmore.LoadMoreAdapter
import com.pokemon.mebius.mebius_widget_recycleview.loadmore.OnLastItemVisibleListener
import com.pokemon.mebius.widget.PersonBean
import com.pokemon.mebius.widget.PersonBean2
import com.pokemon.mebius.widget.PersonView
import com.pokemon.mebius.widget.PersonView2
import com.pokemon.mebius.widget.R
import com.pokemon.mebius.widget.databinding.ActivitySodaRecyclerLoadmoreBinding

class SodaRecyclerLoadMoreActivity : AppCompatActivity() {
    private var adapter: MSimpleAdapter<Any>? = null
    private lateinit var mBinding: ActivitySodaRecyclerLoadmoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySodaRecyclerLoadmoreBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_soda_recycler_loadmore)

        adapter = MSimpleAdapter<Any>(applicationContext).apply {
            registerMapping(PersonBean::class.java, PersonView::class.java)
            registerMapping(PersonBean2::class.java, PersonView2::class.java)
        }

        mBinding.mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mBinding.mRecyclerView.adapter =
            LoadMoreAdapter(applicationContext, adapter!!, mBinding.mRecyclerView)
        adapter?.data?.addAll(initData())
        mBinding.mRecyclerView.setOnLastItemVisibleListener(object : OnLastItemVisibleListener {
            override fun onLastItemVisible() {
                Log.d("kkkkkkkk", "onLastItemVisible")
                addData()
            }
        })

        adapter?.addHeaderView(ImageView(applicationContext).apply {
            setImageResource(R.mipmap.ic_launcher)
        })

//        adapter.addFooterView(TextView(applicationContext).apply {
//            text = "我是一个footer"
//        })
    }

    private fun addData() {
        Handler().postDelayed({
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

            adapter?.data?.addAll(list)
            adapter?.notifyDataSetChanged()
            mBinding.mRecyclerView.hideFootView()
        }, 2000)
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
}