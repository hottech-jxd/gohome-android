package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.FavoriteBean
import com.jxd.android.gohomeapp.quanmodule.R

class FavoriteAdapter(data :ArrayList<FavoriteBean>) :BaseQuickAdapter<FavoriteBean,BaseViewHolder>(R.layout.layout_favorite_item , data ) {

    override fun convert(helper: BaseViewHolder?, item: FavoriteBean?) {

        helper!!.addOnClickListener(R.id.favorite_item_circle)

        helper!!.getView<SimpleDraweeView>(R.id.good_item_image).setImageURI(item!!.imgSrc)


        helper!!.setImageResource( R.id.favorite_item_circle , if( item!!.selected ) R.mipmap.selected else R.mipmap.unselected )

    }
}