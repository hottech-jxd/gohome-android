package com.jxd.android.gohomeapp.libcommon.bean

data class ApiResult<T> (
    var resultCode:Int=0,
    var resultMsg:String="",
    var list:T?=null,
    var detail:T?=null,
    var data:T?=null,
    var share:T?=null
    )




