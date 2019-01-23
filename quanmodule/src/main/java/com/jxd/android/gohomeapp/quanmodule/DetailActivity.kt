package com.jxd.android.gohomeapp.quanmodule

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.barlibrary.ImmersionBar
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


    override fun setStatusColor(){
        ImmersionBar.with(this).statusBarDarkFont(true).init()
    }

    override fun initView(){

        ARouter.getInstance().inject(this)

        quanActivityDetailBinding = DataBindingUtil.setContentView( this  , R.layout.quan_activity_detail)

        var goodsDetailFragment = ARouter.getInstance().build(ARouterPath.QuanFragmentGoodsDetailPath)
            .withString("goodsId" , goodsId).navigation() as GoodsDetailFragment
        loadRootFragment(R.id.detail_container , goodsDetailFragment )

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

        //showToast( "permissions" )
    }

    override fun OnBannerClick(position: Int) {
        showToast("position"+ position )
    }


    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
    }
}
