package com.jxd.android.gohomapp.quanmodule.util

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * Created by Administrator on 2017/11/1.
 */

object MobileUtils {
    /***
     * 检索手机号码是否合法
     * @param phone
     * @return
     */
    fun isPhone(phone: String): Boolean {
        val p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(16[0-9])|(18[0-9])|(17[0-9])|(19[0-9]))\\d{8}$")
        val m = p.matcher(phone)
        //logger.info(m.matches()+"---");
        return m.matches()
    }

    /***
     *
     * @param text
     * @return
     */
     fun dealPhone(text:String):String{
        if(TextUtils.isEmpty( text)) return  text

        var isPh = isPhone(text)
        if(isPh){
            var prefx = text.substring(0,3)
            var len = text.length
            var endfx = text.substring(len-4,len)
            return prefx + "****" + endfx;
        }
        return text
    }


    //判断email格式是否正确
    fun isEmail(email: String): Boolean {
        val str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        val p = Pattern.compile(str)
        val m = p.matcher(email)
        return m.matches()
    }


}
