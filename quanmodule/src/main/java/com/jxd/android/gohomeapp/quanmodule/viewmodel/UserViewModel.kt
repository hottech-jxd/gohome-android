package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.quanmodule.http.wrapper
import com.jxd.android.gohomeapp.quanmodule.repository.GoodsRepository
import com.jxd.android.gohomeapp.quanmodule.repository.UserRepository

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.viewmodel
 * @ClassName:      UserViewModel
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/8 16:41
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/8 16:41
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class UserViewModel(application: Application):  BaseViewModel(application) {

    var liveDataCashApplyResult=MutableLiveData<ApiResult<Any?>>()
    var liveDataSendCodeResult = MutableLiveData<ApiResult<Any?>>()

    fun cashApply(bank:String,
                  branch:String,
                  card:String,
                  name:String,
                  money:String,
                  mobile:String,
                  code:String){
        UserRepository.cashApply(bank,branch,card,name,money,mobile,code)
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .doOnNext{
                liveDataCashApplyResult.postValue(it)
            }
            .doOnError {
                onError(it)
            }
            .subscribe()
    }


    fun sendCode( mobile:String){
        UserRepository.sendCode(mobile)
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .doOnNext{
                liveDataSendCodeResult.postValue(it)
            }
            .doOnError {
                onError(it)
            }
            .subscribe()
    }
}