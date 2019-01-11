package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.jxd.android.gohomeapp.libcommon.bean.*

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
    var liveDataSearchResult=MutableLiveData<ApiResult<ArrayList<SearchGoodsBean>?>>()
    var liveDataGoodsShareBean = MutableLiveData<ApiResult<GoodsShareBean?>>()
    var liveDataIndexResult = MutableLiveData<ApiResult<ArrayList<IndexBean>?>>()
    var liveDataGoodsOfCategory=MutableLiveData<ApiResult<ArrayList<GoodBean>?>>()

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
            .subscribe({
                loading.postValue(false)
                liveDataGoodsDetail.postValue(it)
            } , { onError(it)            })

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
            .subscribe({liveDataGoodsCategories.postValue(it)},{onError(it)})
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
            .subscribe({liveDataCouponList.postValue(it)},{onError(it)})
    }

    fun search(keywords:String?,page:Int){
        GoodsRepository.search(keywords ,page )
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataSearchResult.postValue(it)},{onError(it)})
    }

    fun getShareInfo(goodsId:String ){
        GoodsRepository.getShareInfo( goodsId )
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataGoodsShareBean.postValue(it)},{onError(it)})
    }

    fun index(){
        GoodsRepository.index()
            .wrapper()
            .doOnSubscribe {
                t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataIndexResult.postValue(it)},{onError(it)})
    }

    fun getGoodsOfCategory(categoryId:String, sortEnum: GoodsSortEnum , page:Int){
        GoodsRepository.getGoodsOfCategory(categoryId , sortEnum , page )
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataGoodsOfCategory.postValue(it)},{onError(it)})
    }

}