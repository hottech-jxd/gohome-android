package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jxd.android.gohomeapp.quanmodule.FrescoImageLoader

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.DetailAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentGoodsDetailBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import android.arch.lifecycle.Observer
import android.content.*
import android.net.Uri
import android.text.TextPaint
import android.text.TextUtils
import android.widget.TextView
import com.jxd.android.gohomeapp.quanmodule.base.ARouterPath
import com.jxd.android.gohomeapp.quanmodule.base.BaseFragment
import com.jxd.android.gohomeapp.quanmodule.bean.*
import com.jxd.android.gohomeapp.quanmodule.databinding.LayoutDetailTopBinding
import com.jxd.android.gohomeapp.quanmodule.util.AppUtil
import com.jxd.android.gohomeapp.quanmodule.util.DensityUtils
import com.jxd.android.gohomeapp.quanmodule.util.showToast
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import com.wx.goodview.GoodView
import kotlinx.android.synthetic.main.quan_fragment_goods_detail.*

/**
 * A simple [Fragment] subclass.
 * Use the [GoodsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path = ARouterPath.QuanFragmentGoodsDetailPath)
class GoodsDetailFragment : BaseFragment() , OnBannerListener ,View.OnLongClickListener , View.OnClickListener {
    private var detailAdapter: DetailAdapter?=null
    private var data=ArrayList<PictureBean>()
    @Autowired  @JvmField var goodsId:String=""
    private var quanFragmentDetailBinding : QuanFragmentGoodsDetailBinding?=null
    private var detailTopBinding:LayoutDetailTopBinding?=null
    private var goodDetail: GoodsDetailBean?=null

    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        quanFragmentDetailBinding =
                DataBindingUtil.inflate(inflater, R.layout.quan_fragment_goods_detail, container, false)
        var goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)
        quanFragmentDetailBinding!!.goodsViewModel = goodsViewModel
        quanFragmentDetailBinding!!.userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        quanFragmentDetailBinding!!.clickHandler = this
        return quanFragmentDetailBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        quanFragmentDetailBinding!!.goodsViewModel!!.getGoodsDetail(goodsId)
    }

    private fun setBanner(goodsDetailModel: GoodsDetailModel? ){
        if(goodsDetailModel==null || goodsDetailModel.detail==null ) return
        if(goodsDetailModel.detail!!.pictureUrls==null) return

        var goodsDetail = goodsDetailModel.detail!!
        goodsdetail_banner.setImages( goodsDetail.pictureUrls )
        goodsdetail_banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        goodsdetail_banner.setIndicatorGravity(BannerConfig.RIGHT )
        goodsdetail_banner.setOnBannerListener(this)
        goodsdetail_banner.setImageLoader(FrescoImageLoader( goodsdetail_banner , DensityUtils.getScreenWidth(this.context!!)))
        goodsdetail_banner.start()
    }

    private fun setDetail( goodsDetailModel :GoodsDetailModel? ){
        if(goodsDetailModel==null ||goodsDetailModel.detail==null ) return
        if(goodsDetailModel!!.detail==null) return


        goodDetail = goodsDetailModel.detail

        if(detailTopBinding!=null){
            detailTopBinding!!.goodsBean = goodsDetailModel.detail
        }

        data.clear()

        //data.add( PictureBean( goodsDetailModel.detail!!.detail!! ) )
        if(goodDetail!!.pictureUrls!=null) {
            for(item in goodDetail!!.pictureUrls!!) {
                data.add(PictureBean(item))
            }
        }

        detailAdapter!!.setNewData(data)

        detail_share_reword.text = "￥${goodsDetailModel.detail!!.reward}"
        detail_sheng.text ="￥${goodsDetailModel.detail!!.reward}"

        detail_favorite_image.setImageResource( if( goodsDetailModel.detail!!.collect ) R.mipmap.favorite_red else R.mipmap.favorite_gray )
    }

    override fun initView(){
        ARouter.getInstance().inject(this)

        quanFragmentDetailBinding!!.goodsViewModel!!.liveDataGoodsDetail.observe(this , Observer { it->
            if(it!!.resultCode != ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            setBanner( it.resultData!! )
            setDetail(it.resultData!! )
        })

        detailAdapter = DetailAdapter(data)
        detailTopBinding = DataBindingUtil.inflate( layoutInflater , R.layout.layout_detail_top , null , false )
        detailTopBinding!!.goodsBean = null

        var top = detailTopBinding!!.root
        var detailItemPrice1 = top.findViewById<TextView>(R.id.detail_item_price1)
        detailItemPrice1.paintFlags = TextPaint.STRIKE_THRU_TEXT_FLAG

        var detailItemTitle = top.findViewById<TextView>(R.id.detail_item_title)
        detailItemTitle.setOnLongClickListener(this)

        var detailGet = top.findViewById<TextView>(R.id.detail_item_get)
        detailGet.setOnClickListener(this)

        detailAdapter!!.removeAllHeaderView()
        detailAdapter!!.addHeaderView(top)

        detail_recyclerView.adapter = detailAdapter


        quanFragmentDetailBinding!!.goodsViewModel!!.error.observe(this, Observer { it->
            if(TextUtils.isEmpty(it)){
                return@Observer
            }
            showToast(it!!)
         })

        quanFragmentDetailBinding!!.goodsViewModel!!.loading.observe(this, Observer { it->
            detail_progress.visibility = if(it==null|| !it) View.GONE else View.VISIBLE
        })

        quanFragmentDetailBinding!!.userViewModel!!.loading.observe(this, Observer { it->
            detail_progress.visibility = if(it==null|| !it) View.GONE else View.VISIBLE
        })

        quanFragmentDetailBinding!!.userViewModel!!.liveDataCollectResult.observe(this, Observer { it->
            if(it!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            detail_favorite_image.setImageResource(R.mipmap.favorite_red)
            var goodView= GoodView(context)
            goodView.setImage(R.mipmap.favorite_red)
            goodView.setDistance(100)
            goodView.setDuration(800)
            goodView.show(detail_favorite)

        })

        quanFragmentDetailBinding!!.userViewModel!!.liveDataCancelCollectResult.observe(this, Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            detail_favorite_image.setImageResource(R.mipmap.favorite_gray)
            var goodView= GoodView(context)
            goodView.setImage(R.mipmap.favorite_gray)
            goodView.setDistance(100)
            goodView.setDuration(800)
            goodView.show(detail_favorite)
        })

        quanFragmentDetailBinding!!.goodsViewModel!!.liveDataGoodsShareBean.observe(this, Observer { it->
            if(it!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            if(it.resultData==null || it.resultData!!.share==null) return@Observer

            getCoupon(it.resultData!!.share!!)
        })
    }

    override fun OnBannerClick(position: Int) {
    }

    override fun onLongClick(v: View?): Boolean {
        var clipManager = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var text = (v as TextView).text
        text = if(text.length>4) text.substring(4) else ""

        clipManager.primaryClip = ClipData.newPlainText ("text" , text)
        showToast("复制成功")
        return true
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                _mActivity.onBackPressed()
            }
            R.id.detail_share_lay,
            R.id.header_share->{
                share()
            }
            R.id.detail_collect_lay->{
                collect()
            }
            R.id.detail_lay_buy,
            R.id.detail_item_get->{//立即领券
                if(goodDetail==null) return
                var goodsSource = goodDetail!!.goodsSource
                quanFragmentDetailBinding!!.goodsViewModel!!.getShareInfo( goodsId , goodsSource)
            }
        }
    }

    /**
     *
     */
    private fun getCoupon( shareBean: GoodsShareBean){

        var isInstallPingDuoduo = AppUtil.checkInstallApp( context!! , Constants.PACKAGENAME_PINDUODUO )
        var url:String?=""
        if(isInstallPingDuoduo) {
            url = shareBean.mobileUrl
            if (TextUtils.isEmpty(url)) {
                url = shareBean.mobileShortUrl
            }
            //if(!TextUtils.isEmpty(url)){
                //var clipboardManager = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                //clipboardManager.primaryClip = ClipData.newUri(context!!.contentResolver , "uri", Uri.parse(url) )
                //clipboardManager.primaryClip = ClipData.newPlainText("text" , shareBean.share )

                //var launchIntent = context!!.packageManager.getLaunchIntentForPackage(Constants.PACKAGENAME_PINDUODUO)
                //startActivity(launchIntent)
                //return
            //}

            if (TextUtils.isEmpty(url)) {
                url = shareBean.url
            }
            if (TextUtils.isEmpty(url)) {
                url = shareBean.shortUrl
            }

        }else{
            if (TextUtils.isEmpty(url)) {
                url = shareBean.url
            }
            if (TextUtils.isEmpty(url)) {
                url = shareBean.shortUrl
            }
        }

        if(TextUtils.isEmpty(url)) {
            showToast("缺少信息，无法领取")
            return
        }

        var intent = Intent()
        intent.action=Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse( url )
        context!!.startActivity(intent)

    }

    private fun collect(){

        if(UserViewModel.liveDataMyResult.value ==null ){
            showToast("请先登录")
            return
        }
        if(UserViewModel.liveDataMyResult.value!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
            showToast( UserViewModel.liveDataMyResult.value!!.resultMsg)
            return
        }
        if(UserViewModel.liveDataMyResult.value!!.resultData==null){
            showToast("请先登录")
            return
        }

        if(quanFragmentDetailBinding!!.goodsViewModel!!.liveDataGoodsDetail.value==null) return
        if(quanFragmentDetailBinding!!.goodsViewModel!!.liveDataGoodsDetail.value!!.resultCode !=ApiResultCodeEnum.SUCCESS.code){
            return
        }

        if( quanFragmentDetailBinding!!.goodsViewModel!!.liveDataGoodsDetail.value!!.resultData!!.detail!!.collect ){
            quanFragmentDetailBinding!!.userViewModel!!.cancelCollect(goodsId)
        }else {
            quanFragmentDetailBinding!!.userViewModel!!.collect(goodsId , goodDetail!!.goodsSource)
        }
    }

    private fun share(){

        if(UserViewModel.liveDataMyResult.value ==null ){
            showToast("请先登录")
            return
        }
        if(UserViewModel.liveDataMyResult.value!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
            showToast( UserViewModel.liveDataMyResult.value!!.resultMsg)
            return
        }
        if(UserViewModel.liveDataMyResult.value!!.resultData==null){
            showToast("请先登录")
            return
        }

        //if(!UserViewModel.liveDataMyResult.value!!.resultData!!.data.) {
            //todo
        //    start(ShareTipFragment.newInstance("", ""))
        //}else {
            //start(ShareFragment.newInstance("",""))
        if(goodDetail==null){
            return
        }

//            if(quanFragmentDetailBinding==null || quanFragmentDetailBinding!!.goodsViewModel==null ) return
//            var goods = quanFragmentDetailBinding!!.goodsViewModel!!.liveDataGoodsDetail.value
//            if(goods ==null || goods.resultData== null ||goods.resultData!!.detail==null ) return
//            if(goods.resultCode != ApiResultCodeEnum.SUCCESS.code ) return

            var shareFragment=ARouter.getInstance().build(ARouterPath.QuanFragmentGoodsSharePath)
                .withObject("goods" , goodDetail )
                .navigation() as ShareFragment

            start(shareFragment)

        //}
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment GoodsDetailFragment.
         */
        @JvmStatic
        fun newInstance() =
            GoodsDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
