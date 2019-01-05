package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jxd.android.gohomeapp.libcommon.bean.CashBean
import com.jxd.android.gohomeapp.quanmodule.R

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.adapter
 * @ClassName:      CashRecordAdapter
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/5 14:40
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/5 14:40
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class CashRecordAdapter(data:ArrayList<CashBean>): BaseQuickAdapter<CashBean, BaseViewHolder>( R.layout.layout_cash_item , data) {

    override fun convert(helper: BaseViewHolder?, item: CashBean?) {

    }
}