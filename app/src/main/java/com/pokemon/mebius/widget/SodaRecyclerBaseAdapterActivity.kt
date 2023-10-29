package com.pokemon.mebius.widget

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pokemon.mebius.mebius_widget_recycleview.adapter.MBaseAdapter
import com.pokemon.mebius.mebius_widget_recycleview.interf.AdapterItemView
import com.pokemon.mebius.widget.databinding.ActivitySodaRecyclerBaseAdapterBinding

class SodaRecyclerBaseAdapterActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySodaRecyclerBaseAdapterBinding

    val adapter = object : MBaseAdapter<PersonBean>() {
        override fun getItemType(data: PersonBean): Int = 0

        override fun createItem(type: Int): AdapterItemView<*> = PersonView(this@SodaRecyclerBaseAdapterActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySodaRecyclerBaseAdapterBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mBinding.mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mBinding.mRecyclerView.adapter = adapter
        adapter.data.addAll(initData())

        adapter.addHeaderView(ImageView(applicationContext).apply {
            setImageResource(R.mipmap.ic_launcher)
        })

        adapter.addFooterView(TextView(applicationContext).apply {
            text = "我是一个footer"
        })
    }

    private fun initData():List<PersonBean>{
        val list = mutableListOf<PersonBean>()
        val personBean1 =
            PersonBean("zhangsan", 1, 18, "你好啊")
        val personBean2 =
            PersonBean("李四", 1, 17, "你好啊1")
        val personBean3 =
            PersonBean("王五", 0, 16, "你好啊2")
        val personBean4 =
            PersonBean("赵柳", 0, 15, "你好啊3")
        val personBean5 =
            PersonBean("孙⑦", 1, 14, "你好啊4")
        list.add(personBean1)
        list.add(personBean2)
        list.add(personBean3)
        list.add(personBean4)
        list.add(personBean5)
        return list
    }
}