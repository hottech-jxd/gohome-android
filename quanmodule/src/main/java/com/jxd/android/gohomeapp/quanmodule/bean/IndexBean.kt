package com.jxd.android.gohomeapp.quanmodule.bean

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.bean
 * @ClassName:      IndexBean
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/11 11:48
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/11 11:48
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class IndexBean(
        var category : String?="" ,
        var goodsSource:String?="",
        var goodsId: String?="",
        var goodsList: ArrayList<GoodBean>?=null,
        /*单位：秒*/
        var limitedTime:Long=0,
        var linkUrl: String?="",
        var mode: String?="",
        var name: String?="",
        var pictureUrl:String?="",
        var sort: Int=0,
        var toCode: String?="",
        var listBannerUrl:String?="") {
}

data class IndexModel(var list:ArrayList<IndexBean>?)

data class IndexPageModel(var data:IndexBean?)