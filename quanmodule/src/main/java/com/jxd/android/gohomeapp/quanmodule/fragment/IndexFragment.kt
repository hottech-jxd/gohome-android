package com.jxd.android.gohomeapp.quanmodule.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ClipData.newIntent
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

import com.facebook.drawee.view.SimpleDraweeView
import com.gyf.barlibrary.ImmersionBar
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.AppFragmentAdapter

import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.libcommon.util.newIntent
import com.jxd.android.gohomeapp.libcommon.util.showToast
//import com.jxd.android.gohomeapp.quanmodule.DetailActivity
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.R.id.index_tab
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentIndexBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.android.synthetic.main.quan_fragment_index.*
import java.util.*
import kotlin.collections.ArrayList


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

    var categories = ArrayList<Category>()
    var fragments = ArrayList<BaseFragment>()
    var fragmentAdapter : AppFragmentAdapter?=null
    var indexBinding: QuanFragmentIndexBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            ImmersionBar.with(this).statusBarColor(R.color.default_status_color).init()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        ARouter.getInstance().inject(IndexFragment::class)

        indexBinding = DataBindingUtil.inflate(inflater ,  R.layout.quan_fragment_index , container , false )
        indexBinding!!.clickHandler = this
        //return inflater.inflate(R.layout.quan_fragment_index , container,false)

        var goodsViewModel:GoodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)
        indexBinding!!.goodsViewModel = goodsViewModel


        indexBinding!!.goodsViewModel!!.liveDataGoodsCategories
            .observe(this , Observer { it->
                if(it!!.resultCode != ApiResultCodeEnum.SUCCESS.code){
                    showToast(it.resultMsg)
                    return@Observer
                }
                if(it.list==null) return@Observer
                categories.clear()
                categories.addAll( it.list!!)
                initFragments()

            })


        return indexBinding!!.root
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

        //header_search_lay.setOnClickListener(this)


        indexBinding!!.goodsViewModel!!.getGoodsCategorys()

        //initFragments()


    }

//    override fun fetchData() {
//
//    }




    override fun onRefresh() {

    }


    private fun initFragments(){
//        category.clear()
//        category.add("推荐")
//        category.add("男装")
//        category.add("女装")
//        category.add("护肤")
//        category.add("食品")
//        category.add("百货")
//        category.add("内衣袜子")
//        category.add("数码")
//        category.add("箱包配饰")
//        category.add("家电")
//        category.add("成人")
//        category.add("家纺")
//        category.add("运动")
//        category.add("宠物")
//        category.add("手机")

        fragments.clear()
        fragments.add(RecommandFragment.newInstance("推荐"))
        for(i in 0 until categories.size){
            var tabFragment =  ARouter.getInstance()
                .build(ARouterPath.QuanFragmentTabPath)
                //.withObject("category", categories[i])
                .navigation() as TabFragment
            //fragments.add( TabFragment.newInstance( category[i] ) )
            fragments.add(tabFragment)
        }

        var titles = ArrayList( categories.map { it->it.name })
        titles.add(0 , "推荐")

        fragmentAdapter = AppFragmentAdapter(this.childFragmentManager , fragments , titles )
        indexBinding!!.fragmentadapter = fragmentAdapter
        //index_viewPager.adapter = fragmentAdapter
        index_viewPager.offscreenPageLimit=3
        index_tab.setupWithViewPager(index_viewPager,true)

    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_search_lay->{
                search()
            }
            R.id.header_right_image->{
                //newIntentForLogin<FavoriteActivity>()
                (parentFragment as MainFragment)
                    .start(FavoriteFragment.newInstance("",""))
            }
            R.id.header_left_lay->{
                selectPlat()
            }
            R.id.index_more->{
                showCategorys()
            }
        }
    }

    fun search(){
        ARouter.getInstance().build(ARouterPath.QuanActivitySearch).navigation()

    }

     fun showCategorys(){
        index_category.visibility = if( index_category.visibility == View.VISIBLE ) View.GONE else View.VISIBLE
         
         ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath)
             .withString("goodsId", "88")
             .navigation()
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
        @JvmStatic
        fun newInstance() =
                IndexFragment().apply {

                }
    }
}
