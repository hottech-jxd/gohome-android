package com.jxd.android.gohomeapp.quanmodule.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.quanmodule.FrescoImageLoader
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.bean.Constants
import com.jxd.android.gohomeapp.quanmodule.bean.GoodsDetailBean
import com.jxd.android.gohomeapp.quanmodule.util.AppUtil
import com.jxd.android.gohomeapp.quanmodule.util.DensityUtils
import com.jxd.android.gohomeapp.quanmodule.util.FrescoDraweeListener
import java.io.File

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.fragment
 * @ClassName:      CreatePictureByLayout
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/22 10:47
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/22 10:47
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class CreatePictureByLayout : FrescoDraweeListener.ImageCallback {
    var rootView:View?=null

    fun initShareGoodsTemplete(bean: GoodsDetailBean, context: Context){
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_share_goods_templete , null ,false)
        var screenWidth = DensityUtils.getScreenWidth(context)
        var screenHeight = DensityUtils.getScreenHeight(context)
        rootView!!.layout(0,0,screenWidth , screenHeight)
        var measureWidth = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY)
        var measureHeight = View.MeasureSpec.makeMeasureSpec(screenHeight , View.MeasureSpec.AT_MOST)
        rootView!!.measure(measureWidth ,measureHeight)
        rootView!!.layout(0,0, rootView!!.measuredWidth , rootView!!.measuredHeight)

        var tvTitle=rootView!!.findViewById<TextView>(R.id.share_goods_templete_title)
        var tvPrice = rootView!!.findViewById<TextView>(R.id.share_goods_templete_price)
        var tvFinalPrice= rootView!!.findViewById<TextView>(R.id.share_goods_templete_finalprice)
        var tvFinalPrice2= rootView!!.findViewById<TextView>(R.id.share_goods_templete_finalprice2)
        var tvCouponPrice = rootView!!.findViewById<TextView>(R.id.share_goods_templete_couponPrice)
            //var ivPic= rootView!!.findViewById<SimpleDraweeView>(R.id.share_goods_templete_pic)
        var ivQrcode= rootView!!.findViewById<SimpleDraweeView>(R.id.share_goods_templete_qrcode)

        tvTitle.text = bean.name
        tvPrice.text = bean.price
        tvFinalPrice.text = bean.finalPrice
        tvFinalPrice2.text = bean.finalPrice
        tvCouponPrice.text = bean.couponPrice

        var picWidth = screenWidth- rootView!!.paddingLeft - rootView!!.paddingRight
        var picUrl = bean.pictureUrls!![0]
        //FrescoDraweeController.loadImage( ivPic , picWidth , picWidth , picUrl , this )
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        simpleDraweeView!!.layoutParams = RelativeLayout.LayoutParams(width,height)
        var bitmap = createBitmapOfView()
        saveBitmap(bitmap)
    }

    private fun createBitmapOfView():Bitmap{
        var bitmap = Bitmap.createBitmap( rootView!!.width , rootView!!.height , Bitmap.Config.ARGB_8888 )
        var canvas = Canvas(bitmap)
        rootView!!.draw(canvas)
        return  bitmap
    }

    private fun saveBitmap(bitmap:Bitmap):String{
        var filePath = Constants.ImageDirPath +"/share/"+ System.currentTimeMillis().toShort()+".jpg"
        var isSucces = AppUtil.save( bitmap , File(filePath) , Bitmap.CompressFormat.JPEG ,true)
        return filePath
    }
}