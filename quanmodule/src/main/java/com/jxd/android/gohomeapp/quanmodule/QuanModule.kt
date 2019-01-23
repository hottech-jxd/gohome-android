package com.jxd.android.gohomeapp.quanmodule

import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.backends.pipeline.Fresco
import com.jxd.android.gohomeapp.libcommon.base.BaseApplication
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import com.liulishuo.filedownloader.FileDownloader
import me.yokeyword.fragmentation.Fragmentation

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule
 * @ClassName:      QuanModule
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/4 11:26
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/4 11:26
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object QuanModule {

    var app :BaseApplication?=null
    //var userBean:UserBean?=null
    var userId:String=""

    //var wechat_appId="wx7a334070f7d14aee"

    //var WechatApi: IWXAPI?=null

    var userViewModel:UserViewModel?=null

    fun init(application: BaseApplication){

        app = application

        if(BuildConfig.DEBUG){
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init( app )

        Fragmentation.builder().stackViewMode(Fragmentation.NONE).debug(BuildConfig.DEBUG).install()

        Fresco.initialize(app)

        FileDownloader.setup(app)

//        var userViewModel = UserViewModel(app!!)
//        userViewModel.getUserInfo(false)


        //WechatApi = WXAPIFactory.createWXAPI( app , wechat_appId , true )
        //WechatApi!!.registerApp( wechat_appId )


    }

    fun setMobile(mobile :String){
        this.userId = mobile
        userViewModel = UserViewModel(app!!)
        userViewModel!!.getMyIndex()//getUserInfo(false)
    }





}