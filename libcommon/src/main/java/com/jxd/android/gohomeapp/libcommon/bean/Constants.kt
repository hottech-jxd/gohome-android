package com.jxd.android.gohomeapp.libcommon.bean

import android.os.Environment

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.bean
 * @ClassName:      Constants
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/3 11:01
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/3 11:01
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object Constants {
    //key
    var AppKey ="4165a8d240b29af3f41818d10599d0d1"
    //设备类型
    val OS_TYPE:Int=2  //系统类型 ios->1；android->2；h5->3

    //设备号
    val imei: String? = null
    //验证签名
    val sign: String? = null
    //毫秒--utc
    val timestamp: Long = 0
    //用户Id
    val userId: String? = null
    val ip: String? = null


    val PREF_SEARCH_FILENAME="pref_filename_search"
    val PREF_KEY="pref_key"


    //val APIBaseURL="http://api.mingshz.com/mock/125/api/"

    //val APIBaseURL="http://47.75.172.114:8080/api/"
    val APIBaseURL="http://10.2.2.21:8080/api/"

    const val READ_TIMEOUT :Long= 15
    const val CONNECT_TIMEOUT :Long= 15
    const val WRITE_TIMEOUT :Long= 15


    val ImageDirPath= Environment.getExternalStorageDirectory().toString()+"/quan/images/"
}