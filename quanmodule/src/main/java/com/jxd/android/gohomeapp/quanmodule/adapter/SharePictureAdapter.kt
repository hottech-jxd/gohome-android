package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.libcommon.bean.CouponBean
import com.jxd.android.gohomeapp.libcommon.bean.ShareBean
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
class SharePictureAdapter(data:ArrayList<ShareBean>?) : BaseQuickAdapter<ShareBean, BaseViewHolder>(R.layout.layout_share_item , data ) {

    override fun convert(helper: BaseViewHolder?, item: ShareBean?) {

        helper!!.getView<SimpleDraweeView>(R.id.share_item_image).setImageURI( item!!.url )

        helper.setImageResource( R.id.share_item_check , if( item.check ) R.mipmap.g else R.mipmap.off)


     }
}