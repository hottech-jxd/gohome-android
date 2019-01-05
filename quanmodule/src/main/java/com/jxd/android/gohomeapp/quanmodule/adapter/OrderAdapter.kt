package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jxd.android.gohomeapp.libcommon.bean.OrderBean
import com.jxd.android.gohomeapp.quanmodule.R


class OrderAdapter(data:ArrayList<OrderBean>)
    :BaseQuickAdapter<OrderBean,BaseViewHolder>(R.layout.layout_order_item ,data) {

    override fun convert(helper: BaseViewHolder?, item: OrderBean?) {

    }
}