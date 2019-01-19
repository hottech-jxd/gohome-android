package com.jxd.android.gohomeapp.libcommon.bean

data class FavoriteBean( var selected:Boolean=false ,
                       var goodsId : String,
                       var name :String? ,
                       var price : String? ,
                       var saleAmount :Long ,
                       var pictureUrl :String? ,
                       var couponPrice :String ,
                       var finalPrice :String,
                       var isFav :String,
                       var reward :String? ,
                       var source:String?)

data class FavoriteModel(var list :ArrayList<FavoriteBean>?)