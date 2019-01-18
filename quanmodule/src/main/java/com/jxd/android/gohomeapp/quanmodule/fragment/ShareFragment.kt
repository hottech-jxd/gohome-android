package com.jxd.android.gohomeapp.quanmodule.fragment


import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ClipData
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.ClipboardManager
import android.text.TextPaint
import android.text.TextUtils
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
import com.jxd.android.gohomeapp.libcommon.bean.Constants
import com.jxd.android.gohomeapp.libcommon.bean.GoodsDetailBean
import com.jxd.android.gohomeapp.libcommon.bean.ShareBean
import com.jxd.android.gohomeapp.libcommon.util.AppUtil
import com.jxd.android.gohomeapp.libcommon.util.PermissionsUtils
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.QuanModule

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider4
import com.jxd.android.gohomeapp.quanmodule.adapter.SharePictureAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentShareBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloadQueueSet
import com.liulishuo.filedownloader.FileDownloader
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_goods_detail.*
import kotlinx.android.synthetic.main.quan_fragment_share.*
import kotlinx.android.synthetic.main.quan_fragment_share.view.*
import java.io.File
import java.util.*

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
    private var REQUEST_CODE_SHARE=3001

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
            if(it.resultData ==null || it.resultData!!.share==null ) return@Observer

            share_content.text = it.resultData!!.share!!.share
        })


        dataBinding!!.goodsViewModel!!.error.observe(this, Observer { it->
            if(TextUtils.isEmpty(it)) return@Observer

            showToast(it!!)
        })

        dataBinding!!.goodsViewModel!!.loading.observe(this, Observer { it->
            share_progress.visibility = if( it==null || !it ) View.GONE else View.VISIBLE
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

        dataBinding!!.goodsViewModel!!.getShareInfo( goodsDetailBean!!.goodsId )

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
            R.id.share_saveImage->{
                saveImage()
            }
            R.id.share_copyText->{
                val cm =  this.context!!.getSystemService (Context.CLIPBOARD_SERVICE) as ClipboardManager
                cm.text = share_content.text.toString()
                showToast("复制成功")
            }
            R.id.share_weChat->{
                //shareImages()

                shareImageByWechaSDK()
            }
            R.id.share_weComment->{
                shareImages()
            }
        }
    }

    private fun checkPermission():Boolean{
        if(activity==null) return false
        var list = PermissionsUtils.Builder(activity!!).addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .initPermission()

        return list.isEmpty()
    }

    /**
     * 将图片存到本地
     */
    private fun saveImage( needShare:Boolean=false) {

        if(!checkPermission()) return


        if(goodsDetailBean==null || goodsDetailBean!!.pictureUrls ==null || goodsDetailBean!!.pictureUrls!!.size<1){
            showToast("没有图像需要下载！")
            return
        }
        var dir = Constants.ImageDirPath + goodsDetailBean!!.goodsId+"/"
        //isShowProgress=true
        var downLoadQueueSet = FileDownloadQueueSet(object : FileDownloadListener() {
            override fun warn(task: BaseDownloadTask?) {
                //hideProgress()
                dataBinding!!.goodsViewModel!!.loading.postValue(false)
                //showToast("warn")
            }

            override fun completed(task: BaseDownloadTask?) {
                //hideProgress()
                dataBinding!!.goodsViewModel!!.loading.postValue(false )
                if ((task!!.tag as IdId).id == (task!!.tag as IdId).total) {
                    showToast("图片已经保存在"+dir)

                    if(needShare){
                        shareImages()
                    }
                }

            }

            override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                //hideProgress()
                dataBinding!!.goodsViewModel!!.loading.postValue(false)
            }

            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                //hideProgress()
                dataBinding!!.goodsViewModel!!.loading.postValue(false)
                showToast("error")
                e!!.printStackTrace()
            }

            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                //showProgress("")
                dataBinding!!.goodsViewModel!!.loading.postValue(true)
            }

            override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                //hideProgress()
                dataBinding!!.goodsViewModel!!.loading.postValue(false)
            }
        })
        var tasks = ArrayList<BaseDownloadTask>()

        var f = File(dir)
        f.delete()
        if (!f.exists()) {
            f.parentFile.mkdir()
        }

        for (i in 0 until goodsDetailBean!!.pictureUrls!!.size) {

            var name = AppUtil.getFileName( goodsDetailBean!!.pictureUrls!![i] ) //(i + 1).toString() + ".jpg"
            var path = dir + name
            var idId = IdId(i, goodsDetailBean!!.pictureUrls!!.size - 1)

            tasks.add(FileDownloader.getImpl().create(goodsDetailBean!!.pictureUrls!![i]).setTag(i + 1).setPath(path).setTag(idId))
        }
        downLoadQueueSet.disableCallbackProgressTimes()
        downLoadQueueSet.setAutoRetryTimes(1)
        downLoadQueueSet.downloadSequentially(tasks)//串行下载
        downLoadQueueSet.start()
        //showProgress("")
        dataBinding!!.goodsViewModel!!.loading.postValue(true)
    }

    private fun isDownPicture( dirPath : String ):Boolean{
        //val imageDirectoryPath = Constants.ImageDirPath  + quan.dataId+"/"
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val imageDirectory = File(dirPath)

        val fileList = imageDirectory.list()
        return fileList.isEmpty()
    }

    private fun shareImages() {
        var dirPath = Constants.ImageDirPath + goodsDetailBean!!.goodsId+"/"
        if(isDownPicture(dirPath)) saveImage( true)

        //val cm = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var shareText = share_content.text.toString().trim()

        var intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/*"
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getLocalImages( Constants.ImageDirPath  + goodsDetailBean!!.goodsId +"/"  ))
        intent.putExtra(Intent.EXTRA_SUBJECT, goodsDetailBean!!.name )
        intent.putExtra(Intent.EXTRA_TEXT, shareText )
        intent.putExtra(Intent.EXTRA_TITLE, goodsDetailBean!!.name)
        //intent.putExtra(Intent., quan.ShareTitle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        var shareIntent = Intent.createChooser(intent, "分享")
        //shareIntent.putExtra(Constants.INTENT_GOODSID , quan.dataId )
        //currentShareDataId=quan.dataId
        startActivityForResult( shareIntent , REQUEST_CODE_SHARE )
    }

    private fun shareImageByWechaSDK(){

        var dirPath = Constants.ImageDirPath + goodsDetailBean!!.goodsId+"/"
        if(isDownPicture(dirPath)) saveImage( true)


        var webPage = WXWebpageObject()
        webPage.webpageUrl = "http://wwww.baidu.com"

        var msg= WXMediaMessage(webPage)
        msg.title = "testtest"
        msg.description="testtestest"
        var bitmapFolder = Constants.ImageDirPath  + goodsDetailBean!!.goodsId +"/"
        val imageDirectory = File(bitmapFolder)

        val filePath = imageDirectory.list()[0]
        var bitmap = AppUtil.fileToByte(filePath)

        msg.thumbData = bitmap

        var req = SendMessageToWX.Req()
        req.transaction =  System.currentTimeMillis().toString()
        req.message =msg
        req.scene =  SendMessageToWX.Req.WXSceneSession
        //req.userOpenId = getOpenId()

        //调用api接口，发送数据到微信
        QuanModule.WechatApi!!.sendReq(req)
    }

    /**
     * 设置需要分享的照片放入Uri类型的集合里
     */
    private fun getLocalImages( dirPath:String   ): ArrayList<Uri> {
        val myList = ArrayList<Uri>()

        val imageDirectoryPath = dirPath //Constants.ImageDirPath  + dataId+"/"
        val dir = File(imageDirectoryPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val imageDirectory = File(imageDirectoryPath)

        val fileList = imageDirectory.list()

        if (fileList.isNotEmpty()) {

            var count = if (fileList.size > 9) 9 else fileList.size

            for (i in 0 until count) {

                try {

                    val values = ContentValues(7)

                    values.put(MediaStore.Images.Media.TITLE, fileList[i])

                    values.put(MediaStore.Images.Media.DISPLAY_NAME, fileList[i])

                    values.put(MediaStore.Images.Media.DATE_TAKEN, Date().time)

                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

                    values.put(MediaStore.Images.ImageColumns.BUCKET_ID, imageDirectoryPath.hashCode())

                    values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, fileList[i])

                    values.put("_data", imageDirectoryPath + fileList[i])

                    //val contentResolver = context!!.contentResolver

                    val uri = getUriByFile(context!!, imageDirectoryPath+ fileList[i]) // Uri.fromFile( File(imageDirectoryPath + fileList[i])) //contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)

                    myList.add(uri!!)

                } catch (e: Exception) {

                    e.printStackTrace()

                }

            }

        }


        return myList
    }


    fun getUriByFile(context: Context, path: String): Uri? {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            Uri.fromFile(File(path))
        } else {
            //return FileProvider.getUriForFile(context , context.getPackageName() + ".provider" , new File(path));
            getImageContentUri(context, File(path))
        }
    }


    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context context
     * @param imageFile imageFile
     * @return content Uri
     */
    fun getImageContentUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID), MediaStore.Images.Media.DATA + "=? ",
            arrayOf(filePath), null
        )
        var uri: Uri? = null

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val baseUri = Uri.parse("content://media/external/images/media")
                uri = Uri.withAppendedPath(baseUri, "" + id)
            }

            cursor.close()
        }

        if (uri == null) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DATA, filePath)
            uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }

        return uri
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if( requestCode==REQUEST_CODE_SHARE  ){
            //因为无法判断是否分享成功了，所以就都认为成功
            //toast("分享回调了.....")
            //var dataId = data!!.getLongExtra(Constants.INTENT_GOODSID,0L)

            //iPresenter.shareSuccess(currentShareDataId)
            showToast("分享成功")

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



data class IdId(var id :Int , var total:Int)