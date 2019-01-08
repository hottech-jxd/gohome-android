package com.jxd.android.gohomeapp.quanmodule.fragment


import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment

import com.jxd.android.gohomeapp.quanmodule.R
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.quan_fragment_tutorials.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TutorialsFragment.newInstance] factory method to
 * create an instance of this fragment.
 * 新手教程
 */
class TutorialsFragment : BaseBackFragment() , View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    internal var orientationUtils: OrientationUtils? = null

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
        return inflater.inflate(R.layout.quan_fragment_tutorials, container, false)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initVedioPlayer()
    }

    private fun initVedioPlayer(){
        var source1 = "https://res.exexm.com/cw_145225549855002"
        source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
        tutorials_videoplay.setUp(source1, true, "sssssss")



        //增加封面
        //val imageView = ImageView(this)
        //imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //imageView.setImageResource(R.mipmap.xxx1)
        //videoPlayer.setThumbImageView(imageView)
        //增加title
        //tutorials_videoplay.getTitleTextView().setVisibility(View.VISIBLE)
        //设置返回键
        //tutorials_videoplay.getBackButton().setVisibility(View.VISIBLE)
        //设置旋转
        //orientationUtils = OrientationUtils(this.activity , tutorials_videoplay)
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        //tutorials_videoplay.getFullscreenButton().setOnClickListener(View.OnClickListener { orientationUtils!!.resolveByClick() })
        //是否可以滑动调整
        tutorials_videoplay.setIsTouchWiget(true)
        //设置返回按键功能
        //tutorials_videoplay.backButton.setOnClickListener(View.OnClickListener { onBackPressed() })
        tutorials_videoplay.startPlayLogic()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        GSYVideoManager.onResume()
    }

    override fun onPause() {
        super.onPause()

        GSYVideoManager.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()


    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override fun onBackPressedSupport(): Boolean {

        return if (GSYVideoManager.backFromWindowFull(activity)) {
            true
        } else super.onBackPressedSupport()

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
         * @return A new instance of fragment TutorialsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TutorialsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
