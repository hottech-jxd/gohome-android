package com.jxd.android.gohomeapp.quanmodule


import android.databinding.DataBindingUtil
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.AppFragmentAdapter
import com.jxd.android.gohomeapp.libcommon.base.BaseActivity
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanActivityMainBinding
import com.jxd.android.gohomeapp.quanmodule.fragment.CashFragment
import com.jxd.android.gohomeapp.quanmodule.fragment.IndexFragment
import com.jxd.android.gohomeapp.quanmodule.fragment.MainFragment
import com.jxd.android.gohomeapp.quanmodule.fragment.MyFragment
import kotlinx.android.synthetic.main.layout_bottom_menu.*
import kotlinx.android.synthetic.main.quan_activity_main.*

@Route(path=ARouterPath.QuanActivityIndexPath)
class MainActivity2 : BaseActivity() ,View.OnClickListener {

    var fragments = ArrayList<BaseFragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      
        initView()



    }



    override fun onClick(v: View?) {

    }

    override fun initView() {

        ARouter.getInstance().inject(this)

        setContentView(R.layout.quan_activity_main2)

        if (findFragment(MainFragment::class.java) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance("",""))
        }
    }





}
