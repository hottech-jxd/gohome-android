package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableMap
import com.jxd.android.gohomeapp.libcommon.bean.ApiResult
import com.jxd.android.gohomeapp.libcommon.bean.Category
import com.jxd.android.gohomeapp.libcommon.bean.DetailBean

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
class GoodsViewModel(application: Application) :  AndroidViewModel(application) {

    var loading = MutableLiveData<Boolean>()
    //var test =MutableLiveData<String>()
    var complete=  MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    var hasError = MutableLiveData<Boolean>()
    var liveDataGoodsDetail = MutableLiveData<ApiResult<DetailBean?>>()
    var liveDataGoodsCategories = MutableLiveData<ApiResult<ArrayList<Category>?>>()

    private val mDisposable = CompositeDisposable()



    fun getGoodsDetail(goodsId:Long){

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
                loading.postValue(false)
                hasError.value =true
                error.value ="error"
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
                hasError.postValue(true)
                error.postValue( "error->>"+ it.message )
            }
            .subscribe()
    }


    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }
}