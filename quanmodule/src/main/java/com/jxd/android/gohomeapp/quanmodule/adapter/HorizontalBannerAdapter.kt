package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.bean.GoodBean
import com.jxd.android.gohomeapp.quanmodule.util.DensityUtils
import com.jxd.android.gohomeapp.quanmodule.util.FrescoDraweeController
import com.jxd.android.gohomeapp.quanmodule.util.FrescoDraweeListener

class HorizontalBannerAdapter(data:List<GoodBean>)
    :BaseQuickAdapter<GoodBean,BaseViewHolder>( R.layout.layout_horizontal_banner_item, data) , FrescoDraweeListener.ImageCallback {

    override fun convert(helper: BaseViewHolder?, item: GoodBean?) {
        var iv = helper!!.getView<SimpleDraweeView>(R.id.horizontal_banner_item_image)
        var width = DensityUtils.getScreenWidth(mContext)

        width = (width - width/6)/2

        FrescoDraweeController.loadImage(iv , width , 0 , item!!.pictureUrl , this)

        helper.setText(R.id.horizontal_banner_item_coupon, "券￥${item.couponPrice}")
        helper.setText(R.id.horizontal_banner_item_price , "￥${item.finalPrice}")
        helper.setText(R.id.horizontal_banner_item_reword , "${item.reward}")
        helper.setText(R.id.horizontal_banner_item_title , item.name)
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        if(simpleDraweeView==null) return
        var layoutParams = simpleDraweeView.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        simpleDraweeView.layoutParams =layoutParams


    }
}