package com.jxd.android.gohomeapp.quanmodule.adapter

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jxd.android.gohomeapp.libcommon.bean.ItemTypeEnum
import com.jxd.android.gohomeapp.libcommon.bean.RecommandItem2
import com.jxd.android.gohomeapp.libcommon.bean.RecommandItem7
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

class RecommandDevider(var recommandAdapter: RecommandAdapter , context:Context)
    : Y_DividerItemDecoration(context){

    override fun getDivider(itemPosition: Int): Y_Divider? {
        var divider : Y_Divider= Y_DividerBuilder().create()
        var type = recommandAdapter.getItem(itemPosition)!!.itemType

        when(type ){
            ItemTypeEnum.ONE_COLLOMN_SIMPLE.type->{
                var paddingLeft = (recommandAdapter.getItem(itemPosition) as RecommandItem2).paddingLeft
                var paddingRight = (recommandAdapter.getItem(itemPosition) as RecommandItem2).paddingRight
                return initDivider(itemPosition , paddingLeft , paddingRight)
            }
            ItemTypeEnum.ONE_ROW_CAN_SCROLL_BANNER.type->{
                return initDivide2()
            }
            ItemTypeEnum.ONE_COLLOMN_GOODS.type->{
                var paddingLeft = (recommandAdapter.getItem(itemPosition) as RecommandItem7).paddingLeft
                var paddingRight = (recommandAdapter.getItem(itemPosition) as RecommandItem7).paddingRight
                return initDivide3(itemPosition , paddingLeft , paddingRight)
            }
        }
        return divider
    }

    private fun initDivide3(position: Int , paddingLeft :Int , paddingRight:Int):Y_Divider{

             return Y_DividerBuilder()
                    .setLeftSideLine(true,0xffffff,paddingLeft.toFloat() ,0f,0f)
                    .setRightSideLine(true,0xffffff,paddingRight.toFloat() ,0f,0f)
                    .create()
    }

    private fun initDivide2():Y_Divider{
         return Y_DividerBuilder()
                 .setLeftSideLine(true,0xffffff,8f,0f,0f)
                 .create()
    }

    private fun initDivider( position:Int , paddingLeft :Int , paddingRight:Int ):Y_Divider{
            return Y_DividerBuilder()
                    .setLeftSideLine(true, 0xfffff, paddingLeft.toFloat() , 0f, 0f)
                    //.setTopSideLine(true, 0xffffff, 8f, 0f, 0f)
                    .setRightSideLine(true,0xffffff,paddingRight.toFloat(),0f,0f)
                    .create()

    }
}



class ItemDevider2(var context: Context , var space:Float , var colorRes: Int  ):Y_DividerItemDecoration(context){
    override fun getDivider(itemPosition: Int): Y_Divider {
        var divider : Y_Divider= Y_DividerBuilder().create()
        if(itemPosition%2 ==0 ){
            return Y_DividerBuilder()
                .setLeftSideLine(true , ContextCompat.getColor( context , colorRes) , space , 0f ,0f )
                .setRightSideLine(true , ContextCompat.getColor(context, colorRes), space/2 ,0f,0f)
                .create()
        }else{
            return Y_DividerBuilder()
                .setLeftSideLine(true , ContextCompat.getColor( context , colorRes) , space/2 , 0f ,0f )
                .setRightSideLine(true , ContextCompat.getColor(context , colorRes) , space , 0f, 0f)
                .create()
        }

    }
}

class ItemDevider3(var context: Context , var space:Float , var colorRes:Int, var startPadding:Float ):Y_DividerItemDecoration(context){

    override fun getDivider(itemPosition: Int): Y_Divider {
        return Y_DividerBuilder()
            .setBottomSideLine(true , ContextCompat.getColor(context , colorRes) , space , startPadding , 0f)
            .create()
    }
}

class ItemDevider4(var context: Context , var space:Float , var colorRes:Int, var startPadding:Float ):Y_DividerItemDecoration(context){

    override fun getDivider(itemPosition: Int): Y_Divider {
        return Y_DividerBuilder()
            .setLeftSideLine(true , ContextCompat.getColor(context , colorRes) , space , startPadding , 0f)
            .create()
    }
}