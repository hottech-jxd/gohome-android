package com.jxd.android.gohomeapp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jxd.android.gohomeapp.databinding.ActivityMainBinding
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.fragment.CouponFragment


class MainActivity : AppCompatActivity() , View.OnClickListener {


    var activityMainBinding : ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)

        initView()


    }

    fun initView() {

        QuanModule.setMobile("15869168181")

        var couponFragment = CouponFragment.newInstance("","") as Fragment

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
