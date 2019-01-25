package com.jxd.android.gohomeapp.quanmodule.bean

data class ApiResult<T> (
    var resultCode:Int=0,
    var resultMsg:String="",
    //var list:T?=null,
    //var detail:T?=null,
    //var data:T?=null,
    //var share:T?=null,
    var resultData:T?=null
    )




