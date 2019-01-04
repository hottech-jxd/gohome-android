package com.jxd.android.gohomeapp.quanmodule.http

import android.arch.lifecycle.LifecycleOwner
//import com.uber.autodispose.AutoDispose
//import com.uber.autodispose.ObservableSubscribeProxy
//import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.http
 * @ClassName:      MethodExtention
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/4 14:24
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/4 14:24
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */


fun <T> Observable<T>.wrapper(delay:Long=0):Observable<T>{
    return this.subscribeOn(Schedulers.io())
        .delay(delay , java.util.concurrent.TimeUnit.MICROSECONDS)
        .observeOn(AndroidSchedulers.mainThread())
}

//fun <T>Observable<T>.bindLifeCycle(owner: LifecycleOwner): ObservableSubscribeProxy<T> {
//    return this.`as` (AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//}