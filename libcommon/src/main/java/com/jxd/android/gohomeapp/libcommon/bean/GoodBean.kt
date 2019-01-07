package com.jxd.android.gohomeapp.libcommon.bean

data class GoodBean (var goodsId :Long,
                     var title :String ,
                     var platform : String ,
                     var goodsPrice : String ,
                     var salesVolume :String ,
                     var imgSrc :String ,
                     var images:ArrayList<String>?,
                     var couponPrice :String ,
                     var finalPrice :String,
                     var isFav :String,
                     var earnMoney :String ,
                     var time:String ,
                     var goodsIntro:String)

data class GoodsDetailBean(var goodsId: String ,
                      /**
                       * 商品名称
                       */
                      var name:String?,
                      /**
                       * 商品多张图片
                       */
                      var pictureUrls:ArrayList<String>?,
                      /**
                       * 价格
                       */
                      var price:String?,
                      /**
                       * 赏金
                       */
                      var reward:String?,
                      /**
                       * 销量
                       */
                      var saleAmount:Long,
                      /**
                       * 来源
                       */
                      var source:String?,
                      /**
                       * 优惠劵有效期
                       */
                      var couponExpiryDate:String? ,
                      /**
                       * 劵价格
                       */
                      var couponPrice:String?,
                      /**
                       * 商品详情【图片】
                       */
                      var detail:String?,
                      /**
                       * 劵后价格
                       */
                      var finalPrice:String?)


data class PictureBean(var url:String?)