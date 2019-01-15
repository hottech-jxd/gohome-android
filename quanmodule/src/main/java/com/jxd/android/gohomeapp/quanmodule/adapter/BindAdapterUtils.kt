package com.jxd.android.gohomeapp.quanmodule.adapter

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.NonNull
import android.text.SpannableString
import android.text.style.ImageSpan
import android.widget.ImageView
import android.widget.TextView
import com.jxd.android.gohomeapp.libcommon.bean.GoodsSourceEnum
import com.jxd.android.gohomeapp.quanmodule.R

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
object BindAdapterUtils {



    @JvmStatic @BindingAdapter("app:content", "app:source" , requireAll = false)
    fun setGoodsName(textView: TextView, content: String?, source: String?) {

        var spannableString = SpannableString("^&&^$content")

        var logo = R.mipmap.pinduoduo
        if( source== GoodsSourceEnum.PingDuoDuo.code.toString() ){
            logo = R.mipmap.pinduoduo
        }else if(source== GoodsSourceEnum.TaoBao.code.toString() ){
            logo = R.mipmap.taobao
        }

        var imageSpan = CenteredImageSpan( textView.context , logo)
        spannableString.setSpan(imageSpan, 0, 4, ImageSpan.ALIGN_BASELINE)

        textView.text = spannableString
    }
}


    class CenteredImageSpan(context: Context, drawableRes: Int) : ImageSpan(context, drawableRes) {

        override
        fun draw(@NonNull canvas: Canvas, text: CharSequence,
                 start: Int, end: Int, x: Float,
                 top: Int, y: Int, bottom: Int, @NonNull paint: Paint
        ) {
            // image to draw
            val b = drawable
            // font metrics of text to be replaced
            val fm = paint.fontMetrics
            val transY = (y + fm.descent + y + fm.ascent) / 2 - b.bounds.bottom / 2

            canvas.save()
            canvas.translate(x, transY)
            b.draw(canvas)
            canvas.restore()
        }
    }