package com.jxd.android.gohomeapp.quanmodule.http

import com.jxd.android.gohomeapp.quanmodule.bean.*
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

    /**
     * 初始化
     */
    @GET("init")
    fun init(@Query("userId") userId:String?):Observable<ApiResult<GlobalModel?>>

    @POST("goods/detail")
    @FormUrlEncoded
    fun getGoodsDetail(@Field("userId") userId:String?
                       , @Field("goodsId") goodsId:String
                       , @Field("goodsSource") goodsSource:Int=0 ):Observable<ApiResult<GoodsDetailModel?>>

    /**
     * 获得商品分类列表
     */
    @GET("goods/categories")
    fun getGoodsCategories(@Query("userId") userId:String?, @Query("goodsSource") goodsSource:Int=0):Observable<ApiResult< CategoryModel?>>

    /**
     * 优惠劵列表 随机从后台选择的商品中选5件
     */
    @GET("goods/couponList")
    fun getCouponList(@Query("userId") userId:String? ):Observable<ApiResult<CouponModel?>>

    /**
     * 搜索商品
     */
    @GET("goods/search")
    fun search(@Query("userId") userId:String?
               ,@Query("keywords") keywords:String?
               ,@Query("goodsSource") goodsSource:Int = 0
               ,@Query("page") page:Int):Observable<ApiResult<SearchGoodsModel?>>

    /**
     * 获取商品分享信息
     */
    @GET("goods/share")
    fun share(@Query("userId") userId:String?,@Query("goodsId") goodsId:String , @Query("goodsSource") goodsSource:Int = 0):Observable<ApiResult<GoodsShareModel?>>


    /**
     * 推荐首页
     */
    @GET("goods/index")
    fun index(@Query("userId") userId:String?):Observable<ApiResult<IndexModel?>>

    @GET("goods/indexPage")
    fun indexPage(@Query("userId") userId:String?, @Query("page") page:Int=1):Observable<ApiResult<IndexPageModel?>>

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
    fun getGoodsOfCategories(
        @Field("userId") userId:String?,
        @Field("categoryId") categoryId:String ,
                             @Field("goodsSource") goodsSource:Int ,
                             @Field("sort") sort:String ,
                             @Field("page") page:Int=1):Observable<ApiResult<GoodsOfCategory?>>

    /**
     * 获得热门搜索
     */
    @GET("goods/hotSearch")
    fun hotSearch(@Query("userId") userId:String?):Observable<ApiResult<HotSearchModel?>>

    /**
     *
     */
    @GET("goods/theme")
    fun theme( @Query("userId") userId:String?,
               @Query("goodsSource") goodsSource:String?="0",
              @Query("code") code:String?,
              @Query("sort") sort:String ,
              @Query("page") page:Int =1 ):Observable<ApiResult<GoodsOfCategory?>>

    /**
     * 提现申请
     */
    @POST("user/userApply")
    @FormUrlEncoded
    fun cashApply(
        @Field("userId") userId:String?,
        @Field("bankName")  bankName:String,
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
    @POST("user/getUserAssets")
    @FormUrlEncoded
    fun myIndex( @Field("userId") userId:String?):Observable<ApiResult<MyModel?>>


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
    @FormUrlEncoded
    fun getProfitStat( @Field("userId") userId:String?):Observable<ApiResult<ProfitStatModel?>>

    /**
     * 我的收藏
     */
    @POST("user/getMyCollect")
    @FormUrlEncoded
    fun getMyCollect(@Field("userId") userId:String?
                    ,@Field("platType") platType:Int =-1
                    ,@Field("pageIndex") pageIndex:Int=1
                    ,@Field("pageSize") pageSize:Int=10 ):Observable<ApiResult<FavoriteModel?>>

    /**
     * 用户信息
     */
    @POST("user/userInfo")
    fun getUserInfo():Observable<ApiResult<UserBean?>>

    /**
     * 收藏商品
     */
    @GET("user/goodsCollect")
    fun collect(@Query("userId") userId:String?,@Query("goodsId") goodsId:String , @Query("platType") platType:Int ):Observable<ApiResult<Any?>>

    /**
     * 取消收藏
     */
    @GET("user/cancelCollect")
    fun cancelCollect(@Query("userId") userId:String?,@Query("goodsId") goodsId:String):Observable<ApiResult<Any?>>

    /**
     * 获得提现配置信息
     */
    @GET("user/getApplyConfig")
    fun getApplyConfig(@Query("userId") userId:String?):Observable<ApiResult<ApplyConfigModel?>>

    /**
     * 获取用户的提现账号信息
     */
    @GET("user/getUserApplyAccount")
    fun getUserApplyAccount(@Query("userId") userId:String? ):Observable<ApiResult<UserAccountModel?>>

    /**
     * 分页获取用户提现申请日志
     */
    @GET("user/getApplyList")
    fun getApplyList(@Query("userId") userId:String?, @Query("pageIndex") pageIndex:Int , @Query("pageSize") pageSize:Int =10):Observable<ApiResult<ApplyRecordModel?>>

    /**
     * 分页获取用户余额变动日志
     */
    @GET("user/getBalanceLog")
    fun getBalanceLog(@Query("userId") userId:String?, @Query("pageIndex") pageIndex:Int ,@Query("pageSize")pageSize:Int=10):Observable<ApiResult<BalanceModel?>>


    /**
     * 批量删除收藏的商品
     */
    @GET("user/delCollect")
    fun delCollect(@Query("userId") userId:String? , @Query("cIdList") cIdList:String):Observable<ApiResult<Any?>>

    /**
     * 获取滚动数据(完成)
     */
    @GET("user/getRollDesc")
    fun getRollDesc(@Query("userId") userId:String?):Observable<ApiResult<MessageModel?>>

}