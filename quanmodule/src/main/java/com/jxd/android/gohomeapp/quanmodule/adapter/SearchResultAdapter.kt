package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.bean.SearchGoodsBean


class SearchResultAdapter(data: ArrayList<SearchGoodsBean>) : BaseQuickAdapter<SearchGoodsBean, BaseViewHolder>( R.layout.layout_goods_item_1 , data) {

    override fun convert(helper: BaseViewHolder?, item: SearchGoodsBean) {
        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI(item.pictureUrl)

        helper.setText(R.id.good_item_1_title , item.name)
        helper.setText(R.id.good_item_1_count , "销售${item.saleAmount}件")
        helper.setText(R.id.good_item_1_coupon , "券￥${item.couponPrice}")
        helper.setText(R.id.good_item_1_final_price , item.finalPrice)
        helper.setText(R.id.good_item_1_reword , item.reward )
        helper.setText(R.id.good_item_1_price , "￥${item.price}")


    }
}