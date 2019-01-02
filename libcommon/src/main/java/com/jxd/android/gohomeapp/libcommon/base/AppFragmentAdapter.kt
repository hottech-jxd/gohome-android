package com.jxd.android.gohomeapp.libcommon.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.base
 * @ClassName:      AppFragmentAdapter
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/2 11:18
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/2 11:18
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class AppFragmentAdapter( fragmentManager: FragmentManager ,var  fragments:ArrayList<BaseFragment>? , var titles:ArrayList<String>?)
    : FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(position : Int): Fragment {
        return fragments!![position]
    }

    override fun getCount(): Int {
        return if( fragments==null)  0 else fragments!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        //return super.getPageTitle(position)
        return titles!![position]
    }
}