package com.jxd.android.gohomeapp.quanmodule.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
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
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener


class RecommandAdapter(data : ArrayList<MultiItemEntity>)
    : BaseMultiItemQuickAdapter<MultiItemEntity , BaseViewHolder>(data) , OnBannerListener {

    init {
        addItemType(ItemTypeEnum.BANNER.type , R.layout.layout_recommand_item_1 )
        addItemType(ItemTypeEnum.ONE_COLLOMN_SIMPLE.type , R.layout.layout_recommand_item_2)
        addItemType(ItemTypeEnum.ONE_ROW_COUNTDOWN.type , R.layout.layout_recommand_item_3)
        addItemType(ItemTypeEnum.ONE_ROW_GOODS.type , R.layout.layout_recommand_item_4)
        addItemType(ItemTypeEnum.ONE_ROW_TITLE.type , R.layout.layout_recommand_item_5)
        addItemType(ItemTypeEnum.ONE_ROW_CAN_SCROLL_BANNER.type,R.layout.layout_recommand_item_6)
        addItemType(ItemTypeEnum.ONE_COLLOMN_GOODS.type,R.layout.layout_recommand_item_7)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when(helper!!.itemViewType){
            ItemTypeEnum.BANNER.type->{
                set_1(helper , item)
            }
            ItemTypeEnum.ONE_COLLOMN_SIMPLE.type->{
                set_2(helper,item)
            }
            ItemTypeEnum.ONE_ROW_COUNTDOWN.type->{
                set_3(helper,item)
            }
            ItemTypeEnum.ONE_ROW_GOODS.type->{
                set_4(helper,item)
            }
            ItemTypeEnum.ONE_ROW_TITLE.type->{
                set_5(helper,item)
            }
            ItemTypeEnum.ONE_ROW_CAN_SCROLL_BANNER.type->{
                set_6(helper,item)
            }
            ItemTypeEnum.ONE_COLLOMN_GOODS.type->{
                set_7(helper,item)
            }
        }
    }

    private fun set_1(helper: BaseViewHolder?,item: MultiItemEntity? ){
        var urls = (item as RecommandItem1).data
        var banner = helper!!.getView<Banner>(R.id.recommand_banner)



        banner.setImageLoader(FrescoImageLoader(banner , DensityUtils.getScreenWidth(mContext) ))
        banner.setImages(urls)
        //banner.setOnBannerListener(this)
        banner.setOnBannerListener { position -> {
            var url = urls[position].toString()
            this.setOnItemClick( helper.getView(R.id.recommand_banner)  , helper.adapterPosition )
        } }
        banner.start()

        //helper.addOnClickListener(R.id.recommand_banner)
    }

    private fun set_2(helper: BaseViewHolder?,item: MultiItemEntity?){
        var image = helper!!.getView<SimpleDraweeView>(R.id.recommand_image_1)
        image.setImageURI("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png")
    }

    private fun set_3(helper: BaseViewHolder?,item: MultiItemEntity?){
        var countDown = helper!!.getView<CountdownView>(R.id.recommand_countdown)
        countDown.start(15454545)
    }

    private fun set_4(helper: BaseViewHolder?,item: MultiItemEntity?){
        var bean = item as RecommandItem4
        helper!!.getView<SimpleDraweeView>(R.id.good_item_image)
                .setImageURI(bean.data.imgSrc)
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

    private fun set_7(helper: BaseViewHolder?,item: MultiItemEntity?){
        var bean = item as RecommandItem7
        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI(bean.data.imgSrc)
    }


    override fun OnBannerClick(position: Int) {
        var test = position.toString()

    }
}