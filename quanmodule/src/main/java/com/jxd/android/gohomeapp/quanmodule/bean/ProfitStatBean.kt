package com.jxd.android.gohomeapp.quanmodule.bean

import java.math.BigDecimal

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.bean
 * @ClassName:      ProfitStatBean
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/9 13:46
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/9 13:46
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
data class ProfitStatBean(var lastMonthProfit:BigDecimal ,
                          var lastMonthOrderNum:Int,
                          var lastWeekProfit:BigDecimal,
                          var lastWeekOrderNum:Int ,
                          var thisMonthProfit:BigDecimal,
                          var thisMonthOrderNum:Int,
                          var thisWeekProfit:BigDecimal,
                          var thisWeekOrderNum:Int,
                          var todayProfit:BigDecimal,
                          var todayOrderNum:Int,
                          var trendsProfit:ArrayList<ProfitStatDataBean>?,
                          var yesterdayProfit:BigDecimal,
                          var yesterdayOrderNum:Int) {
}

data class ProfitStatModel(var data:ProfitStatBean?)

data class ProfitStatDataBean(var profitData : Long,
                    var	profitAmount :BigDecimal , var profitOrderNum:Int )