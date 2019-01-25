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
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.barlibrary.ImmersionBar
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.R.id.*
import com.jxd.android.gohomeapp.quanmodule.base.ARouterPath
import com.jxd.android.gohomeapp.quanmodule.base.AppFragmentAdapter
import com.jxd.android.gohomeapp.quanmodule.base.BaseFragment
import com.jxd.android.gohomeapp.quanmodule.bean.*
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentMeBinding
import com.jxd.android.gohomeapp.quanmodule.util.showToast
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.layout_me_header.*
import kotlinx.android.synthetic.main.quan_fragment_me.*
import kotlinx.android.synthetic.main.quan_fragment_share.*
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
@Route(path= ARouterPath.QuanFragmentMyPath)
class MyFragment : BaseFragment() , View.OnClickListener
    , TabLayout.OnTabSelectedListener
    , SwipeRefreshLayout.OnRefreshListener
    , AppBarLayout.OnOffsetChangedListener{

    var fragments=ArrayList<BaseFragment>()
    var titles =ArrayList<String?>()
    var orderAdapter: AppFragmentAdapter?=null
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
        dataBinding!!.myBean = MyBean(BigDecimal.ZERO,BigDecimal.ZERO , BigDecimal.ZERO, BigDecimal.ZERO )
        return dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        my_header_username.text = QuanModule.userId
        quan_my_appbarLayout.addOnOffsetChangedListener (this)


        UserViewModel.liveDataMyResult.observe(this, Observer { it->
            quan_my_refresview.isRefreshing=false
            if(it!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){

                showToast(it.resultMsg)
                return@Observer
            }
            if(it.resultData ==null || it.resultData!!.data==null) return@Observer

            dataBinding!!.myBean = it.resultData!!.data

        })

        dataBinding!!.userViewModel!!.error.observe(this, Observer {
            if( TextUtils.isEmpty(it) ) return@Observer
            quan_my_refresview.isRefreshing=false
            showToast( it )
        } )

        dataBinding!!.userViewModel!!.liveDataRollDescResult.observe(this, Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                return@Observer
            }

            getMessages(it.resultData)
        })

//        UserViewModel.liveDataUserInfo.observe(this, Observer {it->
//            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
//                showToast(it.resultMsg)
//                return@Observer
//            }
//            if(it.resultData==null)return@Observer
//
//            my_header_logo.setImageURI(it.resultData!!.head)
//            my_header_username.text = it.resultData!!.userId
//            my_header_balance.text = it.resultData!!.money.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()
//
//        })

    }

    override fun onOffsetChanged(p0: AppBarLayout?, verticalOffset: Int) {
            quan_my_refresview.isEnabled= verticalOffset>=0
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        ImmersionBar.with(this).statusBarColor(R.color.my_status_color).init()
    }



    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) {
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
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

        quan_my_refresview.setProgressViewOffset(true , -20 , 100)

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

    private fun fetchData() {
        dataBinding!!.userViewModel!!.getMyIndex()
        //dataBinding!!.userViewModel!!.getUserInfo(false)
        dataBinding!!.userViewModel!!.getRollDesc()

    }

    private fun getMessages(messageList : MessageModel?){
        if(messageList==null || messageList.list==null|| messageList.list!!.size<1){
            my_header_lay_message.visibility=View.GONE
            return
        }

        my_header_lay_message.visibility=View.VISIBLE
//        var messages = ArrayList<String>()
//        messages.add("asdfsafassfaeeeeeeeeeeeeeeeeeeeeeeeeeeee")
//        messages.add("23232333333333333333333333333333333333333333333")
//        messages.add("打发斯蒂芬爱的发声发顺丰阿法士大夫撒飞洒发啊所发生的")
//        messages.add("sdfs3w423sfs23eeeeeeeeee42342342342423srsfsf")

        my_header_scrollTextInfo.setResource( messageList.list)

        //提供四个方向动画；默认从下往上
        my_header_scrollTextInfo.setAnimationTop2Bottom()
        //share_scrollTextInfo.setAnimationBottom2Top()
        //share_scrollTextInfo.setAnimationLeft2Right()
        //share_scrollTextInfo.setAnimationRight2Left()

        my_header_scrollTextInfo.startRolling()
    }

    override fun onRefresh() {
        fetchData()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.my_header_cash->{
                (this.parentFragment as MainFragment).start(CashFragment.newInstance("",""))
            }
            R.id.my_header_lay_all,
            R.id.my_header_lay_preweek,
            R.id.my_header_lay_thisweek,
            R.id.my_header_lay_more->{
                (this.parentFragment as MainFragment).start(IncomeFragment.newInstance("",""))
            }
            R.id.my_header_detail->{
                (this.parentFragment as MainFragment).start(ARouter.getInstance().build(ARouterPath.QuanFragmentBalancePath).navigation() as BalanceFragment)
            }
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
         * @return A new instance of fragment MyFragment.
         */
        @JvmStatic
        fun newInstance() =
                MyFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
