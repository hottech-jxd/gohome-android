package com.jxd.android.gohomeapp.quanmodule.adapter

import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.DetailBean
import com.jxd.android.gohomeapp.libcommon.util.DensityUtils
import com.jxd.android.gohomeapp.libcommon.util.FrescoDraweeController
import com.jxd.android.gohomeapp.libcommon.util.FrescoDraweeListener
import com.jxd.android.gohomeapp.quanmodule.R


class DetailAdapter(data :ArrayList<DetailBean>)
    :BaseQuickAdapter<DetailBean,BaseViewHolder>(R.layout.layout_detail_item ,  data) , FrescoDraweeListener.ImageCallback {



    override fun convert(helper: BaseViewHolder?, item: DetailBean?) {

       var view = helper!!.getView<SimpleDraweeView>(R.id.detail_item_pic)
       var  sw = DensityUtils.getScreenWidth(mContext)
        FrescoDraweeController.loadImage( view , sw , 0, item!!.url , this)
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        if(simpleDraweeView==null) return
        simpleDraweeView.layoutParams = ConstraintLayout.LayoutParams(width , height)
    }
}