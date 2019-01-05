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