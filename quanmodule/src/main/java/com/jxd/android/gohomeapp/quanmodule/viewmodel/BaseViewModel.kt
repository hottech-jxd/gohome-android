package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.viewmodel
 * @ClassName:      BaseViewModel
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/7 11:06
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/7 11:06
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
open class BaseViewModel(application: Application ) : AndroidViewModel(application) {
    var loading = MutableLiveData<Boolean>()
    var complete=  MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    var hasError = MutableLiveData<Boolean>()
    protected val mDisposable = CompositeDisposable()



    fun onError(throwable: Throwable){
        loading.postValue(false)
        hasError.postValue(true)
        error.postValue( "error->>"+ throwable.message )
    }


    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }
}