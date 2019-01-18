package com.jxd.android.gohomeapp.quanmodule.repository

import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.quanmodule.http.ApiService
import com.jxd.android.gohomeapp.quanmodule.http.RetrofitManager
import io.reactivex.Observable
import java.math.BigDecimal


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
                  money:Int,
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

    fun getOrderList(userId :String, orderStatus:Int , pageIndex:Int=1):Observable<ApiResult<OrderModel?>>{
        return apiService!!.getOrderList2(userId , orderStatus , pageIndex)
    }

    fun getProfitStat():Observable<ApiResult<ProfitStatBean?>>{
        return apiService!!.getProfitStat()
    }

    fun getMyCollect( platType :Int = -1 , pageIndex:Int=1, pageSize:Int=10 ):Observable<ApiResult<ArrayList<FavoriteBean>?>>{
        return apiService!!.getMyCollect(platType , pageIndex ,pageSize)
    }

    fun getUserInfo():Observable<ApiResult<UserBean?>>{
        return apiService!!.getUserInfo()
    }

    fun collect(goodsId:String):Observable<ApiResult<Any?>>{
        return apiService!!.collect(goodsId )
    }

    fun getApplyConfig():Observable<ApiResult<ApplyConfigModel?>>{
        return apiService!!.getApplyConfig()
    }

    fun getApplyAccount():Observable<ApiResult<UserAccountModel?>>{
        return apiService!!.getUserApplyAccount()
    }

    fun getApplyList(pageIndex: Int=1, pageSize:Int=10):Observable<ApiResult<ApplyRecordModel?>>{
        return apiService!!.getApplyList(pageIndex , pageSize)
    }

    fun getBalanceLog(pageIndex: Int=1,pageSize: Int=10):Observable<ApiResult<BalanceModel?>>{
        return apiService!!.getBalanceLog(pageIndex,pageSize)
    }

    fun cancelCollect(goodsId:String):Observable<ApiResult<Any?>>{
        return apiService!!.cancelCollect(goodsId)
    }

    fun delCollect(idList:String):Observable<ApiResult<Any?>>{
        return apiService!!.delCollect(idList)
    }
}