package com.jxd.android.gohomeapp.quanmodule.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.OrderBean
import com.jxd.android.gohomeapp.libcommon.bean.OrderStatusEnum
import com.jxd.android.gohomeapp.libcommon.bean.OrderTypeEnum
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.OrderAdapter
import kotlinx.android.synthetic.main.quan_fragment_order.*

private const val ARG_ORDERTYPE = "ordertype"
private const val ARG_ORDERSTATUS= "orderstatus"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OrderFragment : BaseFragment() {

    private var orderType: Int = OrderTypeEnum.PINDUODUO.id
    private var orderStatus: Int = OrderStatusEnum.ALL.id
    private var orderAdapter : OrderAdapter?=null
    private var data=ArrayList<OrderBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderType = it.getInt(ARG_ORDERTYPE , OrderTypeEnum.PINDUODUO.id)
            orderStatus = it.getInt(ARG_ORDERSTATUS , OrderStatusEnum.ALL.id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.quan_fragment_order , container ,false)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initView()
    }



    override fun initView() {
        for(i in 0..10){
            data.add(OrderBean(i,i.toString(),"sadfa","","asdf","23","3"))
        }

        orderAdapter = OrderAdapter(data)

        order_recyclerview.layoutManager=LinearLayoutManager(context)
        order_recyclerview.adapter = orderAdapter
        //order_recyclerview.addItemDecoration(RecycleItemDivider(context!!,LinearLayoutManager.VERTICAL,1))
    }

    fun fetchData() {
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
