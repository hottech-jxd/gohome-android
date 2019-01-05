package com.jxd.android.gohomeapp.quanmodule.fragment


import android.database.DatabaseUtils
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.AppFragmentAdapter
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.OrderStatusEnum
import com.jxd.android.gohomeapp.libcommon.bean.OrderTypeEnum
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentMeBinding
import kotlinx.android.synthetic.main.layout_me_header.*
import kotlinx.android.synthetic.main.quan_fragment_me.*
import me.yokeyword.fragmentation.SupportActivity

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
class MyFragment : BaseFragment() , View.OnClickListener , TabLayout.OnTabSelectedListener {

    private var param1: String? = null
    var fragments=ArrayList<BaseFragment>()
    var titles =ArrayList<String>()
    var orderAdapter:AppFragmentAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.quan_fragment_me , container , false)
        var dataBinding:QuanFragmentMeBinding = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_me , container, false)
        dataBinding.clickHandler = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun initView() {
//        my_setting.setOnClickListener(this)
//        my_lay_order.setOnClickListener(this)
//        my_lay_favorite.setOnClickListener(this)
//        my_lay_message.setOnClickListener(this)
//        my_lay_invite.setOnClickListener(this)
//        my_lay_zhuan.setOnClickListener(this)

        //my_header_cash.setOnClickListener(this)

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

    }

    override fun getLayoutResourceId(): Int {
        return 0
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
