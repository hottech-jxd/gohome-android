package com.jxd.android.gohomeapp.quanmodule.bean

import java.io.Serializable

/**
 * 商品分类
 */
data class Category (var categoryId  : String?="" ,var name  :String?="" ,var goodsSource:Int =0 ){}

data class CategoryModel( var list :ArrayList<Category>?)