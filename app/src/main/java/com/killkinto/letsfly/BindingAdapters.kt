package com.killkinto.letsfly

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.killkinto.letsfly.flight.AdapterItemsContract

class BindingAdapters {
    companion object {
        @BindingAdapter("items")
        @JvmStatic
        fun setItems(recyclerView: RecyclerView, list: List<Any>) {
            recyclerView.adapter.let {
                if (it is AdapterItemsContract) {
                    it.replaceItems(list)
                }
            }
        }
    }
}