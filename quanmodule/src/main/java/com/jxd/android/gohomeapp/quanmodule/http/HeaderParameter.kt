package com.jxd.android.gohomeapp.quanmodule.http

import com.jxd.android.gohomeapp.libcommon.bean.Constants


data class HeaderParameter(var appVersion: String? = null,
                           var hwid: String? = null,
                           var mobileType: String? = null,
                           var osType :Int = Constants.OS_TYPE,
                           var osVersion: String? = "",
                           var userId: String = "",
                           var userToken :String= "")