package com.jxd.android.gohomeapp.libcommon.bean

data class UserBean (var UserId :Long ,
                     var LoginName:String ,
                     var Token:String,
                     var InviteCode:String="" ,
                     var UserToken:String="",
                     var WxHeadImg : String )