package com.jxd.android.gohomeapp.quanmodule.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.quanmodule.R


import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.couponsleague.adapter.CategoryAdapter
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.adapter.DataAdapter
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider2
import com.jxd.android.gohomeapp.quanmodule.adapter.RecommandDevider
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentTabBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.layout_column.*
import kotlinx.android.synthetic.main.quan_fragment_recommand.*
import kotlinx.android.synthetic.main.quan_fragment_tab.*


const val ARG_CATEGORY = "category"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TabFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TabFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path = ARouterPath.QuanFragmentTabPath )
class TabFragment : BaseFragment() ,View.OnClickListener
    , SwipeRefreshLayout.OnRefreshListener
    , BaseQuickAdapter.RequestLoadMoreListener
    , BaseQuickAdapter.OnItemChildClickListener
    , BaseQuickAdapter.OnItemClickListener{

    @Autowired(name = "category") @JvmField var category: Category? = null

    private var categoryList=ArrayList<Category>()
    private var categoryAdapter: CategoryAdapter?=null
    private var dataList =ArrayList<GoodBean>()
    private var dataAdapter: DataAdapter?=null
    private var column_price_sort :GoodsSortEnum = GoodsSortEnum.RewardDes
    private var page=0
    private var dataBinding:QuanFragmentTabBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            //category = it.getString(ARG_CATEGORY)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.quan_fragment_tab , container,false)
        dataBinding!!.clickHandler = this
        dataBinding!!.goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)

        return dataBinding!!.root
    }

    override fun onRefresh() {
        page=0
        dataAdapter!!.setNewData(ArrayList())
        fetchData()
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
        column_lay_price.setOnClickListener(this)

        tab_refreshview.setOnRefreshListener (this)

        categoryAdapter = CategoryAdapter(categoryList)
//        tab_recyclerview_class.layoutManager = GridLayoutManager(context , 4)
//        tab_recyclerview_class.adapter = categoryAdapter
        dataAdapter=DataAdapter(dataList)
        dataAdapter!!.onItemClickListener =this
        tab_recyclerview_list.layoutManager=GridLayoutManager(context,2)
        tab_recyclerview_list.adapter=dataAdapter
        tab_recyclerview_list.addItemDecoration( ItemDevider2(context!! , 12f , R.color.white ) )


        dataBinding!!.goodsViewModel!!.liveDataGoodsOfCategory.observe(this,
            Observer { it->
                tab_refreshview.isRefreshing=false
                if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                    showToast(it.resultMsg)
                    return@Observer
                }

                var datas: ArrayList<GoodBean>? = null
                if (it.list == null) {
                    dataAdapter!!.loadMoreEnd(false)
                } else {
                    datas = it.list!!
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

            tab_refreshview.isRefreshing=false
            showToast(it!!)
        })

        dataBinding!!.goodsViewModel!!.loading.observe(this, Observer { it->
            tab_progress.visibility = if(it==null|| !it) View.GONE else View.VISIBLE
        })
    }

    fun fetchData() {
//        categoryList.clear()
//        categoryList.add(Category("1" ,"http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png" ,"T桖"))
//        categoryList.add(Category("2" ,"http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png" ,"休闲裤"))
//        categoryList.add(Category("3", "http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png" ,"外套"))
//        categoryList.add(Category("4","http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png" ,"短裤"))
//        categoryList.add(Category("5","http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png" ,"牛仔裤"))
//        categoryList.add(Category("6","http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png" ,"寸衫"))
//        categoryList.add(Category("7","http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png" ,"套装"))
//        categoryList.add(Category("8","http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png" ,"牛仔衣"))
//        //categoryList.add(Category(9,"http://app.infunpw.com/commons/images/cinema/cinema_films/3823.jpg" ,"抖音款"))
//        categoryAdapter!!.setNewData(categoryList)

//        for(i in 0..20) {
//            dataList.add(i.toString())
//        }
//        dataAdapter!!.notifyDataSetChanged()

        if(category==null) return

        dataBinding!!.goodsViewModel!!.getGoodsOfCategory(category!!.categoryId!! , column_price_sort , page+1)
    }

    override fun onLoadMoreRequested() {
        if(category==null) return

        tab_refreshview.isRefreshing=false
        dataBinding!!.goodsViewModel!!.getGoodsOfCategory(category!!.categoryId!! , column_price_sort , page+1)

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var bean = dataAdapter!!.getItem(position)

        ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath).withString( "goodsId", bean!!.goodsId ).navigation()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view!!.id){
            R.id.good_item_1_favorite->{
                showToast("todo")
            }
        }
    }




    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.column_lay_price->{
                if(column_price_sort==GoodsSortEnum.PriceDes) {
                    column_price_icon.setImageResource(R.mipmap.up1)
                    column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color_selected))
                    column_price_sort=GoodsSortEnum.PriceAsc
                }else{
                    column_price_icon.setImageResource(R.mipmap.down1)
                    column_price_sort=GoodsSortEnum.PriceDes
                    column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color_selected))
                }

                column_commission.setTextColor( ContextCompat.getColor(context!! , R.color.column_text_color) )
                column_news.setTextColor(ContextCompat.getColor(context!!, R.color.column_text_color))
                column_sales.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))


                onRefresh()
            }
            R.id.column_commission->{
                column_price_sort = GoodsSortEnum.RewardDes
                column_commission.setTextColor( ContextCompat.getColor(context!! , R.color.column_text_color_selected) )

                column_news.setTextColor(ContextCompat.getColor(context!!, R.color.column_text_color))
                column_price_icon.setImageResource(R.mipmap.updown)
                column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))
                column_sales.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))

                onRefresh()
            }
            R.id.column_news->{
                column_price_sort = GoodsSortEnum.Newed
                column_commission.setTextColor( ContextCompat.getColor(context!! , R.color.column_text_color) )

                column_news.setTextColor(ContextCompat.getColor(context!!, R.color.column_text_color_selected))
                column_price_icon.setImageResource(R.mipmap.updown)
                column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))
                column_sales.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))

                onRefresh()
            }
            R.id.column_sales->{
                column_price_sort = GoodsSortEnum.SaleDes
                column_commission.setTextColor( ContextCompat.getColor(context!! , R.color.column_text_color) )

                column_news.setTextColor(ContextCompat.getColor(context!!, R.color.column_text_color))
                column_price_icon.setImageResource(R.mipmap.updown)
                column_price.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color))
                column_sales.setTextColor(ContextCompat.getColor(context!!,R.color.column_text_color_selected))

                onRefresh()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment TabFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance( category: String) =
                TabFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_CATEGORY, category)
                    }
                }
    }
}
