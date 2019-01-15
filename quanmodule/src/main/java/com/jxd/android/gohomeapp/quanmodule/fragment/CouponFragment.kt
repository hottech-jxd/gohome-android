package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.MainActivity2

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.R.id.coupon_recyclerview
import com.jxd.android.gohomeapp.quanmodule.R.id.coupon_swipeRefreshView
import com.jxd.android.gohomeapp.quanmodule.TutorialsActivity
import com.jxd.android.gohomeapp.quanmodule.adapter.CouponAdapter
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider3
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentCouponBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.quan_fragment_coupon.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CouponFragment.newInstance] factory method to
 * create an instance of this fragment.
 * 优惠券列表
 */
class CouponFragment : BaseFragment()
    , View.OnClickListener
    , SwipeRefreshLayout.OnRefreshListener
    , BaseQuickAdapter.OnItemChildClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private var couponAdapter:CouponAdapter?=null
    var dataBinding : QuanFragmentCouponBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {

        dataBinding  = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_coupon , container , false)
        dataBinding!!.clickHandler = this
        var goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)
        dataBinding!!.goodsViewModel = goodsViewModel


        return dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(couponAdapter==null) {
            couponAdapter = CouponAdapter(ArrayList())
        }
        coupon_recyclerview.layoutManager=LinearLayoutManager(context!!)
        coupon_recyclerview.adapter = couponAdapter
        coupon_recyclerview.addItemDecoration( ItemDevider3(context!!, 1f , R.color.linecolor , 15f ) )
        couponAdapter!!.onItemChildClickListener=this

        coupon_swipeRefreshView.setOnRefreshListener(this)


        dataBinding!!.goodsViewModel!!.liveDataCouponList
            .observe(this , Observer { it->
                coupon_swipeRefreshView.isRefreshing=false
                if(it!!.resultCode != ApiResultCodeEnum.SUCCESS.code){
                    coupon_progress.visibility = View.GONE
                    showToast(it.resultMsg)
                    return@Observer
                }
                if(it.list==null) return@Observer

                couponAdapter!!.setNewData(it.list)
            })

        dataBinding!!.goodsViewModel!!.error.observe(this, Observer { it->
            if(TextUtils.isEmpty(it)){
                return@Observer
            }
            coupon_swipeRefreshView.isRefreshing=false
            showToast(it!!)
        })

        dataBinding!!.goodsViewModel!!.loading.observe(this, Observer {   it->
            coupon_progress.visibility = if(it==null || !it) View.GONE else View.VISIBLE
        })

    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            ImmersionBar.with(this).statusBarColor(R.color.coupon_status_color).init()
        }
    }

    override fun getUserVisibleHint(): Boolean {
        return super.getUserVisibleHint()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            ImmersionBar.with(this).statusBarColor(R.color.coupon_status_color).init()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)


        dataBinding!!.goodsViewModel!!.getCouponList()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if(view!!.id==R.id.coupon_item_buy){
            var bean = couponAdapter!!.getItem(position)
            ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath)
                .withString("goodsId",bean!!.goodsId)
                .navigation()
        }
    }

    override fun onRefresh() {
        dataBinding!!.goodsViewModel!!.getCouponList()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.coupon_cause->{
                this.startActivity(Intent(this.context, TutorialsActivity::class.java))

                ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath)
                    .withString("goodsId","3109518123").navigation()

            }
            R.id.coupon_go-> {
                startActivity(Intent(context, MainActivity2::class.java))
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
         * @return A new instance of fragment CouponFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CouponFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
