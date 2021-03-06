package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.iwgang.countdownview.CountdownView
import com.jxd.android.gohomapp.quanmodule.util.MobileUtils
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.base.BaseBackFragment
import com.jxd.android.gohomeapp.quanmodule.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.quanmodule.bean.UserApplyAccount
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentCashBankBinding
import com.jxd.android.gohomeapp.quanmodule.repository.UserRepository.sendCode
import com.jxd.android.gohomeapp.quanmodule.util.KeybordUtils
import com.jxd.android.gohomeapp.quanmodule.util.showToast
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.quan_layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_cash_bank.*
import kotlinx.android.synthetic.main.quan_fragment_cash_bank.view.*
import kotlinx.android.synthetic.main.quan_fragment_income.*
import java.lang.Exception
import java.math.BigDecimal
import java.sql.DatabaseMetaData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CashBankFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CashBankFragment : BaseBackFragment()
    , TextWatcher
    , View.OnClickListener
    , CountdownView.OnCountdownEndListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var showCountdown:Boolean=false
    var userViewModel:UserViewModel?=null
    var userAccount: UserApplyAccount?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(   inflater: LayoutInflater, container: ViewGroup?,   savedInstanceState: Bundle?    ): View? {

        var dataBinding : QuanFragmentCashBankBinding = DataBindingUtil.inflate( inflater , R.layout.quan_fragment_cash_bank , container , false)
        dataBinding.clickHandler = this


        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        dataBinding.userViewModel = userViewModel
        dataBinding.accountBean= userAccount
        userViewModel!!.liveDataCashApplyResult.observe(this , Observer { it->
            if(it!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            showToast(it.resultMsg)
            _mActivity.onBackPressed()

        })

        userViewModel!!.liveDataSendCodeResult.observe(this , Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                cashbank_getcode.visibility = View.VISIBLE
                cashbank_countdown.stop()
                cashbank_countdown.visibility = View.GONE
                return@Observer
            }

            cashbank_getcode.visibility = View.GONE
            cashbank_countdown.visibility=View.VISIBLE
            cashbank_countdown.start(60000)
        })


        UserViewModel.liveDataMyResult.observe(this, Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            if(it!!.resultData==null || it.resultData!!.data==null ) return@Observer
            cash_balance.text = it.resultData!!.data!!.userBalance.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()
        })


        userViewModel!!.liveDataApplyConfigResult.observe(this , Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            var config = it.resultData!!.data
            cashbank_desc1.text = "起提金额￥${config!!.baseAmount},提现上限￥${config.highestAmount},手续费率${config.applyFeeRate}%"

        })

        userViewModel!!.liveDataApplyAccountResult.observe(this, Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            if(it.resultData==null) return@Observer
            userAccount = it.resultData!!.data
        })

        return dataBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        header_title.text="提现到银行"
        cashbank_countdown.setOnCountdownEndListener(this)

        cashbank_amount.addTextChangedListener(this)


        userViewModel!!.getApplyAccount()
        userViewModel!!.getApplyConfig()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        if (cashbank_countdown != null) {
            cashbank_countdown.setOnCountdownEndListener(null)
            cashbank_countdown.stop()
        }
    }

    override fun onClick(v: View?) {
        if(v!!.id==R.id.header_left_image){
            _mActivity.onBackPressed()
        }else if(v.id==R.id.cashbank_getcode){
            sendCode()
        }else if(v.id == R.id.cashbank_countdown){

        }
        else if(v.id==R.id.cashbank_confirm){
            apply()
        }
    }

    fun sendCode(){
       var mobile = cashbank_phone.text.trim().toString()
        if(!MobileUtils.isPhone(mobile)){
            showToast("请输入正确的手机号码")
            cashbank_phone.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_phone)
            return
        }

        cashbank_getcode.visibility = View.GONE

        userViewModel!!.sendCode(mobile)
    }

    override fun onEnd(cv: CountdownView?) {
        cashbank_countdown.stop()
        cashbank_countdown.visibility =View.GONE
        cashbank_getcode.visibility = View.VISIBLE
    }


    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        try{
            if( userViewModel==null || userViewModel!!.liveDataApplyConfigResult==null|| userViewModel!!.liveDataApplyConfigResult.value==null)
                return
            if(userViewModel!!.liveDataApplyConfigResult.value!!.resultCode != ApiResultCodeEnum.SUCCESS.code) return
            if(userViewModel!!.liveDataApplyConfigResult.value!!.resultData==null) return

            var m = cashbank_amount.text.toString().toInt()

            var rate = userViewModel!!.liveDataApplyConfigResult.value!!.resultData!!.data!!.applyFeeRate!!

            var rate2 =   rate.setScale(2,BigDecimal.ROUND_HALF_UP).divide(BigDecimal(100) , 2 , BigDecimal.ROUND_HALF_UP).multiply(BigDecimal(m)).setScale(2,BigDecimal.ROUND_HALF_UP)

            cashbank_rate.text ="本次交易将收取￥${rate2}手续费."

        }catch (ex:Exception){

        }
    }

    fun apply(){

        var bank = cashbank_bankname.text.trim().toString()
        var branch = cashbank_subbankname.text.trim().toString()
        var card = cashbank_card.text.trim().toString()
        var name = cashbank_accountname.text.trim().toString()
        var money = cashbank_amount.text.trim().toString()
        var mobile = cashbank_phone.text.trim().toString()
        var code = cashbank_veritifycode.text.trim().toString()

        if(TextUtils.isEmpty(bank)){
            showToast("请输入开户银行")
            cashbank_bankname.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_bankname)
            return
        }
        if(TextUtils.isEmpty(branch)){
            showToast("请输入支行")
            cashbank_subbankname.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_subbankname)
            return
        }
        if(TextUtils.isEmpty(card)){
            showToast("请输入银行卡号")
            cashbank_card.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_card)
            return
        }
        if(TextUtils.isEmpty(name)){
            showToast("请输入账户名称")
            cashbank_accountname.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_accountname)
            return
        }
        if(TextUtils.isEmpty(mobile)){
            showToast("请输入正确的手机号码")
            cashbank_phone.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_phone)
            return
        }
        if(TextUtils.isEmpty(code)){
            showToast("请输入验证码")
            cashbank_veritifycode.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_veritifycode)
            return
        }
        if(TextUtils.isEmpty(money)){
            showToast("请输入100的倍数的提现金额")
            cashbank_amount.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_amount)
            return
        }

        try{
            var m  = money.toInt()

            if( m == 0 || m % 100 != 0){
                showToast("请输入正确的100的倍数的提现金额")
                cashbank_amount.requestFocus()
                KeybordUtils.openKeybord(context!!,cashbank_amount)
                return
            }

        }catch (ex:Exception){
            showToast("请输入正确的100的倍数的提现金额")
            cashbank_amount.requestFocus()
            KeybordUtils.openKeybord(context!!,cashbank_amount)
            return
        }

        userViewModel!!.cashApply(bank , branch , card,name, money.toInt() , mobile ,code)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CashBankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CashBankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
