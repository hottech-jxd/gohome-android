package com.jxd.android.gohomeapp.libcommon.bean

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.bean
 * @ClassName:      SharePictureInfo
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/22 13:32
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/22 13:32
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class SharePictureInfo(var goodsName:String? , var goodsSource:Int
                            , var finalPrice:String?, var price:String?
                            ,var couponPrice:String,
                            var picturePath:String?,
                            var linkUrl:String?,
                            var qrCodePath:String?) {

}