package com.pokemon.mebius.mebius_widget_recycleview.adapter

import android.content.Context
import com.pokemon.mebius.mebius_widget_recycleview.interf.AdapterItemView
import com.pokemon.mebius.mebius_widget_recycleview.interf.AdapterUIMappingProtocol

class MSimpleAdapter<T : Any>(val context: Context, dataList: MutableList<T> = ArrayList()) :
    MBaseAdapter<T>(dataList) {

    private val data2TypeMap = HashMap<String, Int?>()
    private val type2ViewMap = HashMap<Int, Class<out AdapterItemView<*>>>()

    fun registerMapping(dataClass: Class<*>, viewClass: Class<out AdapterItemView<*>>) {
        val type = getTypeByHash(dataClass, viewClass)
        data2TypeMap[dataClass.name] = type
        type2ViewMap[type] = viewClass
    }

    private fun getTypeByHash(dataClass: Class<*>, viewClass: Class<out AdapterItemView<*>>): Int {
        var result = dataClass.hashCode()
        result = 31 * result + viewClass.hashCode()
        return result
    }

    override fun getItemType(data: T): Int {
        return data2TypeMap[data.javaClass.name] ?: AdapterUIMappingProtocol.ERROR_ITEM_TYPE
    }

    //注意：使用SodaSimpleAdapter时所映射的view必须包含一个context的单参数构造函数
    override fun createItem(type: Int): AdapterItemView<*>? {
        val viewClass = type2ViewMap[type] ?: return null
        val contextConstructor = viewClass.getDeclaredConstructor(Context::class.java)
        return contextConstructor.newInstance(context)
    }
}