package com.jxd.android.gohomeapp.libcommon.bean

import java.math.BigDecimal

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.bean
 * @ClassName:      MyBean
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/9 9:15
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/9 9:15
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class MyBean(var lastWeekMoney :BigDecimal , 	var thisWeekMoney:BigDecimal , var 	totalMoney :BigDecimal , var userBalance:BigDecimal) {
}

data class MyModel(var data:MyBean?)