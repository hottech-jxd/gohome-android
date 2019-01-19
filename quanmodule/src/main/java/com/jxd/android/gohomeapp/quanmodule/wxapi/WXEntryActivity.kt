package com.jxd.android.gohomeapp.quanmodule.wxapi


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler


/*
 *
 * 微信客户端回调activity示例
 */
class WXEntryActivity : Activity(), IWXAPIEventHandler {
    private val TAG = this@WXEntryActivity.javaClass.name
    private var toast: Toast?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(View(this))


        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，
        // 如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，
        // 避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            val isDeal = QuanModule.WechatApi!!.handleIntent(intent, this)
            if (!isDeal) {
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        QuanModule.WechatApi!!.handleIntent(intent, this)
    }

    override fun onDestroy() {
        super.onDestroy()

        if(toast!=null) {
            toast!!.cancel()
        }
    }

    private fun showToast(msg:String):Toast{
        if(toast==null){
            toast=Toast.makeText(this,msg ,Toast.LENGTH_LONG)
        }else{
            toast!!.setText(msg)
        }
        toast!!.show()
        return toast!!
    }

    override fun onReq(baseReq: BaseReq) {

    }

    override fun onResp(baseResp: BaseResp) {
        Log.d(TAG, "baseResp.type =" + baseResp.type)

        when(baseResp.type){
            ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM->{//启动小程序，回调
                var launchMinProResp =   baseResp as WXLaunchMiniProgram.Resp
                //var extraData = launchMinProResp.extMsg
            }
            ConstantsAPI.COMMAND_SENDAUTH->{//微信授权登录,回调
                authLogin(baseResp)
            }
            ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX->{
                share(baseResp)
            }
        }


        finish()
    }


    private fun share(baseResp : BaseResp ){
        when (baseResp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                showToast("分享成功")
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> showToast( "操作失败" + baseResp.errStr)
            BaseResp.ErrCode.ERR_UNSUPPORT -> showToast( "操作不支持" + baseResp.errStr)
            BaseResp.ErrCode.ERR_USER_CANCEL -> showToast( "用户取消操作")
            else -> showToast( "操作异常=" + baseResp.errCode + baseResp.errStr)
        }
    }

    private fun authLogin( baseResp : BaseResp){

        when(baseResp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                //ToastUtils.single.showLongToast(this, "授权成功" )
                showToast("授权成功")
                //var intent = Intent()
                //intent.action =Constants.ACTION_WECHAT_LOGIN
                //intent.putExtra("code", (baseResp as SendAuth.Resp).code)
                //sendBroadcast(intent)
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                //ToastUtils.single.showLongToast(this, "授权失败" + baseResp.errStr)
                //Toast.makeText(this, "授权失败" ,Toast.LENGTH_LONG).show()
                showToast("授权失败"+ baseResp.errStr)
            }
            BaseResp.ErrCode.ERR_UNSUPPORT ->{
                //ToastUtils.single.showLongToast(this, "操作不支持" + baseResp.errStr)
                showToast("操作不支持"+ baseResp.errStr)
            }
            BaseResp.ErrCode.ERR_USER_CANCEL ->{
                //ToastUtils.single.showLongToast(this, "用户取消操作" + baseResp.errStr)
                showToast("用户取消操作"+baseResp.errStr)
            }
            else ->{
                //ToastUtils.single.showLongToast(this, "操作异常=" + baseResp.errCode + baseResp.errStr)
                showToast("操作异常=" + baseResp.errCode + baseResp.errStr)
            }
        }
    }

    private fun getAccessToken( code :String  ){

    }


}
