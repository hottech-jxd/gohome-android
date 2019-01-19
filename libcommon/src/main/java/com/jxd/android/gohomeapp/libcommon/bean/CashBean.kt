package com.jxd.android.gohomeapp.libcommon.bean

import java.math.BigDecimal
import java.time.LocalDateTime

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


data class UserAccountModel(var data:UserApplyAccount?)

class UserApplyAccount {
    /**
     * 主键Id
     */
     val accountId: Int? = null

    /**
     * 用户Id
     */
     val userId: String? = null

    /**
     * 账户类型
     */
     val accountType: Int? = null

    /**
     * 账户信息
     */
     val accountInfo: String? = null

    /**
     * 银行名称
     */
     val bankName: String? = null

    /**
     * 支行信息
     */
     val bankInfo: String? = null

    /**
     * 真实姓名
     */
     val realName: String? = null

    /**
     * 是否默认(1-是，0-否）
     */
     val isDefault: Int? = null

    /**
     * 添加时间
     */
     val createTime: String? = null

}


data class ApplyRecordModel(var list:ArrayList<ApplyRecord>?)
data class ApplyRecord(var applyId:String , var	applyMoney:BigDecimal , var applyStatusStr:String? ,var 	applyTime:Long , var applyTypeStr:String?){

}

data class BalanceModel(var list:ArrayList<BalanceLog>?)

data class BalanceLog(var 	changeMoney:BigDecimal,/*变动金额*/
    var 	changeTime :Long,/*变动时间*/
    var 	logRemark:String?/*变动描述*/)