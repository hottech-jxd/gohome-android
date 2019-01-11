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
class TabFragment : BaseFragment() ,View.OnClickListener , BaseQuickAdapter.OnItemClickListener{

    @Autowired(name = "category") @JvmField var category: Category? = null

    private var categoryList=ArrayList<Category>()
    private var categoryAdapter: CategoryAdapter?=null
    private var dataList =ArrayList<GoodBean>()
    private var dataAdapter: DataAdapter?=null
    private var column_price_sort :GoodsSortEnum = GoodsSortEnum.SaleDes
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
        dataBinding!!.goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)

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
        column_lay_price.setOnClickListener(this)

        categoryAdapter = CategoryAdapter(categoryList)
        tab_recyclerview_class.layoutManager = GridLayoutManager(context , 4)
        tab_recyclerview_class.adapter = categoryAdapter
        dataAdapter=DataAdapter(dataList)
        dataAdapter!!.onItemClickListener =this
        tab_recyclerview_list.layoutManager=GridLayoutManager(context,2)
        tab_recyclerview_list.adapter=dataAdapter
        tab_recyclerview_list.addItemDecoration( ItemDevider2(context!! , 15f , R.color.white ) )


        dataBinding!!.goodsViewModel!!.liveDataGoodsOfCategory.observe(this,
            Observer { it->
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

            //recommand_refreshLayout.isRefreshing=false
            showToast(it!!)

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

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var bean = dataAdapter!!.getItem(position)

        ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath).withString( "goodsId", bean!!.goodsId ).navigation()
    }

    override fun onClick(v: View?) {
//        when(v!!.id){
//            R.id.column_lay_price->{
//                if(column_price_sort==0) {
//                    column_price_icon.setImageResource(R.mipmap.up1)
//                    column_price_sort=1
//                }else{
//                    column_price_icon.setImageResource(R.mipmap.down1)
//                    column_price_sort=0
//                }
//            }
//        }
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
