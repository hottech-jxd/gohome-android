package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.libcommon.bean.Constants.userId
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
    var liveDataMyResult = MutableLiveData<ApiResult<MyBean?>>()
    var liveDataOrderList= MutableLiveData<ApiResult<ArrayList<OrderBean>?>>()
    var liveDataProfitStat=MutableLiveData<ApiResult<ProfitStatBean?>>()
    var liveDataMyCollect=MutableLiveData<ApiResult<ArrayList<FavoriteBean>?>>()

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
            .subscribe({liveDataCashApplyResult.postValue(it)},{onError(it)})
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
            .subscribe({ liveDataSendCodeResult.postValue(it)} , {onError(it)})
    }

    fun getMyIndex(){
        UserRepository.getMy()
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({  liveDataMyResult.postValue(it)},{  onError(it)})
    }

    fun getOrderList(userId:String,orderStatus:Int , pageIndex:Int=1){
        UserRepository.getOrderList(userId , orderStatus , pageIndex)
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe( {liveDataOrderList.postValue(it)}, {onError(it)} )
    }

    fun getProfitStat(){
        UserRepository.getProfitStat()
            .wrapper()
            .doOnSubscribe {
                t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataProfitStat.postValue(it)}, {onError(it)} )
    }

    fun getMyCollect(page:Int){
        UserRepository.getMyCollect(page)
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataMyCollect.postValue(it)}, {onError(it)} )
    }

}