package com.jxd.android.gohomeapp.libcommon.bean

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
data class ProfitStatBean(var lastMonth:BigDecimal ,
                          var lastWeek:BigDecimal,
                          var thisMonth:BigDecimal,
                          var thisWeek:BigDecimal,
                          var today:BigDecimal,
                          var total:BigDecimal,
                          var trends:ArrayList<ProfitStatDataBean>?,
                          var yesterday:BigDecimal ) {
}

data class ProfitStatDataBean(var date : String,
                    var	money:BigDecimal)