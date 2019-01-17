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

    fun getGoodsDetail(goodsId:String ): Observable<ApiResult<GoodsDetailModel?>> {
        return apiService!!.getGoodsDetail(goodsId)
    }

    fun getGoodsCategories():Observable<ApiResult<CategoryModel?>>{
        return apiService!!.getGoodsCategories()
    }

    fun getCouponList():Observable<ApiResult<CouponModel?>>{
        return apiService!!.getCouponList()
    }

    fun search(keywords:String? , goodsSource :Int = 0 , page:Int):Observable<ApiResult<SearchGoodsModel?>>{
        return apiService!!.search(keywords , goodsSource ,page)
    }

    fun getShareInfo(goodsId: String):Observable<ApiResult<GoodsShareModel?>>{
        return apiService!!.share(goodsId)
    }

    fun index():Observable<ApiResult<IndexModel?>>{
        return apiService!!.index()
    }

    fun indexPage(page:Int = 1):Observable<ApiResult<IndexPageModel?>>{
        return apiService!!.indexPage(page)
    }

    fun getGoodsOfCategory( categoryId:String ,goodsSource: Int ,sortEnum: GoodsSortEnum, page:Int=1):Observable<ApiResult<GoodsOfCategory?>>{
//        var json ="{\"categoryId\":\"$categoryId\",\"sort\": ${sortEnum.code},\"page\":$page}"
//        var requestBody = RequestBody.create(MediaType.parse("application/json"),json )
//        return apiService!!.getGoodsOfCategories(requestBody)
        return apiService!!.getGoodsOfCategories(categoryId ,goodsSource , sortEnum.name , page)
    }

    fun getHotSearch():Observable<ApiResult<HotSearchModel?>>{
        return apiService!!.hotSearch()
    }

}