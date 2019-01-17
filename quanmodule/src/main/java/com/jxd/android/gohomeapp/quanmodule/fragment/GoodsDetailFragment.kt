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
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.util.DensityUtils
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.FrescoImageLoader

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.R.id.goodsdetail_banner
import com.jxd.android.gohomeapp.quanmodule.R.mipmap.quan
import com.jxd.android.gohomeapp.quanmodule.adapter.DetailAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanActivityDetailBinding
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentGoodsDetailBinding
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentGoodsDetailBindingImpl
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import android.arch.lifecycle.Observer
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextPaint
import android.text.TextUtils
import android.widget.TextView
import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.quanmodule.R.mipmap.share
import com.jxd.android.gohomeapp.quanmodule.databinding.LayoutDetailTopBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import com.wx.goodview.GoodView
import kotlinx.android.synthetic.main.layout_detail_top.*
import kotlinx.android.synthetic.main.quan_activity_detail.*
import kotlinx.android.synthetic.main.quan_fragment_goods_detail.*
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    var quanFragmentDetailBinding : QuanFragmentGoodsDetailBinding?=null
    var detailTopBinding:LayoutDetailTopBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

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

//        var images = ArrayList<String>()
//        images.add("http://t04img.yangkeduo.com/images/2018-05-26/3308bf00afb37922ceef70b9991e0dfd.jpeg")
//        images.add("http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg")
//        images.add("http://t08img.yangkeduo.com/images/2018-04-27/facf8f2067f128b3b2af353411c3ffcd.jpeg")



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
        if(goodsDetailModel!!.detail!!.detail==null) return



        if(detailTopBinding!=null){
        detailTopBinding!!.goodsBean = goodsDetailModel.detail
        }


        data.clear()

        data.add( PictureBean( goodsDetailModel.detail!!.detail!! ) )

//        for(i in 0 .. 10){
//            data.add(DetailBean(i,0,"http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg"))
//        }
//        data.add(DetailBean(4,0,"http://t00img.yangkeduo.com/t09img/images/2018-05-28/4ac0853e1a7a898315f5155bdb733dff.jpeg"))
//
//        data.add(DetailBean(4,0,"http://t04img.yangkeduo.com/images/2018-05-26/3308bf00afb37922ceef70b9991e0dfd.jpeg"))
//
//        data.add(DetailBean(4,0,"http://t08img.yangkeduo.com/images/2018-04-27/facf8f2067f128b3b2af353411c3ffcd.jpeg"))


        //detail_recyclerView.layoutManager=LinearLayoutManager(this)
        detailAdapter!!.setNewData(data)//= DetailAdapter(data)


        detail_share_reword.text = "￥${goodsDetailModel.detail!!.reward}"
        detail_sheng.text ="￥${goodsDetailModel.detail!!.reward}"
    }


    override fun initView(){

        ARouter.getInstance().inject(this)

//        var goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)
//        quanActivityDetailBinding = DataBindingUtil.setContentView( this  , R.layout.quan_fragment_goods_detail)
//        quanActivityDetailBinding!!.goodsViewModel = goodsViewModel
//        quanActivityDetailBinding!!.setLifecycleOwner(this)

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



        //var top = LayoutInflater.from(this.context ).inflate(R.layout.layout_detail_top, null)

        var top = detailTopBinding!!.root

        var detail_item_price1 = top.findViewById<TextView>(R.id.detail_item_price1)
        detail_item_price1.paintFlags = TextPaint.STRIKE_THRU_TEXT_FLAG

        var detail_item_title = top.findViewById<TextView>(R.id.detail_item_title)
        detail_item_title.setOnLongClickListener(this)

        detailAdapter!!.removeAllHeaderView()
        detailAdapter!!.addHeaderView(top)

        detail_recyclerView.adapter = detailAdapter


        quanFragmentDetailBinding!!.goodsViewModel!!.error.observe(this, Observer { it->
            if(TextUtils.isEmpty(it)){
                return@Observer
            }
            showToast(it!!)
         })


        quanFragmentDetailBinding!!.userViewModel!!.liveDataCollectResult.observe(this, Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
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
    }

    override fun OnBannerClick(position: Int) {
        showToast("position"+ position )
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
            R.id.detail_lay_buy->{
                //start(ShareFragment.newInstance("",""))
                showToast("todo")
            }
            R.id.detail_collect_lay->{
                collect()
            }
        }
    }

    fun collect(){
        quanFragmentDetailBinding!!.userViewModel!!.collect(goodsId)
    }

    fun share(){

        if(UserViewModel.liveDataUserInfo.value ==null ){
            showToast("请先登录")
            return
        }
        if(UserViewModel.liveDataUserInfo.value!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
            showToast( UserViewModel.liveDataUserInfo.value!!.resultMsg)
            return
        }
        if(UserViewModel.liveDataUserInfo.value!!.resultData==null){
            showToast("请先登录")
            return
        }

        if(!UserViewModel.liveDataUserInfo.value!!.resultData!!.unlocked) {
            start(ShareTipFragment.newInstance("", ""))
        }else {
            //start(ShareFragment.newInstance("",""))

            if(quanFragmentDetailBinding==null || quanFragmentDetailBinding!!.goodsViewModel==null ) return
            var goods = quanFragmentDetailBinding!!.goodsViewModel!!.liveDataGoodsDetail.value
            if(goods ==null || goods.resultData== null  ) return
            if(goods.resultCode != ApiResultCodeEnum.SUCCESS.code ) return

            var shareFragment=ARouter.getInstance().build(ARouterPath.QuanFragmentGoodsSharePath)
                .withObject("goods" , goods.resultData )
                .navigation() as ShareFragment

            start(shareFragment)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GoodsDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GoodsDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
