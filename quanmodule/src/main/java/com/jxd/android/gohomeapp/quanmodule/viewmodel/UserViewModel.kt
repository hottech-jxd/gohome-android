package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.bean.*
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
    var liveDataOrderList= MutableLiveData<ApiResult<OrderModel?>>()
    var liveDataProfitStat=MutableLiveData<ApiResult<ProfitStatModel?>>()
    var liveDataMyCollect=MutableLiveData<ApiResult<FavoriteModel?>>()
    var liveDataCollectResult = MutableLiveData<ApiResult<Any?>>()
    var liveDataApplyConfigResult = MutableLiveData<ApiResult<ApplyConfigModel?>>()
    var liveDataApplyAccountResult = MutableLiveData<ApiResult<UserAccountModel?>>()
    var liveDataApplyListResult=MutableLiveData<ApiResult<ApplyRecordModel?>>()
    var liveDataBalanceLogResult = MutableLiveData<ApiResult<BalanceModel?>>()
    var liveDataCancelCollectResult = MutableLiveData<ApiResult<Any?>>()
    var liveDataDelCollectResult = MutableLiveData<ApiResult<Any?>>()
    var liveDataRollDescResult = MutableLiveData<ApiResult<MessageModel?>>()

    companion object {
        //var liveDataUserInfo = MutableLiveData<ApiResult<UserBean?>>()
        var liveDataMyResult = MutableLiveData<ApiResult<MyModel?>>()
    }

    fun cashApply(
        bank:String,
                  branch:String,
                  card:String,
                  name:String,
                  money:Int,
                  mobile:String,
                  code:String){

        var userId = QuanModule.userId

        UserRepository.cashApply(userId , bank,branch,card,name,money,mobile,code)
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

        var userId = QuanModule.userId

        UserRepository.getMy(userId)
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
        var userId = QuanModule.userId

        UserRepository.getProfitStat(userId)
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

        var userId = QuanModule.userId

        UserRepository.getMyCollect( userId , platType , pageIndex  )
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


//    fun getUserInfo(showProgress: Boolean) {
//        UserRepository.getUserInfo()
//            .wrapper()
//            .doOnSubscribe { t ->
//                mDisposable.add(t)
//                if(showProgress) {
//                    loading.postValue(true)
//                }
//                hasError.postValue(false)
//            }
//            .doOnComplete {
//                if(showProgress) {
//                    loading.postValue(false)
//                }
//            }
//            .subscribe(
//                {  UserViewModel.liveDataUserInfo.postValue(it) }, { onError(it) })
//    }


    fun collect(goodsId : String , platType: Int) {
        var userId = QuanModule.userId

        UserRepository.collect(userId , goodsId , platType )
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
        var userId = QuanModule.userId
        UserRepository.getApplyConfig(userId)
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
        var userId = QuanModule.userId
        UserRepository.getApplyAccount(userId)
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
        var userId = QuanModule.userId
        UserRepository.getApplyList(userId , pageIndex , pageSize )
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
        var userId = QuanModule.userId
        UserRepository.getBalanceLog(userId , pageIndex , pageSize )
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
        var userId = QuanModule.userId

        UserRepository.cancelCollect(userId , goodsId)
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
        var userId = QuanModule.userId

        UserRepository.delCollect(userId , idList)
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
                {  liveDataDelCollectResult.postValue(it) }, { onError(it) })
    }

    fun getRollDesc() {
        var userId = QuanModule.userId

        UserRepository.getRollDesc(userId )
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
                {  liveDataRollDescResult.postValue(it) }, { onError(it) })
    }
}