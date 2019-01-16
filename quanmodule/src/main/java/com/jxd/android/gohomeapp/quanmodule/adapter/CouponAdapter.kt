package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.libcommon.bean.CouponBean
import com.jxd.android.gohomeapp.quanmodule.R

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.adapter
 * @ClassName:      CouponAdapter
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/7 10:25
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/7 10:25
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class CouponAdapter(data:ArrayList<CouponBean>?) : BaseQuickAdapter<CouponBean, BaseViewHolder>(R.layout.layout_coupon_item , data ) {

    override fun convert(helper: BaseViewHolder?, item: CouponBean?) {

        helper!!.getView<SimpleDraweeView>(R.id.coupon_item_image).setImageURI( item!!.pictureUrl )
        helper.setText(R.id.coupon_item_count , "还剩${item.remain}")
        helper.setText(R.id.coupon_item_coupon , "劵￥"+item.couponPrice)
        helper.setText(R.id.coupon_item_final_price , item.finalPrice )
        helper.setText(R.id.coupon_item_title , item.name)

        helper.addOnClickListener(R.id.coupon_item_buy)

     }
}