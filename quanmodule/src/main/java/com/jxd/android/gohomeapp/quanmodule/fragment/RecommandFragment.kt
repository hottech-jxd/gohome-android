package com.jxd.android.gohomeapp.quanmodule.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import cn.iwgang.countdownview.CountdownView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.libcommon.util.AppUtil
import com.jxd.android.gohomeapp.libcommon.util.DensityUtils
import com.jxd.android.gohomeapp.libcommon.util.NetworkUtil.url
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.FrescoImageLoader
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.*
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentRecommandBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.layout_goods_coupon_item.*
import kotlinx.android.synthetic.main.quan_fragment_recommand.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecommandFragment.newInstance] factory method to
 * create an instance of this fragment.
 * 首页推荐页面
 *
 */
@Route(path = ARouterPath.QuanFragmentRecommandPath)
class RecommandFragment : BaseFragment()
    ,SwipeRefreshLayout.OnRefreshListener
    ,BaseQuickAdapter.OnItemChildClickListener
    ,BaseQuickAdapter.RequestLoadMoreListener
    ,BannerItemClickListener {

    private var category: String? = null
    //private var dataAdapter: DataAdapter?=null
    private var recommandAdapter: RecommandAdapter?=null
    //private var data= ArrayList<String>()
    private var recommands = ArrayList<MultiItemEntity>()
    private var dataBinding:QuanFragmentRecommandBinding?=null
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding=DataBindingUtil.inflate(inflater, R.layout.quan_fragment_recommand,container,false)
        dataBinding!!.goodsViewModel=ViewModelProviders.of(this).get(GoodsViewModel::class.java)
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

    override fun initView() {
        recommandAdapter= RecommandAdapter(recommands)
        recommandAdapter!!.onItemChildClickListener=this
        recommandAdapter!!.onBannerItemClickListener=this
        recommandAdapter!!.emptyView = View.inflate(context , R.layout.layout_empty , null)
        //var emptyTv= recommandAdapter!!.emptyView.findViewById<TextView>(R.id.empty_text)
        recommandAdapter!!.emptyView.findViewById<TextView>(R.id.empty_text).text="啊哦，好像没有数据哦!"
        recommandAdapter!!.isUseEmpty(false)
        recommandAdapter!!.setOnLoadMoreListener(this, recommand_recyclerView)
        recommand_recyclerView.layoutManager= GridLayoutManager(context,2)
        recommandAdapter!!.setSpanSizeLookup(object: BaseQuickAdapter.SpanSizeLookup{
            override fun getSpanSize(gridLayoutManager: GridLayoutManager?, position: Int): Int {
                if( recommandAdapter!!.getItemViewType(position) == ItemTypeEnum.BANNER.type ){
                    return 2
                }else if(recommandAdapter!!.getItemViewType(position) == ItemTypeEnum.ONE_COLLOMN_SIMPLE.type){
                    return 1
                }else if(recommandAdapter!!.getItemViewType(position)==ItemTypeEnum.ONE_ROW_COUNTDOWN.type){
                    return 2
                }else if(recommandAdapter!!.getItemViewType(position)==ItemTypeEnum.ONE_ROW_TITLE.type){
                    return 2
                }else if(recommandAdapter!!.getItemViewType(position)==ItemTypeEnum.ONE_ROW_GOODS.type){
                    return 2
                }else if(recommandAdapter!!.getItemViewType(position)==ItemTypeEnum.ONE_ROW_CAN_SCROLL_BANNER.type){
                    return 2
                }else if(recommandAdapter!!.getItemViewType(position)==ItemTypeEnum.ONE_COLLOMN_GOODS.type){
                    return 1
                }else{
                    return  1
                }
            }
        })

        recommand_recyclerView.adapter = recommandAdapter
        recommandAdapter!!.disableLoadMoreIfNotFullPage()
        recommand_recyclerView.addItemDecoration(RecommandDevider(recommandAdapter!!,context!!))
        recommand_refreshLayout.setOnRefreshListener(this)

        dataBinding!!.goodsViewModel!!.liveDataIndexResult.observe(this, Observer { it->

            recommand_refreshLayout.isRefreshing=false
            recommandAdapter!!.isUseEmpty(true)
            recommandAdapter!!.notifyItemChanged(0)
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            if (it.resultData == null || it.resultData!!.list == null || it.resultData!!.list!!.size < 1) return@Observer


            transferData(it.resultData!!.list)

            dataBinding!!.goodsViewModel!!.indexPage(page+1)
        })

        dataBinding!!.goodsViewModel!!.error.observe(this, Observer { it->
            if(TextUtils.isEmpty(it)){
                return@Observer
            }

            recommandAdapter!!.isUseEmpty(true)
            recommand_refreshLayout.isRefreshing=false
            showToast(it!!)

        })

        dataBinding!!.goodsViewModel!!.loading.observe(this, Observer { it->
            if(page>0){
                recommand_progress.visibility=View.GONE
                return@Observer
            }
            recommand_progress.visibility = if(it==null|| !it) View.GONE else View.VISIBLE
        })

        dataBinding!!.goodsViewModel!!.liveDataIndexPageResult.observe(this, Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            recommandAdapter!!.isUseEmpty(true)
            dealPage(it.resultData)

        })

        dataBinding!!.userViewModel!!.liveDataCollectResult.observe(this, Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            var position = it.resultData.toString().toInt()
            var bean = recommandAdapter!!.getItem(position)
        })

        dataBinding!!.goodsViewModel!!.liveDataGoodsShareBean.observe(this , Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            if(it.resultData==null || it.resultData!!.share==null){
                return@Observer
            }
            getCoupon(it.resultData!!.share!!)
        })
    }

    /**
     *
     */
    private fun getCoupon( shareBean:GoodsShareBean ){

        var isInstallPingDuoduo = AppUtil.checkInstallApp( context!! , Constants.PACKAGENAME_PINDUODUO )
        var url:String?=""
        if(isInstallPingDuoduo) {
            url = shareBean.mobileUrl
            if (TextUtils.isEmpty(url)) {
                url = shareBean.mobileShortUrl
            }

            if (TextUtils.isEmpty(url)) {
                url = shareBean.url
            }
            if (TextUtils.isEmpty(url)) {
                url = shareBean.shortUrl
            }

        }else{
            if (TextUtils.isEmpty(url)) {
                url = shareBean.url
            }
            if (TextUtils.isEmpty(url)) {
                url = shareBean.shortUrl
            }
        }

        if(TextUtils.isEmpty(url)) {
            showToast("缺少信息，无法领取")
            return
        }

        var intent = Intent()
        intent.action= Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse( url )
        context!!.startActivity(intent)

    }

    private fun transferData(result:ArrayList<IndexBean>?) {
        //if (result == null || result.list == null || result.list!!.size < 1) return

        var temp = result!!.sortedWith( Comparator { o1, o2 -> o1.sort.compareTo(o2.sort) })

        recommands.clear()
        for (item in temp ) {
            setTheme(item)
        }

        recommandAdapter!!.notifyDataSetChanged()

    }

    private fun setTheme(item :IndexBean){
        var mode = ThemeIndexRecommendModeEnum.valueOf(item.mode!!)

        if (mode == ThemeIndexRecommendModeEnum.singleTheme) {
            showSingleThemeUI(item)
        } else if (mode == ThemeIndexRecommendModeEnum.slide) {
            showSlideUI(item)
        //} else if (mode == ThemeIndexRecommendModeEnum.limitedTheme) {
        //    showLimitedUI(item)
        } else if (mode == ThemeIndexRecommendModeEnum.listTheme) {
            //showListUI(item)
            showRowBannerUI(item)
        } else if(mode == ThemeIndexRecommendModeEnum.couponTheme){
            showCouponUI(item)
        }else{
            showToast("样式不支持")
        }
    }

    private fun dealPage(result:IndexPageModel? ) {

        if (result == null || result.data == null) {
            recommandAdapter!!.loadMoreEnd(false)
        } else {
            var datas = result.data!!.goodsList!!
            if (datas.size < 1) {
                recommandAdapter!!.loadMoreEnd(false)
            } else {

                showListUI(result.data!!)

                recommandAdapter!!.loadMoreComplete()
                page++
            }
        }
    }

    /**
     * 单张图片显示样式
     */
    private fun showSingleThemeUI(bean:IndexBean ){
        var count = recommands.count { it-> it.itemType == ItemTypeEnum.ONE_COLLOMN_SIMPLE.type }
        var padingLeft = if( count%2 == 0 ) 10 else 5
        var paddingRight = if(count%2 ==0 ) 5 else 10

        var item= RecommandItem2( bean , padingLeft , paddingRight )
        recommands.add(item)
    }

    /**
     * 设置幻灯片样式
     * 先检查是否已经存在 幻灯片 项，如果存在，则添加数据项。
     */
    private fun showSlideUI(bean:IndexBean){
        var bannerItem:RecommandItem1?=null
        for(item in recommands){
            if(item.itemType == ItemTypeEnum.BANNER.type){
                bannerItem= item as RecommandItem1
            }
        }
        if(bannerItem==null){
            bannerItem = RecommandItem1(ArrayList())
            recommands.add(bannerItem)
        }

        bannerItem.data.add(bean)
    }

    /**
     * 限时购样式
     */
    private fun showLimitedUI(bean: IndexBean){

        if(bean.goodsList==null|| bean.goodsList!!.size<1)return

        var itemCountDown = RecommandItem3(bean.limitedTime)
        recommands.add(itemCountDown)

        for(item in bean.goodsList!!) {
            var item4 = RecommandItem4(item)
            recommands.add(item4)
        }
    }

    /**
     * 领券主题
     */
    private fun showCouponUI(bean:IndexBean){
        if(bean.goodsList==null|| bean.goodsList!!.size<1)return
        var itemCountDown = RecommandItem5(bean.name)
        recommands.add(itemCountDown)

        for(item in bean.goodsList!!) {
            var item4 = RecommandItem4(item)
            recommands.add(item4)
        }
    }

    /**
     * (一行显示多张图片的样式)
     */
    private fun showRowBannerUI(bean:IndexBean){
        if(bean.goodsList==null|| bean.goodsList!!.size<1)return

        var itemTitle = RecommandItem5(bean.name)
        recommands.add(itemTitle)


        var itemBanner= RecommandItem6( bean.goodsList!! , 10, 10 )
        recommands.add(itemBanner)
    }

    /**
     * 列表样式
     */
    private fun showListUI(bean: IndexBean){
        if(bean.goodsList==null|| bean.goodsList!!.size<1)return

        if(page==0) {
            var itemTitle = RecommandItem5(bean.name)
            recommands.add(itemTitle)
        }

        var index = 0
        for(item in bean.goodsList!!) {
            var paddingLeft = if( index %2 ==0) 10 else 5
            var paddingRight = if(index%2 == 0 ) 5 else 10
            var itemItem = RecommandItem7(item , paddingLeft , paddingRight )
            recommands.add(itemItem)

            index++
        }
    }

    private fun fetchData() {
        recommandAdapter!!.isUseEmpty(false)
        dataBinding!!.goodsViewModel!!.index()
    }

    override fun onRefresh() {
        page=0
        recommand_refreshLayout.isRefreshing = false
        fetchData()
    }

    override fun onBannerItemClicked(position: Int, bannerIndex: Int) {
        var data = (recommandAdapter!!.getItem(position) as RecommandItem1).data


        var item = data[bannerIndex]

       goto(item)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if(view!!.id == R.id.recommand_banner){
            showToast("todo")
        }else if(view.id==R.id.recommand_image_1){
            var item = (recommandAdapter!!.getItem(position) as RecommandItem2).data

            goto(item)
        }
        else if(view.id == R.id.good_item_container){

            var item  =(recommandAdapter!!.getItem(position) as RecommandItem4).data
            ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath).withString("goodsId", item.goodsId).navigation()

        }
        else if(view.id==R.id.good_item_1_container){
            var item  =(recommandAdapter!!.getItem(position) as RecommandItem7).data
            ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath).withString("goodsId", item.goodsId).navigation()
        }else if(view.id==R.id.good_item_favorite ){
            var goodsId = (recommandAdapter!!.getItem(position) as RecommandItem4).data.goodsId
            var platType = (recommandAdapter!!.getItem(position) as RecommandItem4).data.goodsSource
            dataBinding!!.userViewModel!!.collect( goodsId!! , platType)
        }else if(view.id == R.id.good_item_1_favorite){
            var goodsId = (recommandAdapter!!.getItem(position) as RecommandItem7).data.goodsId
            var platType = (recommandAdapter!!.getItem(position) as RecommandItem7).data.goodsSource
            dataBinding!!.userViewModel!!.collect( goodsId!! , platType)
        }else if(view.id==R.id.goods_coupon_item_go){
            var bean = (recommandAdapter!!.getItem(position) as RecommandItem4).data
            getGoodsCoupon(bean)
        }
    }

    private fun getGoodsCoupon(bean:GoodBean){
        if(TextUtils.isEmpty(bean.goodsId)) return
        dataBinding!!.goodsViewModel!!.getShareInfo( bean.goodsId!! , bean.goodsSource )
    }

    private fun goto(item :IndexBean){

        var category = ThemeCategoryEnum.valueOf( item.category!!)

        if( category == ThemeCategoryEnum.goodsList ) {

            var fragment = ARouter.getInstance().build(ARouterPath.QuanFragmentCategoryPath)
                .withObject("indexbean", item).navigation() as CategoryFragment

            (parentFragment!!.parentFragment as MainFragment).start(fragment)
        }else if(category==ThemeCategoryEnum.link){
            var url = item.linkUrl
            ((parentFragment!!.parentFragment)as MainFragment).start( WebFragment.newInstance(url))

        }else if(category==ThemeCategoryEnum.single){
            ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath).withString("goodsId", item.goodsId).navigation()
        }
    }

    override fun onLoadMoreRequested() {
        recommand_refreshLayout.isRefreshing=false
        dataBinding!!.goodsViewModel!!.indexPage(page+1)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RecommandFragment.
         */
        @JvmStatic
        fun newInstance(category: String) =
                RecommandFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_CATEGORY, category)
                    }
                }
    }
}
