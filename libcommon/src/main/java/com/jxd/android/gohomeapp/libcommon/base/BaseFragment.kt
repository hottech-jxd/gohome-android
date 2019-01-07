package com.jxd.android.gohomeapp.libcommon.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jxd.android.gohomeapp.libcommon.R
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
abstract class BaseFragment : SupportFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(activity).apply {
            setText(R.string.hello_blank_fragment)
        }
    }


    open fun initView(){

    }

    //abstract fun getLayoutResourceId():Int
}
