package com.jxd.android.gohomeapp

import com.alibaba.android.arouter.launcher.ARouter
import com.jxd.android.gohomeapp.libcommon.base.BaseApplication
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import me.yokeyword.fragmentation.Fragmentation

/**
 *
 * @Package:        com.jxd.android.gohomeapp
 * @ClassName:      App
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/2 10:08
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/2 10:08
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class App : BaseApplication()  {

    override fun onCreate() {
        super.onCreate()



        QuanModule.init(this)

    }
}