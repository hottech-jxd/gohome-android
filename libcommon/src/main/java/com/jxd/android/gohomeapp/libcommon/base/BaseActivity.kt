package com.jxd.android.gohomeapp.libcommon.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.yokeyword.fragmentation.SupportActivity

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.base
 * @ClassName:      BaseActivity
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/2 9:50
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/2 9:50
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
open abstract class BaseActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initView()
    }

    abstract fun initView()
}