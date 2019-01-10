package com.jxd.android.gohomeapp.quanmodule.repository

import com.jxd.android.gohomeapp.libcommon.bean.*
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


    fun getMy():Observable<ApiResult<MyBean?>>{
        return apiService!!.myIndex()
    }

    fun getOrderList(userId :String, orderStatus:Int , pageIndex:Int=1):Observable<ApiResult<ArrayList<OrderBean>?>>{
        return apiService!!.getOrderList(userId , orderStatus , pageIndex)
    }

    fun getProfitStat():Observable<ApiResult<ProfitStatBean?>>{
        return apiService!!.getProfitStat()
    }

    fun getMyCollect(page:Int):Observable<ApiResult<ArrayList<FavoriteBean>?>>{
        return apiService!!.getMyCollect(page)
    }

    fun getUserInfo():Observable<ApiResult<UserBean?>>{
        return apiService!!.getUserInfo()
    }
}