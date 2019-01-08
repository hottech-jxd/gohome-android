package com.jxd.android.gohomeapp.quanmodule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jxd.android.gohomeapp.libcommon.base.BaseActivity
import com.jxd.android.gohomeapp.quanmodule.fragment.TutorialsFragment

class TutorialsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quan_activity_tutorials)

        initView()
    }

    override fun initView() {
        loadRootFragment(R.id.tutorial_container , TutorialsFragment.newInstance("",""))
    }
}
