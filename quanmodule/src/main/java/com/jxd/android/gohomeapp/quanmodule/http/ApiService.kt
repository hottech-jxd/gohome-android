package com.jxd.android.gohomeapp.quanmodule.http

import com.jxd.android.gohomeapp.libcommon.bean.*
import io.reactivex.Observable
import okhttp3.RequestBody
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
     * 搜索商品
     */
    @GET("goods/search")
    fun search(@Query("keywords") keywords:String?,@Query("page") page:Int):Observable<ApiResult<ArrayList<SearchGoodsBean>?>>

    /**
     * 获取商品分享信息
     */
    @GET("goods/share")
    fun share(@Query("goodsId") goodsId:String):Observable<ApiResult<GoodsShareBean?>>


    /**
     * 推荐首页
     */
    @GET("goods/index")
    fun index():Observable<ApiResult<ArrayList<IndexBean>?>>

    /**
     * 获得分类下的商品数据
     */
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("goods/category")
    fun getGoodsOfCategories(@Body requestBody: RequestBody):Observable<ApiResult<ArrayList<GoodBean>?>>

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

    /**
     * 个人首页数据接口
     */
    @POST("user/index")
    fun myIndex():Observable<ApiResult<MyBean?>>


    /**
     * 订单接口
     */
    @POST("user/orderList")
    @FormUrlEncoded
    fun getOrderList(@Field("userId") userId:String,
                     @Field("orderStatus") orderStatus:Int ,
                     @Field("pageIndex") pageIndex:Int=1,
                     @Field("pageSize") pageSize:Int=20,
                     @Field("startTime") startTime:String?="",
                     @Field("endTime") endTime:String?=""):Observable<ApiResult<ArrayList<OrderBean>?>>

    /**
     *收益统计
     */
    @POST("user/profitStat")
    fun getProfitStat():Observable<ApiResult<ProfitStatBean?>>

    /**
     * 我的收藏
     */
    @POST("user/myCollect")
    @FormUrlEncoded
    fun getMyCollect(@Field("page") page:Int):Observable<ApiResult<ArrayList<FavoriteBean>?>>

    /**
     * 用户信息
     */
    @POST("user/userInfo")
    fun getUserInfo():Observable<ApiResult<UserBean?>>

    @GET("user/collect")
    fun collect(@Query("goodsId") goodsId:String):Observable<ApiResult<Any?>>
}