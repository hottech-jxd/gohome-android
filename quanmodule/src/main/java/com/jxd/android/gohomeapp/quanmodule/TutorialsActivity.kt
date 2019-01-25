package com.jxd.android.gohomeapp.quanmodule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gyf.barlibrary.ImmersionBar
import com.jxd.android.gohomeapp.quanmodule.base.BaseActivity
import com.jxd.android.gohomeapp.quanmodule.fragment.TutorialsFragment

class TutorialsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quan_activity_tutorials)

        ImmersionBar.with(this)
            .statusBarColor(R.color.my_status_color)
            .init()

        initView()
    }

    override fun setStatusColor(){
        ImmersionBar.with(this).statusBarColor(R.color.my_status_color)
            .init()
    }

    override fun initView() {
        loadRootFragment(R.id.tutorial_container , TutorialsFragment.newInstance("",""))
    }

}
