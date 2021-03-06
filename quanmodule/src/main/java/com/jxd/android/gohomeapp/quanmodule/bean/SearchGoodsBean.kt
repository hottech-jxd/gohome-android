package com.jxd.android.gohomeapp.quanmodule.bean

data class SearchGoodsBean(var selected:Boolean=false,
                           var goodsId : String,
                           var name :String?,
                           var price : String?,
                           var saleAmount :Long,
                           var pictureUrl :String?,
                           var couponPrice :String,
                           var finalPrice :String,
                           var isFav :String,
                           var reward :String?,
                           var source:String?)

data class SearchGoodsModel(var list :ArrayList<SearchGoodsBean>?)

data class HotSearchModel(var data:ArrayList<String>?)