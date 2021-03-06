package com.jxd.android.gohomeapp.quanmodule.adapter


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ImageSpan
import android.widget.LinearLayout
import android.widget.TextView
import cn.iwgang.countdownview.CountdownView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.facebook.drawee.view.SimpleDraweeView

import com.jxd.android.gohomeapp.quanmodule.FrescoImageLoader
import com.jxd.android.gohomeapp.quanmodule.R
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jxd.android.gohomeapp.quanmodule.base.ARouterPath
import com.jxd.android.gohomeapp.quanmodule.bean.*
import com.jxd.android.gohomeapp.quanmodule.util.DensityUtils
import kotlinx.android.synthetic.main.layout_goods_coupon_item.*
import kotlinx.android.synthetic.main.layout_goods_coupon_item.view.*


class RecommandAdapter(data : ArrayList<MultiItemEntity>)
    : BaseMultiItemQuickAdapter<MultiItemEntity , BaseViewHolder>(data)
    , BaseQuickAdapter.OnItemClickListener{

    var onBannerItemClickListener:BannerItemClickListener?=null


    init {
        addItemType(ItemTypeEnum.BANNER.type , R.layout.layout_recommand_item_1 )
        addItemType(ItemTypeEnum.ONE_COLLOMN_SIMPLE.type , R.layout.layout_recommand_item_2)
        addItemType(ItemTypeEnum.ONE_ROW_COUNTDOWN.type , R.layout.layout_recommand_item_3)
        //addItemType(ItemTypeEnum.ONE_ROW_GOODS.type , R.layout.layout_goods_item)
        addItemType(ItemTypeEnum.ONE_ROW_GOODS.type , R.layout.layout_goods_coupon_item)
        addItemType(ItemTypeEnum.ONE_ROW_TITLE.type , R.layout.layout_recommand_item_5)
        addItemType(ItemTypeEnum.ONE_ROW_CAN_SCROLL_BANNER.type,R.layout.layout_recommand_item_6)
        addItemType(ItemTypeEnum.ONE_COLLOMN_GOODS.type,R.layout.layout_goods_item_1 )
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when(helper!!.itemViewType){
            ItemTypeEnum.BANNER.type->{
                setBanner(helper , item)
            }
            ItemTypeEnum.ONE_COLLOMN_SIMPLE.type->{
                set_2(helper,item)
            }
            ItemTypeEnum.ONE_ROW_COUNTDOWN.type->{
                setCountdown(helper,item)
            }
            ItemTypeEnum.ONE_ROW_GOODS.type->{
                //setGoodsOfRow(helper,item)
                setGoodsCouponOfRow(helper,item)
            }
            ItemTypeEnum.ONE_ROW_TITLE.type->{
                set_5(helper,item)
            }
            ItemTypeEnum.ONE_ROW_CAN_SCROLL_BANNER.type->{
                set_6(helper,item)
            }
            ItemTypeEnum.ONE_COLLOMN_GOODS.type->{
                setGoodsOfColumn(helper,item)
            }
        }
    }

    private fun setBanner(helper: BaseViewHolder?,item: MultiItemEntity? ){
        var data = (item as RecommandItem1).data

        var picList = ArrayList<String>()

        for( child in data){
            picList.add(child.pictureUrl!!)
        }


        var banner = helper!!.getView<Banner>(R.id.recommand_banner)

        banner.setImageLoader(FrescoImageLoader(banner , DensityUtils.getScreenWidth(mContext) ))
        banner.setImages(picList)
        banner.setOnBannerListener(BannerListener( helper.adapterPosition , banner , onBannerItemClickListener ))
        banner.setTag(item)
        banner.start()

        //helper.addOnClickListener(R.id.recommand_banner)

    }

    private fun set_2(helper: BaseViewHolder?,item: MultiItemEntity?){
        var data = item as RecommandItem2
        var imageUrl = data.data.pictureUrl
        var image = helper!!.getView<SimpleDraweeView>(R.id.recommand_image_1)
        image.setImageURI( imageUrl )

        helper.addOnClickListener(R.id.recommand_image_1)
    }

    private fun setCountdown(helper: BaseViewHolder?,item: MultiItemEntity?){
        var countDown = helper!!.getView<CountdownView>(R.id.recommand_countdown)

        var time = (item as RecommandItem3).data

        countDown.start( time )
    }

    private fun setGoodsOfRow(helper: BaseViewHolder?,item: MultiItemEntity?){
        var bean = (item as RecommandItem4).data

        var picUrl = bean.pictureUrl

        helper!!.getView<SimpleDraweeView>(R.id.good_item_image).setImageURI(picUrl)

        var spannableString = SpannableString("^&&^ "+bean.name)
        //var logoDraw = ContextCompat.getDrawable( mContext , R.mipmap.pinduoduo)
        //logoDraw!!.setBounds(0,0,logoDraw.minimumWidth,logoDraw.minimumHeight)
        var imageSpan = CenteredImageSpan (mContext, R.mipmap.pinduoduo) //ImageSpan(logoDraw)
        spannableString.setSpan(imageSpan , 0,4, ImageSpan.ALIGN_BASELINE)

        helper.setText(R.id.good_item_title , spannableString )

        helper.setText(R.id.good_item_price , "￥"+bean.price)
        helper.getView<TextView>(R.id.good_item_price).paintFlags=TextPaint.STRIKE_THRU_TEXT_FLAG

        helper.setText(R.id.good_item_coupon , bean.couponPrice+"元券")

        helper.setText(R.id.good_item_count, "销量"+bean.saleAmount+"件")

        helper.setText(R.id.good_item_final_price, bean.finalPrice)

        helper.setText(R.id.good_item_reword, bean.reward)

        helper.addOnClickListener(R.id.good_item_container)

        helper.addOnClickListener(R.id.good_item_favorite)
    }

    private fun setGoodsCouponOfRow(helper:BaseViewHolder?, item:MultiItemEntity?){
        var bean = (item as RecommandItem4).data
        var picUrl = bean.pictureUrl

        helper!!.getView<SimpleDraweeView>(R.id.goods_coupon_item_pic).setImageURI(picUrl)
        helper.setText(R.id.goods_coupon_item_couponPrice , bean.couponPrice+"元")
        helper.setText(R.id.goods_coupon_item_goodname , bean.name)
        //helper.setText(R.id.goods_coupon_item_)
        helper.addOnClickListener(R.id.goods_coupon_item_go)

    }

    private fun set_5(helper: BaseViewHolder?,item: MultiItemEntity?){

        var bean = item as RecommandItem5
        helper!!.setText(R.id.recommand_banner_5 , bean.title )
    }
    private fun set_6(helper: BaseViewHolder?,item: MultiItemEntity?){
        var horizontalBanner = helper!!.getView<RecyclerView>(R.id.recommand_banner_6)
        horizontalBanner.layoutManager= LinearLayoutManager( mContext , LinearLayout.HORIZONTAL ,false )

        var goodsList = (item as RecommandItem6).data
        var horizontalBannerAdapter = HorizontalBannerAdapter( goodsList )
        horizontalBanner.adapter=horizontalBannerAdapter
        horizontalBannerAdapter.onItemClickListener = this
    }

    private fun setGoodsOfColumn(helper: BaseViewHolder?,item: MultiItemEntity?){
        var bean = (item as RecommandItem7).data

        var picUrl =   bean.pictureUrl


        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI(picUrl)


        var spannableString = SpannableString("^&&^ "+bean.name)
        //var logoDraw = ContextCompat.getDrawable( mContext , R.mipmap.pinduoduo)
        //logoDraw!!.setBounds(0,0 ,logoDraw.minimumWidth,logoDraw.minimumHeight+2)
        var imageSpan = CenteredImageSpan(mContext , R.mipmap.pinduoduo) //ImageSpan(logoDraw)
        spannableString.setSpan(imageSpan , 0,4, ImageSpan.ALIGN_BASELINE )

        helper.setText(R.id.good_item_1_title, spannableString )
        helper.setText(R.id.good_item_1_price,bean.price)
        helper.getView<TextView>(R.id.good_item_1_price).paintFlags=TextPaint.STRIKE_THRU_TEXT_FLAG
        helper.setText(R.id.good_item_1_count, "销售"+bean.saleAmount+"件")
        helper.setText(R.id.good_item_1_final_price, bean.finalPrice)
        helper.setText(R.id.good_item_1_coupon, "券￥"+bean.couponPrice+"元")
        helper.setText(R.id.good_item_1_reword, bean.reward)

        helper.addOnClickListener(R.id.good_item_1_container )
        helper.addOnClickListener(R.id.good_item_1_favorite)
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var goodsId = (adapter!!.getItem(position) as GoodBean).goodsId
        ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath)
            .withString("goodsId", goodsId).navigation()
    }
}


class BannerListener(var position: Int , var banner : Banner, var bannerItemClickListener: BannerItemClickListener? ): OnBannerListener{

    override fun OnBannerClick( bannerIndex: Int ) {
        if(bannerItemClickListener==null) return
        bannerItemClickListener!!.onBannerItemClicked(position , bannerIndex)
    }
}

interface BannerItemClickListener{
    fun onBannerItemClicked( position: Int , bannerIndex :Int )
}

