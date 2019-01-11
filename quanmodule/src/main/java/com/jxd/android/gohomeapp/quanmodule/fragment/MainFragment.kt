package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentMainBinding
import kotlinx.android.synthetic.main.layout_bottom_menu.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainFragment : BaseFragment() , View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val mFragments = arrayOfNulls<BaseFragment>(2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?  ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.quan_fragment_main, container, false)

        var dataBinding :QuanFragmentMainBinding=  DataBindingUtil.inflate(inflater , R.layout.quan_fragment_main , container , false)

        dataBinding.clickHandler = this

        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //var firstFragment = findChildFragment()



        val firstFragment = findChildFragment(IndexFragment::class.java)
        if (firstFragment == null) {
            mFragments[0] = IndexFragment.newInstance()
            mFragments[1] = MyFragment.newInstance()

            loadMultipleRootFragment(
                R.id.fl_tab_container, 0,
                mFragments[0],
                mFragments[1]
            )
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment
            mFragments[1] = findChildFragment(MyFragment::class.java)
        }
    }


    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.bottom_index -> {
                showHideFragment(mFragments[0], mFragments[1])

                changeMenuIcon(0)
            }
            R.id.bottom_my -> {
                showHideFragment(mFragments[1], mFragments[0])

                changeMenuIcon(1)
            }
        }
    }

    private fun changeMenuIcon(index:Int) {

            bottom_index_image.setImageResource(if (index == 0) R.mipmap.home2 else R.mipmap.home)
            bottom_index_title.setTextColor(
                if (index == 0) ContextCompat.getColor(
                    this.context!!,
                    R.color.bottom_menu_color
                ) else ContextCompat.getColor(this.context!!, R.color.bottom_menu_color2)
            )
            //bottom_benefit_image.setImageResource(if (index == 1) R.mipmap.benefit2 else R.mipmap.benefit)
            //bottom_benefit_title.setTextColor( if (index ==1) ContextCompat.getColor(this , R.color.textcolor) else ContextCompat.getColor(this, R.color.textcolor2) )
            //bottom_quan_image.setImageResource(if(index==2) R.mipmap.quan2 else R.mipmap.quan)
            //bottom_quan_title.setTextColor( if (index ==2) ContextCompat.getColor(this , R.color.textcolor) else ContextCompat.getColor(this, R.color.textcolor2) )
            bottom_my_image.setImageResource(if (index == 1) R.mipmap.my2 else R.mipmap.my)
            bottom_my_title.setTextColor(
                if (index == 1) ContextCompat.getColor(
                    this.context!!,
                    R.color.bottom_menu_color
                ) else ContextCompat.getColor(this.context!!, R.color.bottom_menu_color2)
            )
        }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
