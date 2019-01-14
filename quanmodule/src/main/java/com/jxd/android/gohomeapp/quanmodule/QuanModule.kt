package com.jxd.android.gohomeapp.quanmodule

import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.backends.pipeline.Fresco
import com.jxd.android.gohomeapp.libcommon.base.BaseApplication
import com.jxd.android.gohomeapp.libcommon.bean.UserBean
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
    var userBean:UserBean?=null

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
    }





}