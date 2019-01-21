package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.libcommon.bean.GlobalModel
import com.jxd.android.gohomeapp.libcommon.bean.Globalbean
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.http.wrapper
import com.jxd.android.gohomeapp.quanmodule.repository.CommonRepository

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.viewmodel
 * @ClassName:      CommonViewModel
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/15 9:08
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/15 9:08
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class CommonViewModel(application: Application) :BaseViewModel(application) {
    var liveDataInitResult=MutableLiveData<ApiResult<GlobalModel?>>()

    fun init(){
        var userId= QuanModule.userId
        CommonRepository.init(userId)
            .wrapper()
            .doOnSubscribe{t -> mDisposable.add(t)
                loading.postValue(true)
            }
            .doOnComplete{
                loading.postValue(false)
                complete.value =true
            }
            .subscribe({
                loading.postValue(false)
                liveDataInitResult.postValue(it)
            } , { onError(it)            })

    }
}