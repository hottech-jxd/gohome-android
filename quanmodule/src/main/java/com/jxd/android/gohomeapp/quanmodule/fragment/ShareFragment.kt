package com.jxd.android.gohomeapp.quanmodule.fragment


import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.*
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
import com.jxd.android.gohomeapp.libcommon.bean.*
import com.jxd.android.gohomeapp.libcommon.util.AppUtil
import com.jxd.android.gohomeapp.libcommon.util.PermissionsUtils
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.QuanModule

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider4
import com.jxd.android.gohomeapp.quanmodule.adapter.SharePictureAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentShareBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloadQueueSet
import com.liulishuo.filedownloader.FileDownloader
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.wxop.stat.event.i
import kotlinx.android.synthetic.main.layout_me_header.*
import kotlinx.android.synthetic.main.quan_layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_goods_detail.*
import kotlinx.android.synthetic.main.quan_fragment_share.*
import kotlinx.android.synthetic.main.quan_fragment_share.view.*
import java.io.File
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ShareFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path = ARouterPath.QuanFragmentGoodsSharePath)
class ShareFragment : BaseBackFragment() , BaseQuickAdapter.OnItemClickListener , View.OnClickListener {
    private var dataBinding:QuanFragmentShareBinding?=null
    private var sharePictureAdapter : SharePictureAdapter?=null
    private var REQUEST_CODE_SHARE=3001
    @Autowired(name="goods") @JvmField var goodsDetailBean :GoodsDetailBean?=null
    private var shareBean:GoodsShareBean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        dataBinding = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_share , container , false )
        dataBinding!!.goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)
        dataBinding!!.userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
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

            shareBean = it.resultData!!.share
            share_content.text = it.resultData!!.share!!.share
        })

        dataBinding!!.goodsViewModel!!.error.observe(this, Observer { it->
            if(TextUtils.isEmpty(it)) return@Observer

            showToast(it!!)
        })

        dataBinding!!.goodsViewModel!!.loading.observe(this, Observer { it->
            share_progress.visibility = if( it==null || !it ) View.GONE else View.VISIBLE
        })

        dataBinding!!.userViewModel!!.liveDataRollDescResult.observe(this , Observer { it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                return@Observer
            }

            initShareMessage(it.resultData)
        })
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        if(goodsDetailBean == null) return
        if( goodsDetailBean!!.pictureUrls == null) return

        sharePictureAdapter!!.setNewData(ArrayList())
        for (pic in goodsDetailBean!!.pictureUrls!!) {
            var bean = ShareBean(pic, false)
            sharePictureAdapter!!.addData(bean)
        }

        dataBinding!!.goodsViewModel!!.getShareInfo( goodsDetailBean!!.goodsId , goodsDetailBean!!.goodsSource )

        dataBinding!!.userViewModel!!.getRollDesc()
    }

    private fun initShareMessage(messageList : MessageModel?){
        if(messageList==null || messageList.list==null|| messageList.list!!.size<1){
            share_lay_scroll.visibility=View.GONE
            return
        }

        share_lay_scroll.visibility=View.VISIBLE



        share_scrollTextInfo.setResource(messageList.list)


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
                data[i].check = true //!data[i].check
                //sharePictureAdapter!!.notifyItemChanged(position)
            }else{
                data[i].check = false
            }
        }

        sharePictureAdapter!!.notifyDataSetChanged()
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

                testBitmpa()

                //shareImages("com.tencent.mm.ui.tools.ShareImgUI")
                //shareImageByWechaSDK(SendMessageToWX.Req.WXSceneSession)
            }
            R.id.share_weComment->{
                shareImages("com.tencent.mm.ui.tools.ShareToTimeLineUI")
                //shareImageByWechaSDK(SendMessageToWX.Req.WXSceneTimeline)
            }
        }
    }

    //var createPictureByLayout =CreatePictureByLayout()

    private fun testBitmpa(){

        var imagePath= AppUtil.getFileName( getSelectPicture())

        imagePath = Constants.ImageDirPath + goodsDetailBean!!.goodsId+"/"+ imagePath


        var info=SharePictureInfo(goodsDetailBean!!.name , goodsDetailBean!!.goodsSource
            , goodsDetailBean!!.finalPrice , goodsDetailBean!!.price , goodsDetailBean!!.couponPrice!! ,
            imagePath , "", "")
        var drawLongPictureUtil=DrawLongPictureUtil(context , info )

        drawLongPictureUtil.setListener(object:DrawLongPictureUtil.Listener{
            override fun onSuccess(path: String?) {
                //showToast("sss")
            }

            override fun onFail() {
                showToast("ss")
            }
        })


        drawLongPictureUtil.startDraw()

        //createPictureByLayout.initShareGoodsTemplete(goodsDetailBean!! , context!!)

    }

    private fun checkPermission():Boolean{
        if(activity==null) return false
        var list = PermissionsUtils.Builder(activity!!).addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).initPermission()
        return list.isEmpty()
    }

    /**
     * 将图片存到本地
     */
    private fun saveImage( needShare:Boolean=false , scene: Int= -1) {

        if(!checkPermission()) return

        if(goodsDetailBean==null || goodsDetailBean!!.pictureUrls ==null || goodsDetailBean!!.pictureUrls!!.size<1){
            showToast("没有图像需要下载！")
            return
        }

//        if(!checkSelectPicture()){
//            showToast("请勾选要下载的图片")
//            return
//        }


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

                    if(needShare){
                        //shareImages()
                        shareImageByWechaSDK(scene)
                    }else{
                        showToast("图片已经保存在"+dir)
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

        var index= 0
        var count = sharePictureAdapter!!.data.size
        for ( item in  sharePictureAdapter!!.data) {
            //if(!item.check) continue

            var url = item.url

            var name = AppUtil.getFileName( url )
            var path = dir + name
            var idId = IdId( index+1 , count )

            tasks.add(FileDownloader.getImpl().create(url).setPath(path).setTag(idId))

            index++

        }
        downLoadQueueSet.disableCallbackProgressTimes()
        downLoadQueueSet.setAutoRetryTimes(1)
        downLoadQueueSet.downloadSequentially(tasks)//串行下载
        downLoadQueueSet.start()
        dataBinding!!.goodsViewModel!!.loading.postValue(true)
    }

    private fun isDownPicture( dirPath : String ):Boolean{
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val imageDirectory = File(dirPath)
        val fileList = imageDirectory.list()
        return fileList.isEmpty()
    }

    private fun shareImages(wechatUI:String) {
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
        intent.component = ComponentName("com.tencent.mm",wechatUI)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        var shareIntent = Intent.createChooser(intent, "分享")
        //shareIntent.putExtra(Constants.INTENT_GOODSID , quan.dataId )
        //currentShareDataId=quan.dataId
        startActivityForResult( shareIntent , REQUEST_CODE_SHARE )
    }

    private fun checkSelectPicture():Boolean{
        if(sharePictureAdapter!!.data.size<1) return false
        for(item in sharePictureAdapter!!.data){
            if(item.check) return true
        }
        return false
    }

    private fun getSelectPicture():String?{
        if(sharePictureAdapter!!.data.size<1) return ""
        for(item in sharePictureAdapter!!.data){
            if(item.check) return item.url
        }
        return ""
    }


    private fun shareImageByWechaSDK( scene: Int ){
        if(goodsDetailBean==null) return
        if(shareBean==null) return

        if(!checkSelectPicture()) {
            showToast("请选择要分享的图片")
            return
        }

        var pictureName = getSelectPicture()
        if(TextUtils.isEmpty(pictureName)){
            return
        }
        pictureName = AppUtil.getFileName(pictureName)

        var dirPath = Constants.ImageDirPath + goodsDetailBean!!.goodsId+"/"
        if(isDownPicture(dirPath)) {
            saveImage(true , scene )
            return
        }

        var linkUrl = shareBean!!.weAppWebViewUrl
        if(TextUtils.isEmpty(linkUrl)){
            linkUrl=shareBean!!.weAppWebViewShortUrl
        }
        if(TextUtils.isEmpty(linkUrl)){
            linkUrl = shareBean!!.url
        }
        if(TextUtils.isEmpty(linkUrl)){
            linkUrl = shareBean!!.shortUrl
        }

        var title = shareBean!!.share

        var description = title


        val imageDirectory = File(dirPath)
        var filePath =""
        var list = imageDirectory.listFiles()
        for(item in list){
            var tempName =AppUtil.getFileName(item.path)
            if(tempName.equals(pictureName,true)){
                filePath = item.path
                break
            }
        }
        if( TextUtils.isEmpty( filePath)){
            showToast("请下载图片")
            return
        }

        var imageBytes = AppUtil.fileToByte(filePath)

        shareWechat(scene , linkUrl , title , description , imageBytes)
    }

    private fun shareWechat( scene:Int ,  linkUrl:String? , title:String?, description:String? , imageBytes:ByteArray?){

        var maxImageSize = 1024*32
        var imageSize = if(imageBytes==null ) 0 else imageBytes.size
        if(imageSize> maxImageSize){
            showToast("分享图片大小超过32KB,分享失败")
            return
        }

        var webPage = WXWebpageObject()
        webPage.webpageUrl = linkUrl//"http://wwww.baidu.com"

        var msg= WXMediaMessage(webPage)
        msg.title = title //"testtest"
        msg.description= description // "testtestest"
        //var bitmapFolder = Constants.ImageDirPath  + goodsDetailBean!!.goodsId +"/"
        //val imageDirectory = File(bitmapFolder)

        //val filePath = imageDirectory.list()[0]
        //var bitmap = AppUtil.fileToByte(filePath)

        msg.thumbData = imageBytes //bitmap

        var req = SendMessageToWX.Req()
        req.transaction =  System.currentTimeMillis().toString()
        req.message =msg
        req.scene = scene // SendMessageToWX.Req.WXSceneSession
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
         * @return A new instance of fragment ShareFragment.
         */
        @JvmStatic
        fun newInstance() =
            ShareFragment().apply {
            }
    }
}



data class IdId(var id :Int , var total:Int)