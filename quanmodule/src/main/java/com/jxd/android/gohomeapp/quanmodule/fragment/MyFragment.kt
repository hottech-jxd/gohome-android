package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.database.DatabaseUtils
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.barlibrary.ImmersionBar
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.AppFragmentAdapter
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.bean.MyBean
import com.jxd.android.gohomeapp.libcommon.bean.OrderStatusEnum
import com.jxd.android.gohomeapp.libcommon.bean.OrderTypeEnum
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.R.id.*
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentMeBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.layout_me_header.*
import kotlinx.android.synthetic.main.quan_fragment_me.*
import me.yokeyword.fragmentation.SupportActivity
import java.math.BigDecimal

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path=ARouterPath.QuanFragmentMyPath)
class MyFragment : BaseFragment() , View.OnClickListener
    , TabLayout.OnTabSelectedListener
    , SwipeRefreshLayout.OnRefreshListener
    , AppBarLayout.OnOffsetChangedListener{

    private var param1: String? = null
    var fragments=ArrayList<BaseFragment>()
    var titles =ArrayList<String>()
    var orderAdapter:AppFragmentAdapter?=null
    var dataBinding:QuanFragmentMeBinding?=null
    //var myBean:MyBean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_me , container, false)
        dataBinding!!.clickHandler = this
        dataBinding!!.userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        dataBinding!!.myBean = MyBean(BigDecimal.ZERO,BigDecimal.ZERO , BigDecimal.ZERO)
        return dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        quan_my_appbarLayout.addOnOffsetChangedListener (this)


        dataBinding!!.userViewModel!!.liveDataMyResult.observe(this, Observer { it->
            quan_my_refresview.isRefreshing=false
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){

                showToast(it.resultMsg)
                return@Observer
            }

            dataBinding!!.myBean = it.data
            //this.myBean = it.data
            //my_header_pre_week_momey.text = "￥${it.data!!.lastWeek.setScale(2,BigDecimal.ROUND_HALF_UP)}"
            //my_header_this_week_momey.text = "￥${it.data!!.thisWeek.setScale(2, BigDecimal.ROUND_HALF_UP)}"
            //my_header_all_momey.text = "￥${it.data!!.total.setScale(2,BigDecimal.ROUND_HALF_UP)}"
        })

        dataBinding!!.userViewModel!!.hasError.observe(this, Observer {
            if(it==false) return@Observer
            quan_my_refresview.isRefreshing=false
            showToast( dataBinding!!.userViewModel!!.error.value!! )
        } )


        UserViewModel.liveDataUserInfo.observe(this, Observer {it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            my_header_logo.setImageURI(it.data!!.head)
            my_header_username.text = it.data!!.userId
            my_header_balance.text = it.data!!.money.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()

        })

    }

    override fun onOffsetChanged(p0: AppBarLayout?, verticalOffset: Int) {
        if(verticalOffset>=0){
            quan_my_refresview.isEnabled=true
        }else{
            quan_my_refresview.isEnabled = false
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) {
            ImmersionBar.with(this).statusBarColor(R.color.my_status_color).init()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initView()
        fetchData()
    }

    override fun initView() {
//        my_setting.setOnClickListener(this)
//        my_lay_order.setOnClickListener(this)
//        my_lay_favorite.setOnClickListener(this)
//        my_lay_message.setOnClickListener(this)
//        my_lay_invite.setOnClickListener(this)
//        my_lay_zhuan.setOnClickListener(this)

        //my_header_cash.setOnClickListener(this)

        quan_my_refresview.setOnRefreshListener(this)

        fragments.clear()
        titles.clear()
        titles.add(OrderStatusEnum.ALL.desc)
        fragments.add(OrderFragment.newInstance(OrderTypeEnum.PINDUODUO.id,OrderStatusEnum.ALL.id))
        titles.add(OrderStatusEnum.PREPARE.desc)
        fragments.add(OrderFragment.newInstance(OrderTypeEnum.PINDUODUO.id,OrderStatusEnum.PREPARE.id))
        titles.add(OrderStatusEnum.RECEIVED.desc)
        fragments.add(OrderFragment.newInstance(OrderTypeEnum.PINDUODUO.id,OrderStatusEnum.RECEIVED.id))
        titles.add(OrderStatusEnum.INVAIDAD.desc)
        fragments.add(OrderFragment.newInstance(OrderTypeEnum.PINDUODUO.id,OrderStatusEnum.INVAIDAD.id))
        titles.add(OrderStatusEnum.ARRIVED.desc)
        fragments.add(OrderFragment.newInstance(OrderTypeEnum.PINDUODUO.id, OrderStatusEnum.ARRIVED.id))
        orderAdapter = AppFragmentAdapter(childFragmentManager ,fragments,titles)

        quan_my_viewpager.adapter=orderAdapter

        quan_my_tab.setupWithViewPager(quan_my_viewpager,true)

        quan_my_tab.addOnTabSelectedListener(this)

    }

    fun fetchData() {
        dataBinding!!.userViewModel!!.getMyIndex()
        dataBinding!!.userViewModel!!.getUserInfo(false)
    }

    override fun onRefresh() {
        fetchData()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.my_header_cash->{

                (this.parentFragment as MainFragment).start(CashFragment.newInstance("",""))

            }
            R.id.my_header_lay_more->{
                (this.parentFragment as MainFragment).start(IncomeFragment.newInstance("",""))
            }
//            R.id.my_lay_order->{
//                newIntent<OrderActivity>()
//            }
//            R.id.my_lay_favorite->{
//                newIntent<FavoriteActivity>()
//            }
//            R.id.my_lay_message->{
//                newIntent<MessageActivity>()
//            }
//            R.id.my_lay_invite->{
//                newIntent<InviteActivity>()
//            }
//            R.id.my_lay_zhuan->{
//                newIntent<MyMoneyActivity>()
//            }
        }
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabSelected(p0: TabLayout.Tab?) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                MyFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
