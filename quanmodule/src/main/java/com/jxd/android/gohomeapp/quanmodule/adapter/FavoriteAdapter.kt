package com.jxd.android.gohomeapp.quanmodule.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.FavoriteBean
import com.jxd.android.gohomeapp.quanmodule.R

class FavoriteAdapter(data :ArrayList<FavoriteBean>) :BaseQuickAdapter<FavoriteBean,BaseViewHolder>(R.layout.layout_favorite_item , data ) {

    override fun convert(helper: BaseViewHolder?, item: FavoriteBean?) {

        helper!!.addOnClickListener(R.id.favorite_item_circle)
        helper!!.addOnClickListener(R.id.favorite_item_container)

        helper!!.getView<SimpleDraweeView>(R.id.good_item_image).setImageURI(item!!.pictureUrl)


        helper!!.setImageResource( R.id.favorite_item_circle , if( item!!.selected ) R.mipmap.selected else R.mipmap.unselected )

        helper.setText(R.id.good_item_price , item.price)
        helper.getView<TextView>(R.id.good_item_price).paintFlags = STRIKE_THRU_TEXT_FLAG

        helper.setText(R.id.good_item_final_price , item.finalPrice)
        helper.setText(R.id.good_item_coupon , item.couponPrice)
        helper.setText(R.id.good_item_count , item.saleAmount.toString())
        helper.setText(R.id.good_item_title, item.name )
        helper.setText(R.id.good_item_reword , item.reward)

    }
}