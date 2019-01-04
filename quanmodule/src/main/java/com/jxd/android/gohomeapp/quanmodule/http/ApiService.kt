package com.jxd.android.gohomeapp.quanmodule.http

import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.libcommon.bean.DetailBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.http
 * @ClassName:      ApiService
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/4 11:14
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/4 11:14
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
interface ApiService {

    @POST("goods/detail")
    @FormUrlEncoded
    fun getGoodsDetail(@Field("goodsid") goodsid:Long ):Observable<ApiResult<DetailBean?>>
}