package com.jxd.android.gohomeapp.quanmodule.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import cn.iwgang.countdownview.CountdownView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.facebook.drawee.view.SimpleDraweeView
import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.libcommon.util.DensityUtils
import com.jxd.android.gohomeapp.quanmodule.FrescoImageLoader
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.R.id.good_item_title
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener


class RecommandAdapter(data : ArrayList<MultiItemEntity>)
    : BaseMultiItemQuickAdapter<MultiItemEntity , BaseViewHolder>(data){

    var onBannerItemClickListener:BannerItemClickListener?=null


    init {
        addItemType(ItemTypeEnum.BANNER.type , R.layout.layout_recommand_item_1 )
        addItemType(ItemTypeEnum.ONE_COLLOMN_SIMPLE.type , R.layout.layout_recommand_item_2)
        addItemType(ItemTypeEnum.ONE_ROW_COUNTDOWN.type , R.layout.layout_recommand_item_3)
        addItemType(ItemTypeEnum.ONE_ROW_GOODS.type , R.layout.layout_goods_item)
        addItemType(ItemTypeEnum.ONE_ROW_TITLE.type , R.layout.layout_recommand_item_5)
        addItemType(ItemTypeEnum.ONE_ROW_CAN_SCROLL_BANNER.type,R.layout.layout_recommand_item_6)
        addItemType(ItemTypeEnum.ONE_COLLOMN_GOODS.type,R.layout.layout_recommand_item_7)
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
                setGoodsOfRow(helper,item)
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

        for(item in data){
            picList.add(item.pictureUrl!!)
        }


        var banner = helper!!.getView<Banner>(R.id.recommand_banner)

        banner.setImageLoader(FrescoImageLoader(banner , DensityUtils.getScreenWidth(mContext) ))
        banner.setImages(picList)
        banner.setOnBannerListener(BannerListener( helper.adapterPosition , banner , onBannerItemClickListener ))
        banner.setTag(item)
        banner.start()

        helper.addOnClickListener(R.id.recommand_banner)

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

        helper!!.setText(R.id.good_item_title , bean.name)

        helper.setText(R.id.good_item_price , bean.price)
        helper.getView<TextView>(R.id.good_item_price).paintFlags=TextPaint.STRIKE_THRU_TEXT_FLAG

        helper.setText(R.id.good_item_coupon , bean.couponPrice+"元券")

        helper.setText(R.id.good_item_count, "销量"+bean.saleAmount+"件")

        helper.setText(R.id.good_item_final_price, "￥"+ bean.finalPrice)

        helper.setText(R.id.good_item_reword, bean.reward)

        helper.addOnClickListener(R.id.good_item_container)
    }

    private fun set_5(helper: BaseViewHolder?,item: MultiItemEntity?){

        var bean = item as RecommandItem5
        helper!!.setText(R.id.recommand_banner_5 , bean.title )
    }
    private fun set_6(helper: BaseViewHolder?,item: MultiItemEntity?){
        var horizontalBanner = helper!!.getView<RecyclerView>(R.id.recommand_banner_6)
        horizontalBanner.layoutManager= LinearLayoutManager( mContext , LinearLayout.HORIZONTAL ,false )

        var horizontalBannerUrl = ArrayList<String>()
        horizontalBannerUrl.add("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png")
        horizontalBannerUrl.add("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png")
        horizontalBannerUrl.add("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png")
        var horizontalBannerAdapter = HorizontalBannerAdapter( horizontalBannerUrl)
        horizontalBanner.adapter=horizontalBannerAdapter
    }

    private fun setGoodsOfColumn(helper: BaseViewHolder?,item: MultiItemEntity?){
        var bean = (item as RecommandItem7).data

        var picUrl =   bean.pictureUrl


        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI(picUrl)

        helper.setText(R.id.good_item_1_title, bean.name)
        helper.setText(R.id.good_item_1_price,bean.price)
        helper.getView<TextView>(R.id.good_item_1_price).paintFlags=TextPaint.STRIKE_THRU_TEXT_FLAG
        helper.setText(R.id.good_item_1_count, "销售量"+bean.saleAmount+"件")
        helper.setText(R.id.good_item_1_final_price, "￥"+ bean.finalPrice)
        helper.setText(R.id.good_item_1_coupon, "卷￥"+bean.couponPrice+"元")
        helper.setText(R.id.good_item_1_reword, bean.reward)

        helper.addOnClickListener(R.id.good_item_1_container )
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