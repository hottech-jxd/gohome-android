package com.jxd.android.gohomeapp.quanmodule

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jxd.android.gohomeapp.quanmodule.base.ARouterPath
import java.lang.reflect.Type

/**
 *
 * @Package:        com.jxd.android.gohomeapp.libcommon.util
 * @ClassName:      JsonConvertImpl
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/10 13:45
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/10 13:45
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
@Route(path = ARouterPath.QuanModuleJsonService)
class JsonConvertImpl : SerializationService {

    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        return parseObject(input , clazz)
    }

    override fun init(context: Context?) {

    }

    override fun object2Json(instance: Any?): String {
        return GsonBuilder().serializeNulls().create().toJson(instance)
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        return GsonBuilder().serializeNulls().create().fromJson(input , clazz)
    }
}