package com.jxd.android.gohomeapp.quanmodule.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.text.SpannableString
import android.text.style.ImageSpan
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.FavoriteBean
import com.jxd.android.gohomeapp.libcommon.bean.GoodsSourceEnum
import com.jxd.android.gohomeapp.quanmodule.R

class FavoriteAdapter(data :ArrayList<FavoriteBean>) :BaseQuickAdapter<FavoriteBean,BaseViewHolder>(R.layout.layout_favorite_item , data ) {

    override fun convert(helper: BaseViewHolder?, item: FavoriteBean?) {

        helper!!.addOnClickListener(R.id.favorite_item_circle)
        helper.addOnClickListener(R.id.good_item_image)

        helper.getView<SimpleDraweeView>(R.id.good_item_image).setImageURI(item!!.picUrl)


        helper.setImageResource( R.id.favorite_item_circle , if( item.selected ) R.mipmap.selected else R.mipmap.unselected )

        helper.setText(R.id.good_item_price , "￥${item.goodsPrice}" )
        helper.getView<TextView>(R.id.good_item_price).paintFlags = STRIKE_THRU_TEXT_FLAG

        helper.setText(R.id.good_item_final_price , "￥${item.finalPrice}" )
        helper.setText(R.id.good_item_count , "销量" + item.saleVol+"件")
        helper.setText(R.id.good_item_coupon , "${item.couponPrice}元券")
        //helper.setText(R.id.good_item_title, item.goodsName )
        helper.setText(R.id.good_item_reword , item.rewardPrice )


        var logo = R.mipmap.pinduoduo
        if (item.platType == GoodsSourceEnum.PingDuoDuo.code) {
            logo = R.mipmap.pinduoduo
        } else if (item.platType == GoodsSourceEnum.TaoBao.code) {
            logo = R.mipmap.taobao
        }
        val imageSpan = CenteredImageSpan( mContext , logo)
        val spannableString = SpannableString("^&&  ${item.goodsName}")
        spannableString.setSpan(imageSpan, 0, 4, ImageSpan.ALIGN_BASELINE)
        helper.setText( R.id.good_item_title , spannableString)

    }
}