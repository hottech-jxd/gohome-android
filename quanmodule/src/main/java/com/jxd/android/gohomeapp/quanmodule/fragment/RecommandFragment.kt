package com.jxd.android.gohomeapp.quanmodule.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
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
import cn.iwgang.countdownview.CountdownView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.libcommon.util.DensityUtils
import com.jxd.android.gohomeapp.libcommon.util.NetworkUtil.url
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.FrescoImageLoader
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.*
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentRecommandBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
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
    ,BannerItemClickListener
    ,OnBannerListener {

    private var category: String? = null
    private var dataAdapter: DataAdapter?=null
    private var recommandAdapter: RecommandAdapter?=null
    private var data= ArrayList<String>()
    private var recommands = ArrayList<MultiItemEntity>()
    private var dataBinding:QuanFragmentRecommandBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding=DataBindingUtil.inflate(inflater, R.layout.quan_fragment_recommand,container,false)
        dataBinding!!.goodsViewModel=ViewModelProviders.of(this).get(GoodsViewModel::class.java)
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

    private fun mockData():ArrayList<MultiItemEntity>{
        recommands.clear()

//        var bannerUrl = ArrayList<String>()
//        bannerUrl.add("http://image.tkcm888.com/adSet_2018-06-01_f406f8550f0f4b21b41fca881bbcb11415278577614883710.png")
//        bannerUrl.add("http://image.tkcm888.com/adSet_2018-05-31_56440f86ea1d4d60a9a4d725e26e62c015277545962763144.png")
//        bannerUrl.add("http://image.tkcm888.com/adSet_2018-05-31_a13475823f524d5f8b3b9480673e339915277602221601122.png")
//        bannerUrl.add("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png")
//        var item1 = RecommandItem1(bannerUrl)
//        recommands.add(item1)



//        var bean = GoodBean(1,"","","", "","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        var item2 = RecommandItem2(bean)
//        recommands.add(item2)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item2 = RecommandItem2(bean)
//        recommands.add(item2)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item2 = RecommandItem2(bean)
//        recommands.add(item2)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item2 = RecommandItem2(bean)
//        recommands.add(item2)
//
//        var item3 = RecommandItem3(1152021)
//        recommands.add(item3)

//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        var item4 = RecommandItem4(bean)
//        recommands.add(item4)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item4 = RecommandItem4(bean)
//        recommands.add(item4)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item4 = RecommandItem4(bean)
//        recommands.add(item4)
//
//        var item5 = RecommandItem5("厂家直供")
//        recommands.add(item5)
//
//        var goods = ArrayList<GoodBean>()
//        goods.add(bean)
//        goods.add(bean)
//        goods.add(bean)
//        goods.add(bean)
//        var item6 = RecommandItem6(goods)
//        recommands.add(item6)
//
//        var item7 = RecommandItem5("新品首发")
//        recommands.add(item7)


//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        var item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)
//        bean = GoodBean(1,"","","","","http://t00img.yangkeduo.com/t01img/images/2018-06-04/c421980286368efa5c730b0e6404f7ec.jpeg",null,"","","","","","")
//        item8 = RecommandItem7(bean)
//        recommands.add(item8)

        return recommands
    }


    override fun initView() {
//        mockData()

        recommandAdapter= RecommandAdapter(recommands)
        recommandAdapter!!.onItemChildClickListener=this
        recommandAdapter!!.onBannerItemClickListener=this
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

        //var header = LayoutInflater.from(context).inflate(R.layout.layout_recommand_header, null )
//        var bannerUrl = ArrayList<String>()
//        bannerUrl.add("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png")
//        bannerUrl.add("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png")
//        bannerUrl.add("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png")
//        var indexBanner = header.findViewById<Banner>(R.id.recommand_banner)
//        indexBanner.setImageLoader(FrescoImageLoader( indexBanner , DensityUtils.getScreenWidth(context!!)))
//        indexBanner.setImages(bannerUrl)
//        indexBanner.start()
//        dataAdapter!!.addHeaderView(header)

//        var banner = header.findViewById<Banner>(R.id.recommand_banner)
//        banner.setOnBannerListener(this)

        recommand_recyclerView.adapter = recommandAdapter //dataAdapter
        recommand_recyclerView.addItemDecoration(RecommandDevider(recommandAdapter!!,context!!))

        recommand_refreshLayout.setOnRefreshListener(this)

//        var image1 = header.findViewById<SimpleDraweeView>(R.id.recommand_image_1)
//        var image2 = header.findViewById<SimpleDraweeView>(R.id.recommand_image_2)
//        var image3 = header.findViewById<SimpleDraweeView>(R.id.recommand_image_3)
//        var image4 = header.findViewById<SimpleDraweeView>(R.id.recommand_image_4)
//        image1.setImageURI("http://app.infunpw.com/commons/images/cinema/cinema_films/3823.jpg")
//        image2.setImageURI("http://app.infunpw.com/commons/images/cinema/cinema_films/3566.jpg")
//        image3.setImageURI("http://app.infunpw.com/commons/images/cinema/cinema_films/3823.jpg")
//        image4.setImageURI("http://app.infunpw.com/commons/images/cinema/cinema_films/3757.jpg")
//
//        var countDown = header.findViewById<CountdownView>(R.id.recommand_countdown)
//        countDown.start(15454545)
//
//        var horizontalBanner = header.findViewById<RecyclerView>(R.id.recommand_banner_6)
//        horizontalBanner.layoutManager= LinearLayoutManager(context , LinearLayout.HORIZONTAL ,false )

//        var horizontalBannerUrl = ArrayList<String>()
//        horizontalBannerUrl.add("http://app.infunpw.com/commons/images/cinema/cinema_films/3823.jpg")
//        horizontalBannerUrl.add("http://app.infunpw.com/commons/images/cinema/cinema_films/3566.jpg")
//        horizontalBannerUrl.add("http://app.infunpw.com/commons/images/cinema/cinema_films/3757.jpg")
//        var horizontalBannerAdapter = HorizontalBannerAdapter(horizontalBannerUrl)
//        horizontalBanner.adapter=horizontalBannerAdapter

        dataBinding!!.goodsViewModel!!.liveDataIndexResult.observe(this, Observer { it->

            recommand_refreshLayout.isRefreshing=false

            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            transferData(it.resultData)
        })

        dataBinding!!.goodsViewModel!!.error.observe(this, Observer { it->
            if(TextUtils.isEmpty(it)){
                return@Observer
            }

            recommand_refreshLayout.isRefreshing=false
            showToast(it!!)

        })

        dataBinding!!.goodsViewModel!!.loading.observe(this, Observer { it->
            recommand_progress.visibility = if(it==null|| !it) View.GONE else View.VISIBLE
        })
    }

    private fun transferData(result:IndexModel?) {
        if (result == null || result.list == null || result.list!!.size < 1) return

        //var temp = result.list.sortWith(( o1,o2->))

        recommands.clear()
        for (item in result.list!!) {

            var mode = ThemeIndexRecommendModeEnum.valueOf(item.mode!!)

            if (mode == ThemeIndexRecommendModeEnum.singleTheme) {
                showSingleThemeUI(item)
            } else if (mode == ThemeIndexRecommendModeEnum.slide) {
                showSlideUI(item)
            } else if (mode == ThemeIndexRecommendModeEnum.limitedTheme) {
                showLimitedUI(item)
            } else if (mode == ThemeIndexRecommendModeEnum.listTheme) {
                showListUI(item)
            } else {
                showToast("样式不支持")
            }
        }

        recommandAdapter!!.notifyDataSetChanged()
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

        bannerItem.data!!.add(bean)
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
     * 列表样式
     */
    private fun showListUI(bean: IndexBean){
        if(bean.goodsList==null|| bean.goodsList!!.size<1)return


        var itemTitle = RecommandItem5(bean.name)
        recommands.add(itemTitle)


        var index = 0
        for(item in bean.goodsList!!) {
            var paddingLeft = if( index %2 ==0) 10 else 5
            var paddingRight = if(index%2 == 0 ) 5 else 10
            var itemItem = RecommandItem7(item , paddingLeft , paddingRight )
            recommands.add(itemItem)

            index++
        }

    }

    fun fetchData() {

        dataBinding!!.goodsViewModel!!.index()
    }

    override fun onRefresh() {
        fetchData()
    }

    override fun OnBannerClick(position: Int) {
        //newIntent<DetailActivity>()
        showToast("todo")
    }

    override fun onBannerItemClicked(position: Int, bannerIndex: Int) {
        var data = (recommandAdapter!!.getItem(position) as RecommandItem1).data


        var item = data[bannerIndex]

       goto(item)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if(view!!.id == R.id.recommand_banner){
            showToast("todo")
        }else if(view!!.id==R.id.recommand_image_1){
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
        }else if(view.id==R.id.good_item_favorite || view.id==R.id.good_item_1_favorite){
            showToast("todo 收藏")
        }
    }

    private fun goto(item :IndexBean){

        var category = ThemeCategoryEnum.valueOf( item.category!!)

        if( category == ThemeCategoryEnum.goodsList ) {

            var fragment = ARouter.getInstance().build(ARouterPath.QuanFragmentCategoryPath)
                .withObject("indexbean", item).navigation() as CategoryFragment

            (parentFragment!!.parentFragment as MainFragment).start(fragment)
        }else if(category==ThemeCategoryEnum.link){

            //ARouter.getInstance().build(ARouterPath.Quanf)
            var url = item.linkUrl
            ((parentFragment!!.parentFragment)as MainFragment).start( WebFragment.newInstance(url))

        }else if(category==ThemeCategoryEnum.single){
            ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath).withString("goodsId", item.goodsId).navigation()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
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
