package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.libcommon.bean.IndexBean
import com.jxd.android.gohomeapp.libcommon.util.DensityUtils
import com.jxd.android.gohomeapp.libcommon.util.FrescoDraweeController
import com.jxd.android.gohomeapp.libcommon.util.FrescoDraweeListener
import com.jxd.android.gohomeapp.quanmodule.FrescoImageLoader

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentCategoryBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.quan_fragment_category.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path = ARouterPath.QuanFragmentCategoryPath)
class CategoryFragment : BaseBackFragment() , FrescoDraweeListener.ImageCallback , View.OnClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Autowired(name = "indexbean") @JvmField var indexBean:IndexBean?=null

    private var dataBinding:QuanFragmentCategoryBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        dataBinding  = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_category , container , false)
        dataBinding!!.goodsViewModel=ViewModelProviders.of(this).get(GoodsViewModel::class.java)
        dataBinding!!.clickHandler = this

        return dataBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        initView()
    }



    override fun initView() {
        super.initView()

//        var fragment = RecommandFragment.newInstance(categoryId.toString())
//        this.loadRootFragment(R.id.category_container , fragment, false, true)
        if(indexBean==null) return


        var pictureUrl = indexBean!!.pictureUrl
        //todo banner图片 需要后端开发 确认
        FrescoDraweeController.loadImage(category_Banner , DensityUtils.getScreenWidth(context!!), 100,pictureUrl,this)

        var category=Category( indexBean!!.goodsId , "" )
        var fragment = ARouter.getInstance().build(ARouterPath.QuanFragmentTabPath)
            .withObject("category",category).navigation() as TabFragment

        this.loadRootFragment(R.id.category_container , fragment , false,true)

    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        if(simpleDraweeView==null) return
        simpleDraweeView.layoutParams = LinearLayout.LayoutParams(width , height)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_x->{
                _mActivity.onBackPressed()
            }
            R.id.header_search_lay->{
                ARouter.getInstance().build(ARouterPath.QuanActivitySearch).navigation()
            }
            R.id.header_right_image->{
                (parentFragment!!.parentFragment as MainFragment).start(FavoriteFragment.newInstance("", ""))
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
