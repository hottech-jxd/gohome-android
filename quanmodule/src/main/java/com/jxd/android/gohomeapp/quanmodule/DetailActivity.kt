package com.jxd.android.gohomeapp.quanmodule

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseActivity
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.bean.DetailBean
import com.jxd.android.gohomeapp.libcommon.util.DensityUtils
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.adapter.DetailAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanActivityDetailBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.quan_activity_detail.*


@Route(path = ARouterPath.QuanActivityGoodsDetailPath)
class DetailActivity : BaseActivity(),View.OnClickListener , OnBannerListener {
    private var detailAdapter:DetailAdapter?=null
    private var data=ArrayList<DetailBean>()
    //private var goodsViewModel:GoodsViewModel?=null
    private var quanActivityDetailBinding :QuanActivityDetailBinding?=null
    @Autowired
    var goodsId:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

    }

    private fun setBanner(){
        var images = ArrayList<String>()
        images.add("http://t04img.yangkeduo.com/images/2018-05-26/3308bf00afb37922ceef70b9991e0dfd.jpeg")
        images.add("http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg")
        images.add("http://t08img.yangkeduo.com/images/2018-04-27/facf8f2067f128b3b2af353411c3ffcd.jpeg")

        goodsdetail_banner.setImages(images)
        goodsdetail_banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        goodsdetail_banner.setIndicatorGravity(BannerConfig.RIGHT )
        goodsdetail_banner.setOnBannerListener(this)
        goodsdetail_banner.setImageLoader(FrescoImageLoader( goodsdetail_banner , DensityUtils.getScreenWidth(this)))
        goodsdetail_banner.start()
    }

    private fun setDetail(){
        data.clear()
        for(i in 0 .. 10){
            data.add(DetailBean(i,0,"http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg"))
        }
        data.add(DetailBean(4,0,"http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg"))

        data.add(DetailBean(4,0,"http://t04img.yangkeduo.com/images/2018-05-26/3308bf00afb37922ceef70b9991e0dfd.jpeg"))

        data.add(DetailBean(4,0,"http://t08img.yangkeduo.com/images/2018-04-27/facf8f2067f128b3b2af353411c3ffcd.jpeg"))


        //detail_recyclerView.layoutManager=LinearLayoutManager(this)
        detailAdapter!!.setNewData(data)//= DetailAdapter(data)
    }

    override fun initView(){

        var goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)

        quanActivityDetailBinding = DataBindingUtil.setContentView(this , R.layout.quan_activity_detail)
        quanActivityDetailBinding!!.goodsViewModel = goodsViewModel
        quanActivityDetailBinding!!.setLifecycleOwner(this)

        goodsViewModel!!.liveDataGoodsDetail.observe(this , Observer { it->
            if(it!!.resultCode != ApiResultCodeEnum.SUCCESS.code){
                showToast(it!!.resultMsg)
                return@Observer
            }

            setBanner()
            setDetail()
        })


//        header_left_image.setOnClickListener(this)

        detailAdapter = DetailAdapter(data)

        //goodsdetail_image.setImageURI("http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg")

//        var images = ArrayList<String>()
//        images.add("http://t04img.yangkeduo.com/images/2018-05-26/3308bf00afb37922ceef70b9991e0dfd.jpeg")
//        images.add("http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg")
//        images.add("http://t08img.yangkeduo.com/images/2018-04-27/facf8f2067f128b3b2af353411c3ffcd.jpeg")
//
//        goodsdetail_banner.setImages(images)
//        goodsdetail_banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
//        goodsdetail_banner.setIndicatorGravity(BannerConfig.RIGHT )
//        goodsdetail_banner.setOnBannerListener(this)
//        goodsdetail_banner.setImageLoader(FrescoImageLoader( goodsdetail_banner , DensityUtils.getScreenWidth(this)))
//        goodsdetail_banner.start()

//        for(i in 0 .. 10){
//            data.add(DetailBean(i,0,"http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg"))
//        }
//        data.add(DetailBean(4,0,"http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg"))
//
//        data.add(DetailBean(4,0,"http://t04img.yangkeduo.com/images/2018-05-26/3308bf00afb37922ceef70b9991e0dfd.jpeg"))
//
//        data.add(DetailBean(4,0,"http://t08img.yangkeduo.com/images/2018-04-27/facf8f2067f128b3b2af353411c3ffcd.jpeg"))
//
//
//        detail_recyclerView.layoutManager=LinearLayoutManager(this)
//        detailAdapter= DetailAdapter(data)

        var top = LayoutInflater.from(this).inflate(R.layout.layout_detail_top, null)
        detailAdapter!!.removeAllHeaderView()
        detailAdapter!!.addHeaderView(top)

        detail_recyclerView.adapter = detailAdapter


        quanActivityDetailBinding!!.goodsViewModel!!.getGoodsDetail(goodsId)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
        }
    }

    override fun OnBannerClick(position: Int) {
        showToast("position"+ position )
    }
}
