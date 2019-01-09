package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.OrderBean
import com.jxd.android.gohomeapp.quanmodule.R
import java.math.BigDecimal


class OrderAdapter(data:ArrayList<OrderBean>)
    :BaseQuickAdapter<OrderBean,BaseViewHolder>(R.layout.layout_order_item ,data) {

    override fun convert(helper: BaseViewHolder?, item: OrderBean?) {

        helper!!.setText(R.id.order_item_orderNo , "订单号: "+ item!!.orderId)
        helper!!.setText(R.id.order_item_benefit , item.reward.setScale(2,BigDecimal.ROUND_HALF_UP).toString())
        helper.setText(R.id.order_item_name , item.title)
        helper.setText(R.id.order_item_time2 , if( item.grouped) item.groupTime + " 已成团" else "未成团" )
        helper.setText(R.id.order_item_spend , "消费 " + item.finalMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString() )
        helper.getView<SimpleDraweeView>(R.id.order_item_image).setImageURI(item.pictureUrl)
        helper.setText(R.id.order_item_time , item.orderTime +"下单")

    }
}