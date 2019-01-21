package com.jxd.android.gohomeapp.quanmodule.repository

import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.quanmodule.http.ApiService
import com.jxd.android.gohomeapp.quanmodule.http.RetrofitManager
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.http.Query


/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.repository
 * @ClassName:      GoodsRepository
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/4 13:46
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/4 13:46
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
object GoodsRepository {
    private var apiService = RetrofitManager.getApiService()

    fun getGoodsDetail( userId: String? , goodsId:String ): Observable<ApiResult<GoodsDetailModel?>> {
        return apiService!!.getGoodsDetail( userId , goodsId)
    }

    fun getGoodsCategories(userId:String?, goodsSource: Int):Observable<ApiResult<CategoryModel?>>{
        return apiService!!.getGoodsCategories(userId,goodsSource)
    }

    fun getCouponList(userId: String?):Observable<ApiResult<CouponModel?>>{
        return apiService!!.getCouponList(userId)
    }

    fun search(userId: String?, keywords:String? , goodsSource :Int = 0 , page:Int):Observable<ApiResult<SearchGoodsModel?>>{
        return apiService!!.search( userId , keywords , goodsSource ,page)
    }

    fun getShareInfo(userId: String? , goodsId: String , goodsSource:Int ):Observable<ApiResult<GoodsShareModel?>>{
        return apiService!!.share(userId , goodsId , goodsSource )
    }

    fun index(userId: String?):Observable<ApiResult<IndexModel?>>{
        return apiService!!.index(userId)
    }

    fun indexPage(userId: String? , page:Int = 1):Observable<ApiResult<IndexPageModel?>>{
        return apiService!!.indexPage( userId , page)
    }

    fun getGoodsOfCategory(userId: String? , categoryId:String ,goodsSource: Int ,sortEnum: GoodsSortEnum, page:Int=1):Observable<ApiResult<GoodsOfCategory?>>{
//        var json ="{\"categoryId\":\"$categoryId\",\"sort\": ${sortEnum.code},\"page\":$page}"
//        var requestBody = RequestBody.create(MediaType.parse("application/json"),json )
//        return apiService!!.getGoodsOfCategories(requestBody)
        return apiService!!.getGoodsOfCategories( userId, categoryId ,goodsSource , sortEnum.name , page)
    }

    fun getHotSearch(userId: String?):Observable<ApiResult<HotSearchModel?>>{
        return apiService!!.hotSearch(userId)
    }

    fun getTheme(userId: String?,goodsSource: String? , code :String? , goodsSortEnum: GoodsSortEnum , page:Int=1):Observable<ApiResult<GoodsOfCategory?>>{
        return apiService!!.theme(userId , goodsSource , code , goodsSortEnum.name , page)
    }

}