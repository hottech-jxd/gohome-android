package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.SearchGoodsBean
import com.jxd.android.gohomeapp.quanmodule.R


class SearchResultAdapter(data: ArrayList<SearchGoodsBean>) : BaseQuickAdapter<SearchGoodsBean, BaseViewHolder>( R.layout.layout_goods_item_1 , data) {

    override fun convert(helper: BaseViewHolder?, item: SearchGoodsBean) {
        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI(item.pictureUrl)
    }
}