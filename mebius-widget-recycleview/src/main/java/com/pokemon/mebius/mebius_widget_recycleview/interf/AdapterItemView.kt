package com.pokemon.mebius.mebius_widget_recycleview.interf

interface AdapterItemView<T> {
    fun bindData(data: T, position: Int)
}