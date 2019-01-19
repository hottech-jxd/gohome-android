package com.jxd.android.gohomeapp.libcommon.bean

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.bean
 * @ClassName:      Globalbean
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/15 9:10
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/15 9:10
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class Globalbean(var helpVideoUrl:String?) {
}

data class GlobalModel(var global:Globalbean?)

data class MessageModel(var list:ArrayList<String>?)