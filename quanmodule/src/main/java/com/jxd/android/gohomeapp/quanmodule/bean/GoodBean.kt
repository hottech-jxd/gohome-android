package com.jxd.android.gohomeapp.quanmodule.bean

import com.jxd.android.gohomeapp.quanmodule.util.DateUtils


data class GoodBean (
   /** 劵价格*/
    var couponPrice :String? ,

    var finalPrice :String?,

   var 	goodsId :String?,
   var	name  :String?,

    var	pictureUrl  :String?,

   var	price :String?,

    var	reward :String?,

  var	saleAmount :Long,

   var	goodsSource :Int )


data class GoodsDetailModel(var detail:GoodsDetailBean?)

data class GoodsDetailBean(var goodsId: String= "" ,
                      /**
                       * 商品名称
                       */
                      var name:String?="",
                      /**
                       * 商品多张图片
                       */
                      var pictureUrls:ArrayList<String>?=null,
                      /**
                       * 价格
                       */
                      var price:String?="0.00",
                      /**
                       * 赏金
                       */
                      var reward:String?="0.00",
                      /**
                       * 销量
                       */
                      var saleAmount:Long=0,
                      /**
                       * 来源
                       */
                      var goodsSource:Int=0,
                      /**
                       * 优惠劵有效期
                       */
                      var couponExpiryDate:Long=0 ,
                      /**
                       * 劵价格
                       */
                      var couponPrice:String?="0.00",
                      /**
                       * 商品详情【图片】
                       */
                      var detail:String?="",
                      /**
                       * 劵后价格
                       */
                      var finalPrice:String?="",
                           /**
                            * 是否收藏过
                            */
                      var collect:Boolean =false     ){

    fun getCouponExpiryDateString():String{
        return DateUtils.formatDate(this.couponExpiryDate*1000)
    }
}


data class PictureBean(var url:String?)


data class GoodsOfCategory(var list :ArrayList<GoodBean>?)