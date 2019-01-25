package com.jxd.android.gohomeapp.quanmodule.adapter

import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.bean.PictureBean
import com.jxd.android.gohomeapp.quanmodule.util.DensityUtils
import com.jxd.android.gohomeapp.quanmodule.util.FrescoDraweeController
import com.jxd.android.gohomeapp.quanmodule.util.FrescoDraweeListener


class DetailAdapter(data :ArrayList<PictureBean>)
    :BaseQuickAdapter<PictureBean,BaseViewHolder>(R.layout.layout_detail_item ,  data) , FrescoDraweeListener.ImageCallback {



    override fun convert(helper: BaseViewHolder?, item: PictureBean?) {

       var view = helper!!.getView<SimpleDraweeView>(R.id.detail_item_pic)
       var  sw = DensityUtils.getScreenWidth(mContext)
        FrescoDraweeController.loadImage( view , sw , 0, item!!.url , this)
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        if(simpleDraweeView==null) return
        simpleDraweeView.layoutParams = ConstraintLayout.LayoutParams(width , height)
    }
}