package com.jxd.android.gohomeapp.quanmodule.adapter

import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ImageSpan
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.GoodBean
import com.jxd.android.gohomeapp.libcommon.bean.GoodsSourceEnum
import com.jxd.android.gohomeapp.quanmodule.R


class DataAdapter(data: List<GoodBean>) : BaseQuickAdapter<GoodBean, BaseViewHolder>( R.layout.layout_goods_item_1 , data) {



    override fun convert(helper: BaseViewHolder?, item: GoodBean) {
        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI(item.pictureUrl)

        //helper.setText(R.id.good_item_1_title , item.name)
        helper.setText(R.id.good_item_1_count , "销售${item.saleAmount}件")
        helper.setText(R.id.good_item_1_coupon , "券￥${item.couponPrice}")
        helper.setText(R.id.good_item_1_final_price , item.finalPrice)
        helper.setText(R.id.good_item_1_reword , item.reward )
        helper.setText(R.id.good_item_1_price , "￥${item.price}")

        helper.getView<TextView>(R.id.good_item_1_price).paintFlags = TextPaint.STRIKE_THRU_TEXT_FLAG

        var logo = R.mipmap.pinduoduo
        if (item.goodsSource == GoodsSourceEnum.PingDuoDuo.code) {
            logo = R.mipmap.pinduoduo
        } else if (item.goodsSource == GoodsSourceEnum.TaoBao.code) {
            logo = R.mipmap.taobao
        }
        val imageSpan = CenteredImageSpan( mContext , logo)
        val spannableString = SpannableString("^&&  ${item.name}")
        spannableString.setSpan(imageSpan, 0, 4, ImageSpan.ALIGN_BASELINE)
        helper.setText( R.id.good_item_1_title , spannableString)

        helper.addOnClickListener(R.id.good_item_1_favorite)
    }
}