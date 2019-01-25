package com.jxd.android.gohomeapp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.base.BaseActivity
import com.jxd.android.gohomeapp.quanmodule.fragment.CouponFragment


class MainActivity : BaseActivity() , View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)

        initView()


    }

    override fun initView() {

        QuanModule.setMobile("15869168181")

        var couponFragment = CouponFragment.newInstance("","") as Fragment

        this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_1 , couponFragment)
            .commit()


    }



    override fun onClick(v: View?) {
        //ARouter.getInstance().build(ARouterPath.QuanActivityIndexPath).navigation()
        //ARouter.getInstance().build("/app/test").navigation()

        //newIntent<MainActivity2>()

    }

}
