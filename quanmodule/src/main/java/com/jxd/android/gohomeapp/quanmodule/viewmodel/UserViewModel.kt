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
    var liveDataOrderList= MutableLiveData<ApiResult<OrderModel?>>()
    var liveDataProfitStat=MutableLiveData<ApiResult<ProfitStatBean?>>()
    var liveDataMyCollect=MutableLiveData<ApiResult<ArrayList<FavoriteBean>?>>()
    var liveDataCollectResult = MutableLiveData<ApiResult<Any?>>()
    var liveDataApplyConfigResult = MutableLiveData<ApiResult<ApplyConfigModel?>>()
    var liveDataApplyAccountResult = MutableLiveData<ApiResult<UserAccountModel?>>()
    var liveDataApplyListResult=MutableLiveData<ApiResult<ApplyRecordModel?>>()
    var liveDataBalanceLogResult = MutableLiveData<ApiResult<BalanceModel?>>()
    var liveDataCancelCollectResult = MutableLiveData<ApiResult<Any?>>()
    var liveDataDelCollectResul = MutableLiveData<ApiResult<Any?>>()

    companion object {
        var liveDataUserInfo = MutableLiveData<ApiResult<UserBean?>>()
    }

    fun cashApply(bank:String,
                  branch:String,
                  card:String,
                  name:String,
                  money:Int,
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

    fun getMyCollect( platType :Int= -1,  pageIndex:Int ){
        UserRepository.getMyCollect(platType , pageIndex  )
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

    fun getUserInfo(showProgress: Boolean) {
        UserRepository.getUserInfo()
            .wrapper()
            .doOnSubscribe { t ->
                mDisposable.add(t)
                if(showProgress) {
                    loading.postValue(true)
                }
                hasError.postValue(false)
            }
            .doOnComplete {
                if(showProgress) {
                    loading.postValue(false)
                }
            }
            .subscribe(
                {  UserViewModel.liveDataUserInfo.postValue(it) }, { onError(it) })
    }

    fun collect(goodsId : String ) {
        UserRepository.collect(goodsId)
            .wrapper()
            .doOnSubscribe { t ->
                mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe(
                {  liveDataCollectResult.postValue(it) }, { onError(it) })
    }

    fun getApplyConfig() {
        UserRepository.getApplyConfig()
            .wrapper()
            .doOnSubscribe { t ->
                mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe(
                {  liveDataApplyConfigResult.postValue(it) }, { onError(it) })
    }

    fun getApplyAccount() {
        UserRepository.getApplyAccount()
            .wrapper()
            .doOnSubscribe { t ->
                mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe(
                {  liveDataApplyAccountResult.postValue(it) }, { onError(it) })
    }

    fun getApplyList(pageIndex: Int, pageSize:Int=10) {
        UserRepository.getApplyList(pageIndex , pageSize )
            .wrapper()
            .doOnSubscribe { t ->
                mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe(
                {  liveDataApplyListResult.postValue(it) }, { onError(it) })
    }

    fun getBalanceLog(pageIndex: Int, pageSize:Int=10) {
        UserRepository.getBalanceLog(pageIndex , pageSize )
            .wrapper()
            .doOnSubscribe { t ->
                mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe(
                {  liveDataBalanceLogResult.postValue(it) }, { onError(it) })
    }

    fun cancelCollect( goodsId: String) {
        UserRepository.cancelCollect(goodsId)
            .wrapper()
            .doOnSubscribe { t ->
                mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe(
                {  liveDataCancelCollectResult.postValue(it) }, { onError(it) })
    }

    fun delCollect( idList: String) {
        UserRepository.delCollect( idList)
            .wrapper()
            .doOnSubscribe { t ->
                mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe(
                {  liveDataDelCollectResul.postValue(it) }, { onError(it) })
    }
}