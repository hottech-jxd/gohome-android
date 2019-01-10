package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.bean.GoodsDetailBean
import com.jxd.android.gohomeapp.libcommon.bean.ShareBean
import com.jxd.android.gohomeapp.libcommon.util.showToast

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider4
import com.jxd.android.gohomeapp.quanmodule.adapter.SharePictureAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentShareBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_share.*
import kotlinx.android.synthetic.main.quan_fragment_share.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShareFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path = ARouterPath.QuanFragmentGoodsSharePath)
class ShareFragment : BaseBackFragment() , BaseQuickAdapter.OnItemClickListener , View.OnClickListener {

    private var param1: String? = null
    private var param2: String? = null
    var dataBinding:QuanFragmentShareBinding?=null
    var sharePictureAdapter : SharePictureAdapter?=null

    @Autowired(name="goods") @JvmField var goodsDetailBean :GoodsDetailBean?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        ARouter.getInstance().inject(this)

        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        dataBinding = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_share , container , false )
        dataBinding!!.goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)
        dataBinding!!.goodsBean = goodsDetailBean
        dataBinding!!.clickHandler=this
        return dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        header_title.text="分享赚"
        share_price.paintFlags = TextPaint.STRIKE_THRU_TEXT_FLAG

        if(sharePictureAdapter==null){
            sharePictureAdapter= SharePictureAdapter(ArrayList())



//            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
//            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
//            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
//            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
//            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
//            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
//            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))

        }



        share_images.layoutManager=LinearLayoutManager(context , RecyclerView.HORIZONTAL, false)
        share_images.adapter = sharePictureAdapter
        share_images.addItemDecoration(ItemDevider4(context!! , 20f , R.color.white , 0f))
        sharePictureAdapter!!.onItemClickListener=this


        dataBinding!!.goodsViewModel!!.liveDataGoodsShareBean.observe(this, Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            if(it!!.share==null) return@Observer

            share_content.text = it.share!!.share
        })
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        if (goodsDetailBean == null) return



        if( goodsDetailBean!!.pictureUrls == null) return

        sharePictureAdapter!!.setNewData(ArrayList())
        for (pic in goodsDetailBean!!.pictureUrls!!) {
            var bean = ShareBean(pic, false)
            sharePictureAdapter!!.addData(bean)
        }

        initShareMessage()

    }

    private fun initShareMessage(){
        var messages = ArrayList<String>()
        messages.add("asdfsafassfa")
        messages.add("232323")
        messages.add("打发斯蒂芬爱的发声发顺丰阿法士大夫撒飞洒发啊所发生的")
        messages.add("sdfs3w423sfs2342342342342423srsfsf")



        share_scrollTextInfo.setResource(messages)


        //提供四个方向动画；默认从下往上
        share_scrollTextInfo.setAnimationTop2Bottom()
        //share_scrollTextInfo.setAnimationBottom2Top()
        //share_scrollTextInfo.setAnimationLeft2Right()
        //share_scrollTextInfo.setAnimationRight2Left()

        share_scrollTextInfo.startRolling()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        share_scrollTextInfo.destroySwitcher()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        var data = sharePictureAdapter!!.data

        for(i in 0 until  data.size ){
            if( i==position){
                data[i].check = !data[i].check
                sharePictureAdapter!!.notifyItemChanged(position)
            }
        }



    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                _mActivity.onBackPressed()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShareFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShareFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
