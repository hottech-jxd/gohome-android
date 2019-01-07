package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.libcommon.bean.CouponBean
import com.jxd.android.gohomeapp.libcommon.bean.GoodsDetailBean

import com.jxd.android.gohomeapp.quanmodule.http.wrapper
import com.jxd.android.gohomeapp.quanmodule.repository.GoodsRepository
import io.reactivex.disposables.CompositeDisposable

/**
 *
 * @Package:        com.jxd.android.gohomeapp.quanmodule.viewmodel
 * @ClassName:      GoodsViewModel
 * @Description:     java类作用描述
 * @Author:         jinxiangdong
 * @CreateDate:     2019/1/4 14:05
 * @UpdateUser:     jinxiangdong
 * @UpdateDate:     2019/1/4 14:05
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class GoodsViewModel(application: Application) :  BaseViewModel(application) {

    var liveDataGoodsDetail = MutableLiveData<ApiResult<GoodsDetailBean?>>()
    var liveDataGoodsCategories = MutableLiveData<ApiResult<ArrayList<Category>?>>()
    var liveDataCouponList = MutableLiveData<ApiResult<ArrayList<CouponBean>?>>()



    fun getGoodsDetail(goodsId:String){

        GoodsRepository.getGoodsDetail(goodsId)
            .wrapper()
            .doOnSubscribe{t -> mDisposable.add(t)
                //loading.value =true
                loading.postValue(true)
            }
            .doOnComplete{
                loading.postValue(false)
                complete.value =true
            }
            .doOnError {
                onError(it)
            }
            .subscribe({
                loading.postValue(false)
                //test.postValue("eeeeeeeeeeeeeeeee")
                liveDataGoodsDetail.postValue(it)
            } , {it->
                loading.postValue(false)
                hasError.value =true
                error.value ="error"
            })


    }



    fun getGoodsCategorys(){
        GoodsRepository.getGoodsCategories()
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
                liveDataGoodsCategories.postValue(it)
            }
            .doOnError {
                onError(it)
            }
            .subscribe()
    }


    fun getCouponList(){
        GoodsRepository.getCouponList()
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
                liveDataCouponList.postValue(it)
            }
            .doOnError {
                onError(it )
            }
            .subscribe()
    }


}