package com.jxd.android.gohomeapp.libcommon.bean

import java.math.BigDecimal

data class UserBean (var userId : String ,
                     var nickName:String? ,
                     var money:BigDecimal,
                     var unlocked:Boolean=false ,
                     var head : String? )
