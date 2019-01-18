package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.libcommon.util.DensityUtils
import com.jxd.android.gohomeapp.libcommon.util.FrescoDraweeController
import com.jxd.android.gohomeapp.libcommon.util.FrescoDraweeListener
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.DataAdapter
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider2
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentCategoryBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.layout_column.*
import kotlinx.android.synthetic.main.quan_fragment_category.*
import kotlinx.android.synthetic.main.quan_fragment_tab.*



/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path = ARouterPath.QuanFragmentCategoryPath)
class CategoryFragment : BaseBackFragment()
    , FrescoDraweeListener.ImageCallback
    , View.OnClickListener
    , SwipeRefreshLayout.OnRefreshListener
    , BaseQuickAdapter.RequestLoadMoreListener
    , BaseQuickAdapter.OnItemChildClickListener
    , AppBarLayout.OnOffsetChangedListener
    , BaseQuickAdapter.OnItemClickListener{

    private var dataAdapter: DataAdapter?=null
    private var column_price_sort : GoodsSortEnum = GoodsSortEnum.rewardDes
    private var page=0

    @Autowired(name = "indexbean") @JvmField var indexBean:IndexBean?=null

    private var dataBinding:QuanFragmentCategoryBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)

        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        dataBinding  = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_category , container , false)
        dataBinding!!.goodsViewModel=ViewModelProviders.of(this).get(GoodsViewModel::class.java)
        dataBinding!!.clickHandler = this
        dataBinding!!.userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        return dataBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

       fetchData()
    }

    override fun onOffsetChanged(p0: AppBarLayout?, voffset: Int) {
        category_refreshview.isEnabled = voffset>=0
    }

    override fun initView() {
        super.initView()


        category_refreshview.setOnRefreshListener (this)

        category_refreshview.setProgressViewOffset(true, -20, 100)



        category_appbarlayout.addOnOffsetChangedListener(this)



        if(indexBean==null) return


        dataAdapter=DataAdapter(ArrayList())
        dataAdapter!!.emptyView = View.inflate( context , R.layout.layout_empty , null)
        dataAdapter!!.emptyView.findViewById<TextView>(R.id.empty_text).text="啊哦，好像没有数据哦!"
        dataAdapter!!.isUseEmpty(false)
        dataAdapter!!.onItemClickListener =this
        dataAdapter!!.setOnLoadMoreListener(this, tab_recyclerview_list)
        category_recyclerview_list.layoutManager= GridLayoutManager(context,2)
        category_recyclerview_list.adapter=dataAdapter
        category_recyclerview_list.addItemDecoration( ItemDevider2(context!! , 12f , R.color.white ) )



        var pictureUrl = indexBean!!.pictureUrl
        //todo banner图片 需要后端开发 确认
        FrescoDraweeController.loadImage(category_Banner , DensityUtils.getScreenWidth(context!!), 100,pictureUrl,this)

        //var category=Category( indexBean!!.goodsId , "" )


        //var fragment = ARouter.getInstance().build(ARouterPath.QuanFragmentTabPath)
        //    .withObject("category",category).navigation() as TabFragment

        //this.loadRootFragment(R.id.category_container , fragment , false,true)

        dataBinding!!.goodsViewModel!!.liveDataThemeListResult.observe(this, Observer { it->
                category_refreshview.isRefreshing=false
                if(it!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
                    showToast(it.resultMsg)
                    return@Observer
                }

                var datas: ArrayList<GoodBean>?
                if (it.resultData == null || it.resultData!!.list==null ) {
                    dataAdapter!!.loadMoreEnd(false)
                } else {
                    datas = it.resultData!!.list!!
                    if (  datas.size < 1) {
                        dataAdapter!!.loadMoreEnd(false)
                    } else {
                        dataAdapter!!.loadMoreComplete()
                        page++
                    }
                    dataAdapter!!.addData(datas)
                }

            })

        dataBinding!!.goodsViewModel!!.error.observe(this, Observer { it->
            if(TextUtils.isEmpty(it)){
                return@Observer
            }

            category_refreshview.isRefreshing=false
            showToast(it!!)
        })

        dataBinding!!.goodsViewModel!!.loading.observe(this, Observer { it->

            if(page>0){//表示加载更多
                category_progress.visibility = View.GONE
                return@Observer
            }

            category_progress.visibility = if(it==null|| !it) View.GONE else View.VISIBLE
        })


        dataBinding!!.userViewModel!!.liveDataCollectResult.observe(this, Observer { it->

            showToast(it!!.resultMsg)

        })

    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        if(simpleDraweeView==null) return
        simpleDraweeView.layoutParams =  CollapsingToolbarLayout.LayoutParams(width , height)
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
            R.id.column_lay_price->{
                if(column_price_sort==GoodsSortEnum.priceDes ) {
                    column_price_icon.setImageResource(R.mipmap.up1)
                    column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color_selected))
                    column_price_sort=GoodsSortEnum.priceAsc
                }else{
                    column_price_icon.setImageResource(R.mipmap.down1)
                    column_price_sort=GoodsSortEnum.priceDes
                    column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color_selected))
                }

                column_commission.setTextColor( ContextCompat.getColor(context!! , R.color.column_text_color) )
                column_news.setTextColor(ContextCompat.getColor(context!!, R.color.column_text_color))
                column_sales.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))


                onRefresh()
            }
            R.id.column_commission->{
                column_price_sort = GoodsSortEnum.rewardDes
                column_commission.setTextColor( ContextCompat.getColor(context!! , R.color.column_text_color_selected) )

                column_news.setTextColor(ContextCompat.getColor(context!!, R.color.column_text_color))
                column_price_icon.setImageResource(R.mipmap.updown)
                column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))
                column_sales.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))

                onRefresh()
            }
            R.id.column_news->{
                column_price_sort = GoodsSortEnum.newed
                column_commission.setTextColor( ContextCompat.getColor(context!! , R.color.column_text_color) )

                column_news.setTextColor(ContextCompat.getColor(context!!, R.color.column_text_color_selected))
                column_price_icon.setImageResource(R.mipmap.updown)
                column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))
                column_sales.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))

                onRefresh()
            }
            R.id.column_sales->{
                column_price_sort = GoodsSortEnum.saleDes
                column_commission.setTextColor( ContextCompat.getColor(context!! , R.color.column_text_color) )

                column_news.setTextColor(ContextCompat.getColor(context!!, R.color.column_text_color))
                column_price_icon.setImageResource(R.mipmap.updown)
                column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))
                column_sales.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color_selected))

                onRefresh()
            }
        }
    }

    override fun onRefresh() {
        page=0
        dataAdapter!!.setNewData(ArrayList())
        fetchData()
    }

    private fun fetchData() {
        category_refreshview.isRefreshing=false
        if(indexBean==null) return

        dataBinding!!.goodsViewModel!!.getThemeList( indexBean!!.goodsSource
            , indexBean!!.toCode , column_price_sort , page+1)
    }

    override fun onLoadMoreRequested() {
        if( indexBean ==null){
            return
        }
        category_refreshview.isRefreshing=false
        dataBinding!!.goodsViewModel!!.getThemeList( indexBean!!.goodsSource , indexBean!!.toCode , column_price_sort , page+1)

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var bean = dataAdapter!!.getItem(position)
        ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath).withString( "goodsId", bean!!.goodsId ).navigation()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view!!.id){
            R.id.good_item_1_favorite->{

                var goodsId = dataAdapter!!.getItem(position)!!.goodsId
                if(TextUtils.isEmpty(goodsId)) return
                dataBinding!!.userViewModel!!.collect(goodsId!!)
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

                }
            }
    }
}
