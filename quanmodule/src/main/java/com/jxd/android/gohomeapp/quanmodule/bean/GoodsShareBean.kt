package com.jxd.android.gohomeapp.quanmodule.bean

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.bean
 * @ClassName:      GoodsShareBean
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/10 11:25
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/10 11:25
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class GoodsShareBean(/**
                           * 分享文案
                           */
                          var share:String?,
                          /**
                           * 唤起微信app推广短链接
                           */
                          var  weAppWebViewShortUrl:String?,
                          /**
                           * 唤起微信app推广链接
                           */
                          var weAppWebViewUrl:String?,
                          /**
                           * 唤醒拼多多app的推广短链接
                           */
                          var mobileShortUrl:String?,
                          /**
                           * 唤醒拼多多app的推广长链接
                           */
                          var mobileUrl:String?,
                          /**
                           * 推广短链接
                          */
                          var shortUrl:String?,
                          /**
                          * 推广长链接
                          */
                         var  url:String?) {
}

data class GoodsShareModel(var share : GoodsShareBean?)