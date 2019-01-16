package com.jxd.android.gohomeapp.quanmodule

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseActivity
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanActivityDetailBinding
import com.jxd.android.gohomeapp.quanmodule.fragment.GoodsDetailFragment
import com.youth.banner.listener.OnBannerListener

@Route(path = ARouterPath.QuanActivityGoodsDetailPath)
class DetailActivity : BaseActivity(),View.OnClickListener , OnBannerListener {
    //private var detailAdapter:DetailAdapter?=null
    //private var data=ArrayList<PictureBean>()

    var quanActivityDetailBinding :QuanActivityDetailBinding?=null
    @Autowired @JvmField var goodsId:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

//    private fun setBanner(goodsDetail: GoodsDetailBean? ){
//        if(goodsDetail==null) return
//        if(goodsDetail.pictureUrls==null) return
//
////        var images = ArrayList<String>()
////        images.add("http://t04img.yangkeduo.com/images/2018-05-26/3308bf00afb37922ceef70b9991e0dfd.jpeg")
////        images.add("http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg")
////        images.add("http://t08img.yangkeduo.com/images/2018-04-27/facf8f2067f128b3b2af353411c3ffcd.jpeg")
////
//        goodsdetail_banner.setImages( goodsDetail.pictureUrls )
//        goodsdetail_banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
//        goodsdetail_banner.setIndicatorGravity(BannerConfig.RIGHT )
//        goodsdetail_banner.setOnBannerListener(this)
//        goodsdetail_banner.setImageLoader(FrescoImageLoader( goodsdetail_banner , DensityUtils.getScreenWidth(this)))
//        goodsdetail_banner.start()
//
//    }

//    private fun setDetail( goodsDetail:GoodsDetailBean? ){
//        if(goodsDetail==null) return
//        if(goodsDetail.detail==null) return
//
//
//        data.clear()
//
//        data.add( PictureBean( goodsDetail.detail!! ) )
//
////        for(i in 0 .. 10){
////            data.add(DetailBean(i,0,"http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg"))
////        }
////        data.add(DetailBean(4,0,"http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg"))
////
////        data.add(DetailBean(4,0,"http://t04img.yangkeduo.com/images/2018-05-26/3308bf00afb37922ceef70b9991e0dfd.jpeg"))
////
////        data.add(DetailBean(4,0,"http://t08img.yangkeduo.com/images/2018-04-27/facf8f2067f128b3b2af353411c3ffcd.jpeg"))
//
//
//        //detail_recyclerView.layoutManager=LinearLayoutManager(this)
//        detailAdapter!!.setNewData(data)//= DetailAdapter(data)
//    }

    override fun initView(){

        ARouter.getInstance().inject(this)

        quanActivityDetailBinding = DataBindingUtil.setContentView( this  , R.layout.quan_activity_detail)


        //test
        //goodsId = "3109518123"
        //

        var goodsDetailFragment = ARouter.getInstance().build(ARouterPath.QuanFragmentGoodsDetailPath)
            .withString("goodsId" , goodsId).navigation() as GoodsDetailFragment
        loadRootFragment(R.id.detail_container , goodsDetailFragment )


//        var goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)
//

//        quanActivityDetailBinding!!.goodsViewModel = goodsViewModel
//        quanActivityDetailBinding!!.setLifecycleOwner(this)
//
//        goodsViewModel.liveDataGoodsDetail.observe(this , Observer { it->
//            if(it!!.resultCode != ApiResultCodeEnum.SUCCESS.code){
//                showToast(it!!.resultMsg)
//                return@Observer
//            }
//
//            setBanner( it.detail )
//            setDetail(it.detail )
//        })


//        header_left_image.setOnClickListener(this)

//        detailAdapter = DetailAdapter(data)

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

//        var top = LayoutInflater.from(this).inflate(R.layout.layout_detail_top, null)
//        detailAdapter!!.removeAllHeaderView()
//        detailAdapter!!.addHeaderView(top)
//
//        detail_recyclerView.adapter = detailAdapter
//
//
//        quanActivityDetailBinding!!.goodsViewModel!!.getGoodsDetail(goodsId)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.detail_share_lay->{

            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        showToast( "permissions" )
    }

    override fun OnBannerClick(position: Int) {
        showToast("position"+ position )
    }


    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
    }
}
