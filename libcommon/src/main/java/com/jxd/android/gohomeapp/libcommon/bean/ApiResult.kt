package com.jxd.android.gohomeapp.libcommon.bean

data class ApiResult<T> (
    var resultCode:Int=0,
    var resultMsg:String="",
    var data:T?=null
    )



