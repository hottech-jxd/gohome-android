package com.jxd.android.gohomeapp.quanmodule.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.R
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.constants.ConstantsAPI
import kotlinx.android.synthetic.main.activity_wx_pay.*
import kotlinx.android.synthetic.main.layout_common_header.*



/**
 * 微信支付回调类
 */
class WXPayEntryActivity : Activity()
        ,View.OnClickListener
        , IWXAPIEventHandler {

    //private val api: IWXAPI? = null

    override fun onReq(baseReq: BaseReq) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView ( R.layout.activity_wx_pay )
        wx_pay_lay.visibility=View.GONE
        wx_pay_back.setOnClickListener(this)
        header_left_image.setOnClickListener(this)
        header_title.text="微信支付"


        try {
            val isDeal = QuanModule.WechatApi!!.handleIntent(intent, this)
            if (!isDeal) {
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image,
            R.id.wx_pay_back->{
                finish()
            }
        }
    }


    private fun sendBroadCast(status:Boolean){
//        var intent = Intent()
//        intent.action = PayReceiver.ACTION_PAY
//        intent.putExtra(Constants.INTENT_STATUS , status )
//        sendBroadcast(intent)
    }

    private fun pay(resp: BaseResp) {

        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                var msg = "支付成功"
                wx_pay_lay.visibility=View.VISIBLE
                //ToastUtils.single.showToast(msg)
                wx_pay_text.text = msg
                var drawable = ContextCompat.getDrawable(this, R.mipmap.payright)
                drawable!!.setBounds(0,0,drawable!!.intrinsicWidth,drawable.intrinsicHeight)
                wx_pay_text.setCompoundDrawables(null,drawable,null,null)
                sendBroadCast(true)
                return
            }
            BaseResp.ErrCode.ERR_COMM -> {
                var msg = "支付失败"
                wx_pay_lay.visibility=View.VISIBLE
                //ToastUtils.single.showToast(msg)
                var drawable = ContextCompat.getDrawable(this, R.mipmap.warm)
                drawable!!.setBounds(0,0,drawable!!.intrinsicWidth,drawable.intrinsicHeight)
                wx_pay_text.setCompoundDrawables(null,drawable,null,null)
                wx_pay_text.text = msg
                sendBroadCast(false)
                return
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                var msg = "用户取消支付"
                wx_pay_lay.visibility=View.VISIBLE
                //ToastUtils.single.showToast(msg)
                wx_pay_text.text = msg
                var drawable = ContextCompat.getDrawable(this, R.mipmap.warm)
                drawable!!.setBounds(0,0,drawable!!.intrinsicWidth,drawable.intrinsicHeight)
                wx_pay_text.setCompoundDrawables(null,drawable,null,null)
                sendBroadCast(false)
                return
            }
            else -> {
                var msg="支付失败 错误码="+ resp.errCode +" 错误信息="+resp.errStr
                //ToastUtils.single.showToast(msg)
                wx_pay_lay.visibility=View.VISIBLE
                wx_pay_text.text = msg
                var drawable = ContextCompat.getDrawable(this, R.mipmap.warm)
                drawable!!.setBounds(0,0,drawable!!.intrinsicWidth,drawable.intrinsicHeight)
                wx_pay_text.setCompoundDrawables(null,drawable,null,null)
                sendBroadCast(false)
                return
            }
        }
    }

    override fun onResp(resp: BaseResp) {
        Log.i("info", "onPayFinish, errCode = " + resp.errCode)
        wx_pay_progress.visibility=View.GONE
        when(resp.type){
            ConstantsAPI.COMMAND_PAY_BY_WX->{
                pay(resp)
            }
        }


        //        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
        //            String msg = "";
        //            if( resp.errCode== 0)
        //            {
        //                BuyerPayUtil.paySuccessCallback( this , resp);
        //
        //                msg="支付成功";
        //                this.finish();
        //                ToastUtils.showLongToast (  msg );
        //                return;
        //            }else if( resp.errCode== -1){
        //                BuyerPayUtil.wxPayErrorCallback(resp);
        //
        //                msg="支付失败";
        //                ToastUtils.showLongToast (  msg );
        //                this.finish();
        //                return;
        //            }else if(resp.errCode ==-2){
        //                BuyerPayUtil.wxPayCancelCallback(resp);
        //
        //                msg="用户取消支付";
        //                ToastUtils.showLongToast( msg);
        //                this.finish();
        //                return;
        //            }
        //
        //            PayResp payResp = (PayResp)resp;
        //            if(null==payResp){
        //                Log.i("wxpay>>>payResp=null","");
        //                msg="支付失败";
        //                ToastUtils.showLongToast( msg);
        //                this.finish();
        //                return;
        //            }else{
        //                Log.i("wxpay>>>extData", payResp.extData==null? "": payResp.extData );
        //                //Log.i("wxpay>>>prepayid",payResp.prepayId);
        //            }
        //
        //        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        //api!!.handleIntent(intent, this)

        QuanModule.WechatApi!!.handleIntent(intent, this)
    }
}
