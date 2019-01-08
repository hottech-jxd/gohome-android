package com.jxd.android.gohomeapp.quanmodule.http

import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.libcommon.bean.CouponBean
import com.jxd.android.gohomeapp.libcommon.bean.GoodsDetailBean
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
    fun getGoodsDetail(@Field("goodsId") goodsId:String ):Observable<ApiResult<GoodsDetailBean?>>

    /**
     * 获得商品分类列表
     */
    @GET("goods/categories")
    fun getGoodsCategories():Observable<ApiResult<ArrayList<Category>?>>

    /**
     * 优惠劵列表 随机从后台选择的商品中选5件
     */
    @GET("goods/couponList")
    fun getCouponList():Observable<ApiResult<ArrayList<CouponBean>?>>


    /**
     * 提现申请
     */
    @POST("user/cashApply")
    @FormUrlEncoded
    fun cashApply(@Field("bank")  bank:String,
                  @Field("branch")  branch:String,
                  @Field("card")  card:String,
                  @Field("name")  name:String,
                  @Field("money")  money:String,
                  @Field("mobile")  mobile:String,
                  @Field("code")  code:String):Observable<ApiResult<Any?>>

    @POST("user/sendCode")
    @FormUrlEncoded
    fun sendCode(@Field("mobile") mobile:String):Observable<ApiResult<Any?>>
}