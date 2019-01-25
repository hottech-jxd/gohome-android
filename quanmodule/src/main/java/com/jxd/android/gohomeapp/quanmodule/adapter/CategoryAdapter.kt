package com.huotu.android.couponsleague.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.bean.Category

class CategoryAdapter(data :ArrayList<Category>) : BaseQuickAdapter<Category,BaseViewHolder>(R.layout.layout_category_item , data ) {

    override fun convert(helper: BaseViewHolder?, item: Category?) {
        //helper!!.getView<SimpleDraweeView>(R.id.category_item_logo).setImageURI(item!!.logo)
        helper!!.setText(R.id.category_item_title , item!!.name)
    }
}