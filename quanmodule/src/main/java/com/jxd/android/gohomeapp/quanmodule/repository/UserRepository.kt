package com.jxd.android.gohomeapp.quanmodule.repository

import com.jxd.android.gohomeapp.quanmodule.bean.*
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

    fun cashApply(
        userId: String?,
        bank:String,
                  branch:String,
                  card:String,
                  name:String,
                  money:Int,
                  mobile:String,
                  code:String):  Observable<ApiResult<Any?>> {
        return apiService!!.cashApply(userId , bank,branch , card,name , money , mobile , code)
    }

    fun sendCode(mobile: String): Observable<ApiResult<Any?>> {
        return apiService!!.sendCode(mobile)
    }


    fun getMy(userId: String?):Observable<ApiResult<MyModel?>>{
        return apiService!!.myIndex(userId)
    }

    fun getOrderList(userId :String, orderStatus:Int , pageIndex:Int=1):Observable<ApiResult<OrderModel?>>{
        return apiService!!.getOrderList2(userId , orderStatus , pageIndex)
    }

    fun getProfitStat(userId: String?):Observable<ApiResult<ProfitStatModel?>>{
        return apiService!!.getProfitStat(userId)
    }

    fun getMyCollect(userId: String?, platType :Int = -1 , pageIndex:Int=1, pageSize:Int=10 ):Observable<ApiResult<FavoriteModel?>>{
        return apiService!!.getMyCollect(userId , platType , pageIndex ,pageSize)
    }

//    fun getUserInfo():Observable<ApiResult<UserBean?>>{
//        return apiService!!.getUserInfo()
//    }

    fun collect(userId: String? , goodsId:String, platType: Int):Observable<ApiResult<Any?>>{
        return apiService!!.collect(userId , goodsId , platType )
    }

    fun getApplyConfig(userId: String?):Observable<ApiResult<ApplyConfigModel?>>{
        return apiService!!.getApplyConfig(userId )
    }

    fun getApplyAccount(userId: String?):Observable<ApiResult<UserAccountModel?>>{
        return apiService!!.getUserApplyAccount(userId)
    }

    fun getApplyList(userId: String? , pageIndex: Int=1, pageSize:Int=10):Observable<ApiResult<ApplyRecordModel?>>{
        return apiService!!.getApplyList(userId , pageIndex , pageSize)
    }

    fun getBalanceLog(userId: String? , pageIndex: Int=1,pageSize: Int=10):Observable<ApiResult<BalanceModel?>>{
        return apiService!!.getBalanceLog(userId , pageIndex,pageSize)
    }

    fun cancelCollect(userId: String? , goodsId:String):Observable<ApiResult<Any?>>{
        return apiService!!.cancelCollect( userId , goodsId)
    }

    fun delCollect( userId: String? , idList:String):Observable<ApiResult<Any?>>{
        return apiService!!.delCollect(userId , idList)
    }

    fun getRollDesc(userId: String?):Observable<ApiResult<MessageModel?>>{
        return apiService!!.getRollDesc(userId)
    }
}