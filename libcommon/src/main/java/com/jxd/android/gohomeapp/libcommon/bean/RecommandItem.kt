package com.jxd.android.gohomeapp.libcommon.bean

import com.chad.library.adapter.base.entity.MultiItemEntity


data class RecommandItem1( var data:ArrayList<IndexBean> )
    : MultiItemEntity {

    override fun getItemType(): Int {
        return ItemTypeEnum.BANNER.type
    }
}

data class RecommandItem2(var data : IndexBean , var paddingLeft: Int, var paddingRight:Int ) :MultiItemEntity{
    override fun getItemType(): Int {
        return ItemTypeEnum.ONE_COLLOMN_SIMPLE.type
    }
}

data class RecommandItem3(var data: Long):MultiItemEntity{

    override fun getItemType(): Int {
        return ItemTypeEnum.ONE_ROW_COUNTDOWN.type
    }
}

data class RecommandItem4(var data : GoodBean ):MultiItemEntity{

    override fun getItemType(): Int {
        return ItemTypeEnum.ONE_ROW_GOODS.type
    }
}

data class RecommandItem5(var title:String?):MultiItemEntity{
    override fun getItemType(): Int {
        return ItemTypeEnum.ONE_ROW_TITLE.type
    }
}

data class RecommandItem6(var data : ArrayList<GoodBean> , var  paddingLeft :Int ,var paddingRight: Int):MultiItemEntity{
    override fun getItemType(): Int {
        return ItemTypeEnum.ONE_ROW_CAN_SCROLL_BANNER.type
    }
}

data class RecommandItem7(var data : GoodBean ,var  paddingLeft :Int ,var paddingRight: Int):MultiItemEntity{
    override fun getItemType(): Int {
        return ItemTypeEnum.ONE_COLLOMN_GOODS.type
    }
}