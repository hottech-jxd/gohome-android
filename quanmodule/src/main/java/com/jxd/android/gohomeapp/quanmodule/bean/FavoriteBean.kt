package com.jxd.android.gohomeapp.quanmodule.bean

data class FavoriteBean( var selected:Boolean=false ,
                         var collectId:Int,
                       var goodsId : String,
                       var goodsName :String? ,
                       var goodsPrice : String? ,
                       var saleVol : String? ,
                       var picUrl :String? ,
                       var couponPrice :String? ,
                       var finalPrice :String? ,
                       var  platType:Int,
//                       var isFav :String,
                       var rewardPrice :String? ,
                       var userId :String?)

data class FavoriteModel(var list :ArrayList<FavoriteBean>?)