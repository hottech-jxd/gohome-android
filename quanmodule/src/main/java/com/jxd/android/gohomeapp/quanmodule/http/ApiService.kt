package com.jxd.android.gohomeapp.quanmodule.http

import com.jxd.android.gohomeapp.libcommon.bean.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*
import java.math.BigDecimal

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

    /**
     * 初始化
     */
    @GET("init")
    fun init():Observable<ApiResult<Globalbean?>>

    @POST("goods/detail")
    @FormUrlEncoded
    fun getGoodsDetail(@Field("goodsId") goodsId:String
                       , @Field("goodsSource") goodsSource:Int=0 ):Observable<ApiResult<GoodsDetailModel?>>

    /**
     * 获得商品分类列表
     */
    @GET("goods/categories")
    fun getGoodsCategories(@Query("goodsSource") goodsSource:Int=0):Observable<ApiResult< CategoryModel?>>

    /**
     * 优惠劵列表 随机从后台选择的商品中选5件
     */
    @GET("goods/couponList")
    fun getCouponList():Observable<ApiResult<CouponModel?>>

    /**
     * 搜索商品
     */
    @GET("goods/search")
    fun search(@Query("keywords") keywords:String?
               ,@Query("goodsSource") goodsSource:Int = 0
               ,@Query("page") page:Int):Observable<ApiResult<SearchGoodsModel?>>

    /**
     * 获取商品分享信息
     */
    @GET("goods/share")
    fun share(@Query("goodsId") goodsId:String):Observable<ApiResult<GoodsShareModel?>>


    /**
     * 推荐首页
     */
    @GET("goods/index")
    fun index():Observable<ApiResult<IndexModel?>>

    @GET("goods/indexPage")
    fun indexPage(@Query("page") page:Int=1):Observable<ApiResult<IndexPageModel?>>

    /**
     * 获得分类下的商品数据
     */
    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("goods/category")
    fun getGoodsOfCategories(@Body requestBody: RequestBody):Observable<ApiResult<ArrayList<GoodBean>?>>

    /**
     * 获得分类下的商品数据
     */
    @POST("goods/category")
    @FormUrlEncoded
    fun getGoodsOfCategories(@Field("categoryId") categoryId:String ,
                             @Field("goodsSource") goodsSource:Int ,
                             @Field("sort") sort:String ,
                             @Field("page") page:Int=1):Observable<ApiResult<GoodsOfCategory?>>

    /**
     * 提现申请
     */
    @POST("user/userApply")
    @FormUrlEncoded
    fun cashApply(@Field("bankName")  bankName:String,
                  @Field("bankInfo")  bankInfo:String,
                  @Field("bankAccount")  bankAccount:String,
                  @Field("realName")  realName:String,
                  @Field("applyMoney")  applyMoney:Int,
                  @Field("userMobile")  userMobile:String,
                  @Field("verifyCode")  verifyCode:String):Observable<ApiResult<Any?>>

    @POST("user/sendCode")
    @FormUrlEncoded
    fun sendCode(@Field("userMobile") userMobile:String):Observable<ApiResult<Any?>>

    /**
     * 个人首页数据接口
     */
    @POST("user/index")
    fun myIndex():Observable<ApiResult<MyBean?>>


    /**
     * 订单接口
     */
    @POST("user/getOrderList")
    @FormUrlEncoded
    fun getOrderList(@Field("userId") userId:String,
                     @Field("orderStatus") orderStatus:Int=-1 ,
                     @Field("pageIndex") pageIndex:Int=1,
                     @Field("pageSize") pageSize:Int=20,
                     @Field("startTime") startTime:String?="",
                     @Field("endTime") endTime:String?=""):Observable<ApiResult<OrderModel?>>

    @GET("user/getOrderList")
    fun getOrderList2(@Query("userId") userId:String,
                     @Query("orderStatus") orderStatus:Int=-1,
                     @Query("pageIndex") pageIndex:Int=1,
                     @Query("pageSize") pageSize:Int=20,
                     @Query("startTime") startTime:String?="",
                     @Query("endTime") endTime:String?=""):Observable<ApiResult<OrderModel?>>


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

    /**
     * 获得提现配置信息
     */
    @GET("user/getApplyConfig")
    fun getApplyConfig():Observable<ApiResult<ApplyConfigModel?>>

    /**
     * 获取用户的提现账号信息
     */
    @GET("user/getUserApplyAccount")
    fun getUserApplyAccount():Observable<ApiResult<UserAccountModel?>>
}