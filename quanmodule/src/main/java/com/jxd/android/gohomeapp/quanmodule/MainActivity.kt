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
import com.jxd.android.gohomeapp.quanmodule.fragment.MyFragment
import kotlinx.android.synthetic.main.layout_bottom_menu.*
import kotlinx.android.synthetic.main.quan_activity_main.*

//@Route(path=ARouterPath.QuanActivityIndexPath)
class MainActivity : BaseActivity() ,View.OnClickListener ,ViewPager.OnPageChangeListener{

    var fragments = ArrayList<BaseFragment>()
    var fragmentAdapter : AppFragmentAdapter?=null
    var activityMainBinding:QuanActivityMainBinding?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()


        initFragments()
    }

    override fun initView() {

        ARouter.getInstance().inject(this)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.quan_activity_main)
        activityMainBinding!!.setLifecycleOwner(this)
        activityMainBinding!!.clickHandler = this

//        bottom_index.setOnClickListener(this)
//        bottom_benefit.setOnClickListener(this)
//        bottom_quan.setOnClickListener(this)
//        bottom_my.setOnClickListener(this)
        main_viewPager.addOnPageChangeListener(this)
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.bottom_index->{
                activityMainBinding!!.mainViewPager.setCurrentItem(0,true)

                changeMenuIcon(0)
            }
//            R.id.bottom_benefit->{
//                activityMainBinding!!.mainViewPager.setCurrentItem(1,true)
//                changeMenuIcon(1)
//            }
//            R.id.bottom_quan->{
//                activityMainBinding!!.mainViewPager.setCurrentItem(2,true)
//                changeMenuIcon(2)
//            }
            R.id.bottom_my->{
                activityMainBinding!!.mainViewPager.setCurrentItem(1,true)
                changeMenuIcon(1)
            }
        }
    }

    private fun initFragments(){



        fragments.clear()
        var indexFragment : IndexFragment =  (ARouter.getInstance().build(ARouterPath.QuanFragmentIndexPath).navigation()) as IndexFragment
        fragments.add(indexFragment)
//        var benefitFragment:BenefitsFragment = ARouter.getInstance().build(ARouterPath.QuanFragmentBenifitPath).navigation() as BenefitsFragment
//        fragments.add(benefitFragment)
//        fragments.add(QuanFragment.newInstance())
        var myFragment = ARouter.getInstance().build(ARouterPath.QuanFragmentMyPath).navigation() as MyFragment
        fragments.add( myFragment )
//
        var titles = ArrayList<String>()
        titles.add(getString(R.string.bottom_menu_index))
        titles.add(getString(R.string.bottom_menu_my))
        fragmentAdapter = AppFragmentAdapter( supportFragmentManager , fragments , titles)
        //main_viewPager.adapter = fragmentAdapter
        activityMainBinding!!.fragmentAdapter = fragmentAdapter


//
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        changeMenuIcon(position)
    }

    private fun changeMenuIcon(index:Int) {

        bottom_index_image.setImageResource(if (index == 0) R.mipmap.home2 else R.mipmap.home)
        bottom_index_title.setTextColor(if(index==0) ContextCompat.getColor (this , R.color.bottom_menu_color) else ContextCompat.getColor(this , R.color.bottom_menu_color2 ))
        //bottom_benefit_image.setImageResource(if (index == 1) R.mipmap.benefit2 else R.mipmap.benefit)
        //bottom_benefit_title.setTextColor( if (index ==1) ContextCompat.getColor(this , R.color.textcolor) else ContextCompat.getColor(this, R.color.textcolor2) )
        //bottom_quan_image.setImageResource(if(index==2) R.mipmap.quan2 else R.mipmap.quan)
        //bottom_quan_title.setTextColor( if (index ==2) ContextCompat.getColor(this , R.color.textcolor) else ContextCompat.getColor(this, R.color.textcolor2) )
        bottom_my_image.setImageResource(if(index==1)R.mipmap.my2 else  R.mipmap.my)
        bottom_my_title.setTextColor( if (index ==1) ContextCompat.getColor(this , R.color.bottom_menu_color) else ContextCompat.getColor(this, R.color.bottom_menu_color2) )
    }

//    fun indexImage():Int{
//        return if( activityMainBinding!!.mainViewPager.currentItem==0 ) R.mipmap.home2 else R.mipmap.home
//    }
//
//    fun myImage():Int{
//        return if ( activityMainBinding!!.mainViewPager.currentItem==1) R.mipmap.my2 else R.mipmap.my
//    }


}
