package com.jxd.android.gohomeapp.libcommon.util

import android.databinding.BindingAdapter
import android.widget.ImageView

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.util
 * @ClassName:      BindAdapterUtilss
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/3 14:17
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/3 14:17
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object BindAdapterUtilss {

    @BindingAdapter("android:src")
    fun loadImage(imageView: ImageView , resourceId :Int){
        if(imageView!=null){
            imageView.setImageResource(resourceId)
        }
    }
}