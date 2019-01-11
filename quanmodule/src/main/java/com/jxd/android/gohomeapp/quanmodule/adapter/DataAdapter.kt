package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.GoodBean
import com.jxd.android.gohomeapp.quanmodule.R


class DataAdapter(data: List<GoodBean>) : BaseQuickAdapter<GoodBean, BaseViewHolder>( R.layout.layout_goods_item_1 , data) {



    override fun convert(helper: BaseViewHolder?, item: GoodBean) {
        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI(item.pictureUrl)
    }
}