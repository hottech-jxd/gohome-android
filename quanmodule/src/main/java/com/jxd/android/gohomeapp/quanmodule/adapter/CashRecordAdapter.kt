package com.jxd.android.gohomeapp.quanmodule.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jxd.android.gohomeapp.libcommon.bean.ApplyRecord
import com.jxd.android.gohomeapp.libcommon.bean.BalanceLog
import com.jxd.android.gohomeapp.libcommon.bean.CashBean
import com.jxd.android.gohomeapp.libcommon.util.DateUtils
import com.jxd.android.gohomeapp.quanmodule.R
import java.math.BigDecimal

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
class CashRecordAdapter(data:ArrayList<ApplyRecord>): BaseQuickAdapter<ApplyRecord, BaseViewHolder>( R.layout.layout_cash_item , data) {

    override fun convert(helper: BaseViewHolder?, item: ApplyRecord?) {
        helper!!.setText(R.id.cash_item_title , item!!.applyTypeStr)
        helper.setText(R.id.cash_item_time , DateUtils.formatDate(item.applyTime) )
        helper.setText(R.id.cash_item_amount , item.applyMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString())
        helper.setText(R.id.cash_item_status , item.applyStatusStr)
    }
}


class BalanceAdapter(data:ArrayList<BalanceLog>): BaseQuickAdapter<BalanceLog, BaseViewHolder>( R.layout.layout_balance_item , data) {

    override fun convert(helper: BaseViewHolder?, item: BalanceLog?) {
        helper!!.setText(R.id.balance_item_title , item!!.logRemark)
        helper.setText(R.id.balance_item_time , DateUtils.formatDate(item.changeTime) )
        helper.setText(R.id.balance_item_amount , item.changeMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString())

    }
}