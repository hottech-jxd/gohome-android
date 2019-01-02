package com.jxd.android.gohomeapp.libcommon.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.base
 * @ClassName:      BaseApplication
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/2 9:46
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/2 9:46
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
open class BaseApplication : MultiDexApplication(){


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}