package com.jxd.android.gohomeapp.quanmodule.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.bean.ShareBean
import com.jxd.android.gohomeapp.libcommon.util.showToast

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider4
import com.jxd.android.gohomeapp.quanmodule.adapter.SharePictureAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentShareBinding
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_share.*

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
class ShareFragment : BaseBackFragment() , BaseQuickAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var dataBinding:QuanFragmentShareBinding?=null
    var sharePictureAdapter : SharePictureAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.quan_fragment_share, container, false)

        dataBinding = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_share , container , false )

        return dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        header_title.text="分享赚"

        if(sharePictureAdapter==null){
            sharePictureAdapter= SharePictureAdapter(ArrayList())


            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))
            sharePictureAdapter!!.addData(ShareBean("https://img2018.cnblogs.com/news_topic/20181019115720199-66310872.png",false))

        }
        share_images.layoutManager=LinearLayoutManager(context , RecyclerView.HORIZONTAL, false)
        share_images.adapter = sharePictureAdapter
        share_images.addItemDecoration(ItemDevider4(context!! , 20f , R.color.white , 0f))
        sharePictureAdapter!!.onItemClickListener=this
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)



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
