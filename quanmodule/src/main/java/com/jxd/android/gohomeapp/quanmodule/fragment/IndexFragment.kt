package com.jxd.android.gohomeapp.quanmodule.fragment

import android.content.ClipData.newIntent
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route

import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.AppFragmentAdapter

import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.quanmodule.R


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [IndexFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [IndexFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path = ARouterPath.QuanFragmentIndexPath)
class IndexFragment : BaseFragment()
        , SwipeRefreshLayout.OnRefreshListener
        , View.OnClickListener {

    var category = ArrayList<String>()
    var fragments = ArrayList<BaseFragment>()
    var fragmentAdapter : AppFragmentAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.quan_fragment_index , container,false)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        initView()
    }

    override fun initView() {
//        header_search.setOnClickListener(this)
//        header_right_image.setOnClickListener(this)
//        header_left_lay.setOnClickListener(this)
//        index_more.setOnClickListener(this)

        initFragments()

    }

//    override fun fetchData() {
//
//    }

    override fun getLayoutResourceId(): Int {
        return 0
    }


    override fun onRefresh() {

    }


    private fun initFragments(){
        category.clear()
        category.add("推荐")
        category.add("男装")
        category.add("女装")
        category.add("护肤")
        category.add("食品")
        category.add("百货")
        category.add("内衣袜子")
        category.add("数码")
        category.add("箱包配饰")
        category.add("家电")
        category.add("成人")
        category.add("家纺")
        category.add("运动")
        category.add("宠物")
        category.add("手机")

        fragments.clear()
        fragments.add(RecommandFragment.newInstance(category[0]))
        for(i in 1 until category.size){
            fragments.add( TabFragment.newInstance( category[i] ) )
        }

        fragmentAdapter = AppFragmentAdapter(this.childFragmentManager , fragments , category)
        index_viewPager.adapter = fragmentAdapter
        index_viewPager.offscreenPageLimit=3
        index_tab.setupWithViewPager(index_viewPager,true)

    }


    override fun onClick(v: View?) {
//        when(v!!.id){
//            R.id.header_search->{
//                newIntent<SearchActivity>()
//            }
//            R.id.header_right_image->{
//                newIntentForLogin<FavoriteActivity>()
//            }
//            R.id.header_left_lay->{
//                selectPlat()
//            }
//            R.id.index_more->{
//                showCategorys()
//            }
//        }
    }

    private fun showCategorys(){
//        index_category.visibility = if( index_category.visibility == View.VISIBLE ) View.GONE else View.VISIBLE
    }

    private fun selectPlat(){
//        var dialog = SelectDialog(context!!)
//        var data = ArrayList<KeyValue>()
//        data.add(KeyValue(1,"拼多多"))
//        data.add(KeyValue(2,"京东"))
//        data.add(KeyValue(3,"天猫"))
//        dialog.show(header_left_lay , data , this)
//        index_lay_platform.visibility= if( index_lay_platform.visibility == View.VISIBLE ) View.GONE else View.VISIBLE
//
//        if(index_lay_platform.visibility == View.VISIBLE) {
//            activity!!.window.attributes.alpha = 0.6f
//        }else{
//            activity!!.window.attributes.alpha=1f
//        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment IndexFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                IndexFragment().apply {

                }
    }
}
