package com.jxd.android.gohomeapp.quanmodule.repository

import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.libcommon.bean.DetailBean
import com.jxd.android.gohomeapp.quanmodule.http.ApiService
import com.jxd.android.gohomeapp.quanmodule.http.RetrofitManager
import io.reactivex.Observable


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

    fun getGoodsDetail(goodsId:Long): Observable<ApiResult<DetailBean?>> {
        return apiService!!.getGoodsDetail(goodsId)
    }

    fun getGoodsCategories():Observable<ApiResult<ArrayList<Category>?>>{
        return apiService!!.getGoodsCategories()
    }

}