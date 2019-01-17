package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.bean.BalanceLog
import com.jxd.android.gohomeapp.libcommon.bean.OrderBean
import com.jxd.android.gohomeapp.libcommon.util.showToast

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.BalanceAdapter
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider3
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentBalanceBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_balance.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BalanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 * 账号明细
 */
@Route(path = ARouterPath.QuanFragmentBalancePath)
class BalanceFragment : BaseBackFragment() , SwipeRefreshLayout.OnRefreshListener , BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var dataBinding:QuanFragmentBalanceBinding?=null
    private var pageIndex = 0
    private var balanceAdapter:BalanceAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dataBinding = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_balance, container, false)
        dataBinding!!.userViewModel  =ViewModelProviders.of(this).get(UserViewModel::class.java)
        dataBinding!!.clickHandler=this
        return  dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        header_title.text="账户明细"

        balance_refreshview.setOnRefreshListener(this)
        balanceAdapter= BalanceAdapter(ArrayList())
        balanceAdapter!!.setOnLoadMoreListener(this, balance_recyclerview)
        balance_recyclerview.addItemDecoration(ItemDevider3(context!!, 1f, R.color.linecolor, 15f))
        balance_recyclerview.layoutManager=LinearLayoutManager(context)
        balance_recyclerview.adapter = balanceAdapter
        balanceAdapter!!.emptyView = View.inflate(context, R.layout.layout_empty, null)
        balanceAdapter!!.emptyView.findViewById<TextView>(R.id.empty_text).text = "暂无数据"
        balanceAdapter!!.isUseEmpty(false)

        dataBinding!!.userViewModel!!.liveDataBalanceLogResult.observe(this, Observer { it->
            balance_refreshview.isRefreshing=false
            balanceAdapter!!.isUseEmpty(true )
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            var datas: ArrayList<BalanceLog>?
            if (it.resultData == null || it.resultData!!.list ==null ) {
                balanceAdapter!!.loadMoreEnd(false)
            } else {
                datas = it.resultData!!.list!!
                if (  datas.size < 1) {
                    balanceAdapter!!.loadMoreEnd(false)
                } else {
                    balanceAdapter!!.loadMoreComplete()
                    pageIndex++
                }
                balanceAdapter!!.addData(datas)
            }

        })

        dataBinding!!.userViewModel!!.error.observe(this, Observer { it->
            balanceAdapter!!.isUseEmpty(true)
            if(TextUtils.isEmpty(it)){
                return@Observer
            }
            showToast(it!!)
        })

        dataBinding!!.userViewModel!!.loading.observe(this, Observer { it->
            balance_progress.visibility = if(it==null||!it)View.GONE else View.VISIBLE
        })
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        onRefresh()
    }

    override fun onClick(v: View?) {
        if(v!!.id==R.id.header_left_image){
            _mActivity.onBackPressed()
        }
    }

    override fun onRefresh() {
        pageIndex=0
        balanceAdapter!!.isUseEmpty(false)
        balance_refreshview.isRefreshing=false
        dataBinding!!.userViewModel!!.getBalanceLog(pageIndex+1)
    }

    override fun onLoadMoreRequested() {
        balance_refreshview.isRefreshing=false
        dataBinding!!.userViewModel!!.getBalanceLog(pageIndex+1)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BalanceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BalanceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
