package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.bean.OrderBean
import com.jxd.android.gohomeapp.libcommon.bean.OrderStatusEnum
import com.jxd.android.gohomeapp.libcommon.bean.OrderTypeEnum
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.OrderAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentOrderBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.quan_fragment_order.*

private const val ARG_ORDERTYPE = "ordertype"
private const val ARG_ORDERSTATUS= "orderstatus"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OrderFragment : BaseFragment() , SwipeRefreshLayout.OnRefreshListener , BaseQuickAdapter.RequestLoadMoreListener {

    private var orderType: Int = OrderTypeEnum.PINDUODUO.id
    private var orderStatus: Int = OrderStatusEnum.ALL.id
    private var orderAdapter : OrderAdapter?=null
    private var data=ArrayList<OrderBean>()
    var dataBinding:QuanFragmentOrderBinding?=null
    private var pageIndex:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderType = it.getInt(ARG_ORDERTYPE , OrderTypeEnum.PINDUODUO.id)
            orderStatus = it.getInt(ARG_ORDERSTATUS , OrderStatusEnum.ALL.id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dataBinding = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_order , container , false)
        dataBinding!!.userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        return  dataBinding!!.root
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        //initView()

        fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        order_recyclerview.layoutManager=LinearLayoutManager(context)
        orderAdapter = OrderAdapter(data)
        orderAdapter!!.setOnLoadMoreListener(this , order_recyclerview)
        order_recyclerview.adapter = orderAdapter
        order_refreshview.setOnRefreshListener(this)


        dataBinding!!.userViewModel!!.liveDataOrderList.observe(this , Observer { it->

            order_refreshview.isRefreshing=false

            if(it!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }


            var datas: ArrayList<OrderBean>? = null
            if (it.list == null) {
                orderAdapter!!.loadMoreEnd(false)
            } else {
                datas = it.list!!
                if (  datas.size < 1) {
                    orderAdapter!!.loadMoreEnd(false)
                } else {
                    orderAdapter!!.loadMoreComplete()
                    pageIndex++
                }
                orderAdapter!!.addData(datas)
            }

         })

        dataBinding!!.userViewModel!!.hasError.observe(this, Observer { it->
            if(it==false) return@Observer

            order_refreshview.isRefreshing=false
            showToast(dataBinding!!.userViewModel!!.error.value!!)

        })
    }

    override fun initView() {
//        for(i in 0..10){
//            data.add(OrderBean(i,i.toString(),"sadfa","","asdf","23","3"))
//        }





        //order_recyclerview.addItemDecoration(RecycleItemDivider(context!!,LinearLayoutManager.VERTICAL,1))
    }

    fun fetchData() {

        dataBinding!!.userViewModel!!.getOrderList(QuanModule.userBean!!.UserId , orderStatus , pageIndex+1)

    }

    override fun onRefresh() {
        pageIndex=0
        fetchData()
    }

    override fun onLoadMoreRequested() {

        order_refreshview.isRefreshing=false
        dataBinding!!.userViewModel!!.getOrderList(QuanModule.userBean!!.UserId , orderStatus , pageIndex+1)
    }

    //    override fun getLayoutResourceId(): Int {
//        return R.layout.fragment_order
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        @JvmStatic
        fun newInstance( orderType: Int, orderStatus: Int) =
                OrderFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_ORDERTYPE, orderType)
                        putInt(ARG_ORDERSTATUS, orderStatus)
                    }
                }
    }
}
