package com.jxd.android.gohomeapp.quanmodule.bean

import java.math.BigDecimal

data class OrderBean (var orderId:String?,
                      var title:String?,
                      var orderTime:String?,
                      var reward:BigDecimal,
                      var finalMoney:BigDecimal,
                      var grouped:Boolean,
                      var groupTime:String?,
                      var pictureUrl:String?)

data class OrderModel(var list:ArrayList<OrderBean>?)