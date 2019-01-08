package com.jxd.android.gohomeapp.quanmodule.repository

import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.quanmodule.http.ApiService
import com.jxd.android.gohomeapp.quanmodule.http.RetrofitManager
import io.reactivex.Observable


/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.repository
 * @ClassName:      UserRepository
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/8 16:36
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/8 16:36
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object  UserRepository {
    private var apiService = RetrofitManager.getApiService()

    fun cashApply(bank:String,
                  branch:String,
                  card:String,
                  name:String,
                  money:String,
                  mobile:String,
                  code:String):  Observable<ApiResult<Any?>> {
        return apiService!!.cashApply(bank,branch , card,name , money , mobile , code)
    }

    fun sendCode(mobile: String): Observable<ApiResult<Any?>> {
        return apiService!!.sendCode(mobile)
    }

}