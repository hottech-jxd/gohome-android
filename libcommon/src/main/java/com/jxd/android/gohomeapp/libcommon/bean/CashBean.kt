package com.jxd.android.gohomeapp.libcommon.bean

import java.math.BigDecimal

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.bean
 * @ClassName:      CashBean
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/5 14:46
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/5 14:46
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class CashBean (var id:Int , var name :String , var status:Int , var amount:BigDecimal)


data class ApplyConfigBean(/** 手续费率(百分比)*/ val applyFeeRate: BigDecimal? = null,
   /**
    * 最低起提金额
    */
    val baseAmount: BigDecimal? = null ,
   /**
    * 最高提现金额
    */
   val highestAmount: BigDecimal? = null)

data class ApplyConfigModel(var data:ApplyConfigBean?)