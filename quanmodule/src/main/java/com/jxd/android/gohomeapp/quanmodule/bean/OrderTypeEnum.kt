package com.jxd.android.gohomeapp.quanmodule.bean

enum class OrderTypeEnum(var id :Int, var desc:String){
    ALL(0,"全部"),
    PINDUODUO(1,"拼多多订单"),
    JINDONG(2,"京东订单"),

}

enum class OrderStatusEnum(var id:Int,var desc:String){
    ALL(-1,"全部"),
    PREPARE(0,"预估"),
    RECEIVED(1,"收货"),
    INVAIDAD(3,"失效"),
    ARRIVED(2,"到账"),
}