package com.jxd.android.gohomeapp.quanmodule.repository

import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.libcommon.bean.Globalbean
import com.jxd.android.gohomeapp.libcommon.bean.GoodsDetailBean
import com.jxd.android.gohomeapp.quanmodule.http.RetrofitManager
import io.reactivex.Observable

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.repository
 * @ClassName:      CommonRepository
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/15 9:08
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/15 9:08
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object CommonRepository {
    private var apiService = RetrofitManager.getApiService()

    fun init(): Observable<ApiResult<Globalbean?>> {
        return apiService!!.init()
    }
}