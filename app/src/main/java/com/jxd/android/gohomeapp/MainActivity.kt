package com.jxd.android.gohomeapp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jxd.android.gohomeapp.databinding.ActivityMainBinding
//import com.jxd.android.gohomeapp.databinding.ActivityMainBinding
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseActivity
import com.jxd.android.gohomeapp.libcommon.bean.UserBean
import com.jxd.android.gohomeapp.libcommon.util.newIntent
import com.jxd.android.gohomeapp.quanmodule.MainActivity
import com.jxd.android.gohomeapp.quanmodule.MainActivity2
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.fragment.CouponFragment
import java.math.BigDecimal


class MainActivity : BaseActivity() , View.OnClickListener {


    var activityMainBinding : ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)

        initView()


    }

    override fun initView() {

        QuanModule.setMobile("15869168181")

        var couponFragment = CouponFragment.newInstance("","")

        this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_1 , couponFragment)
            .commit()



        activityMainBinding  = DataBindingUtil.setContentView(this, R.layout.activity_main )
        activityMainBinding!!.clickHandler = this
    }



    override fun onClick(v: View?) {
        //ARouter.getInstance().build(ARouterPath.QuanActivityIndexPath).navigation()
        //ARouter.getInstance().build("/app/test").navigation()

        //newIntent<MainActivity2>()




    }

}
