package com.jxd.android.gohomeapp.quanmodule.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.CashBean
import com.jxd.android.gohomeapp.libcommon.util.showToast

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.R.id.*
import com.jxd.android.gohomeapp.quanmodule.adapter.CashRecordAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentCashBinding
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_cash.*
import java.math.BigDecimal

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CashFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CashFragment : BaseBackFragment() , View.OnClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private var cashRecordAdapter:CashRecordAdapter?=null
    private var title = "提现/充值"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {

        //return inflater.inflate(R.layout.quan_fragment_cash, container, false)

        var dataBinding = DataBindingUtil.inflate<QuanFragmentCashBinding>(inflater , R.layout.quan_fragment_cash , container , false)
        dataBinding.clickHandler = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        header_title.text = title
        //header_left_image.setOnClickListener(this)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        fetchData()
    }

    fun fetchData(){
        var data = ArrayList<CashBean>()
        for(i in 0..10){
            data.add(CashBean(i ,"aaa",1 , BigDecimal(23)))
        }

        cashRecordAdapter= CashRecordAdapter(data)
        cash_recyclerview.layoutManager = LinearLayoutManager(context)
        cash_recyclerview.adapter = cashRecordAdapter
    }

    override fun getLayoutResourceId(): Int {
        return 0
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.header_left_image){
            _mActivity.onBackPressed()
        }else if(v!!.id==R.id.cash_lay_bank){
            this.start(CashBankFragment.newInstance("",""))
        }else if(v!!.id==R.id.cash_lay_cash){
            showToast("todo")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CashFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
