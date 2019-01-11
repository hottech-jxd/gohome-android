package com.jxd.android.gohomeapp.libcommon.bean

enum class ApiResultCodeEnum (var code :Int,var desc :String ){

    SUCCESS(20000 , "请求成功"),
    TOKEN_ERROR(40003,"交互会话已过期，请重新登录"),
    REQUEST_NOT_EXIST(2,"无法执行的请求或请求交互不存在"),
    PARAMETER_ERROR(3,"参数错误"),
    PARAMETER_TYPE_ERROR(4,"无法识别的参数类型"),


    SMS_LOGIN(20,"用户已登录，需使用短信验证码确认登录。"),

    SYSTEM_ERROR(99,"系统错误"),
    ACCOUNT_EXIST(6002,"账号已经存在"),
//    MISS_PARAMETER(400,"缺少请求参数"),
//    SIGN_MISS(401,"签名未传"),
//    SING_ERROR(402,"签名错误"),
//    NO_INFO(403,"没有信息"),
//    SERVER_ERROR(500,"服务器错误"),
//    USER_NO_LOGIN(1000,"用户未登录"),
//    USER_FREEZE(1001,"用户已被冻结"),
//    USER_ILLEGAL(1002,"用户登录信息非法"),
    //TOKEN_ERROR(4003, "token失效"),
    //AUTH_ERROR(4123 ,"未完成认证,前往认证"),
    //EXIST_RECYCLE_ERROR(4502,"存在回收中的记录，请完成后再次申请!")
}


enum class ThemeCategoryEnum(var code: Int,var desc:String){
    GoodsList(0,"商品列表"),
    Link(1,"外链"),
    Single(2,"单品")
}

enum class ThemeIndexRecommendModeEnum(var code: Int,var desc:String){
    LimitedTheme(0,"限时购主题 [商品列表形式展示，同时显示倒计时]"),
    ListTheme(1,"列表主题 [商品列表形式展示，可设置显示条数或分页获取]"),
    SingleTheme(2,"单图主题 [单图展示]"),
    Slide(3,"幻灯")
}

enum class GoodsSortEnum(var code: Int , var desc: String){
    Newed(1,"最新"),
    PriceAsc(2,"价格升序"),
    PriceDes(3,"价格降序"),
    RewardDes(4,"佣金"),
    SaleDes(5,"销量")
}