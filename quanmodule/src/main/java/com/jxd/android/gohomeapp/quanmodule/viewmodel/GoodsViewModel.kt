package com.jxd.android.gohomeapp.quanmodule.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.jxd.android.gohomeapp.quanmodule.QuanModule
import com.jxd.android.gohomeapp.quanmodule.bean.*
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanActivityDetailBinding

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

    var liveDataGoodsDetail = MutableLiveData<ApiResult<GoodsDetailModel?>>()
    var liveDataGoodsCategories = MutableLiveData<ApiResult<CategoryModel?>>()
    var liveDataCouponList = MutableLiveData<ApiResult<CouponModel?>>()
    var liveDataSearchResult=MutableLiveData<ApiResult<SearchGoodsModel?>>()
    var liveDataGoodsShareBean = MutableLiveData<ApiResult<GoodsShareModel?>>()
    var liveDataIndexResult = MutableLiveData<ApiResult<IndexModel?>>()
    var liveDataIndexPageResult = MutableLiveData<ApiResult<IndexPageModel?>>()
    var liveDataGoodsOfCategory=MutableLiveData<ApiResult<GoodsOfCategory?>>()
    var liveDataHotSearch=MutableLiveData<ApiResult<HotSearchModel?>>()
    var liveDataThemeListResult = MutableLiveData<ApiResult<GoodsOfCategory?>>()

    fun getGoodsDetail(goodsId:String){
        var userId = QuanModule.userId

        GoodsRepository.getGoodsDetail( userId , goodsId)
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



    fun getGoodsCategorys(goodsSource: Int){
        var userId= QuanModule.userId
        GoodsRepository.getGoodsCategories(userId , goodsSource )
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
        var userId = QuanModule.userId

        GoodsRepository.getCouponList(userId)
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

    fun search(keywords:String? ,page:Int , goodsSource :Int= 0 ){
        var userId = QuanModule.userId

        GoodsRepository.search(userId ,keywords , goodsSource ,page )
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

    fun getShareInfo(goodsId:String , goodsSource:Int ){
        var userId = QuanModule.userId

        GoodsRepository.getShareInfo(userId , goodsId ,goodsSource )
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

        var userId=  QuanModule.userId

        GoodsRepository.index( userId )
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

    fun indexPage(page:Int ){
        var userId = QuanModule.userId

        GoodsRepository.indexPage( userId , page)
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataIndexPageResult.postValue(it)},{onError(it)})
    }

    fun getGoodsOfCategory(categoryId:String, goodsSource: Int , sortEnum: GoodsSortEnum , page:Int){

        var userId = QuanModule.userId

        GoodsRepository.getGoodsOfCategory(userId , categoryId , goodsSource , sortEnum , page )
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

    fun getHotSearch(){
        var userId = QuanModule.userId
        GoodsRepository.getHotSearch(userId)
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataHotSearch.postValue(it)},{onError(it)})
    }

    fun getThemeList(goodsSource: String? , code:String?,sortEnum: GoodsSortEnum,page: Int){
        var userId = QuanModule.userId

        GoodsRepository.getTheme( userId , goodsSource,code, sortEnum , page)
            .wrapper()
            .doOnSubscribe {
                    t->mDisposable.add(t)
                loading.postValue(true)
                hasError.postValue(false)
            }
            .doOnComplete {
                loading.postValue(false)
            }
            .subscribe({liveDataThemeListResult.postValue(it)},{onError(it)})
    }
}

