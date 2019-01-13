
package com.jxd.android.gohomeapp.quanmodule.fragment

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.jxd.android.gohomeapp.libcommon.base.BaseApplication
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentWebBinding
import com.jxd.android.gohomeapp.quanmodule.widget.TipAlertDialog
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_web.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_URL = "arg_url"


/**
 * A simple [Fragment] subclass.
 * Use the [WebFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WebFragment : BaseFragment() , View.OnClickListener {

    private var urlString: String? = null
    private var quanFragmentWebBinding:QuanFragmentWebBinding?=null
    //var urlIntercepter : UrlInterceptor ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlString = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        //return inflater.inflate(R.layout.quan_fragment_web, container, false)
        quanFragmentWebBinding = DataBindingUtil.inflate(inflater , R.layout.quan_fragment_web , container , false )
        quanFragmentWebBinding!!.clickHandler=this
        return quanFragmentWebBinding!!.root
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        initView()
    }

    override fun initView() {
        loadPage()
    }

    fun fetchData() {
    }


    private fun loadPage() {
        //urlIntercepter = UrlInterceptor(activity!!)

        web_webView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        web_webView.isVerticalScrollBarEnabled = false
        web_webView.isHorizontalScrollBarEnabled = false
        web_webView.isClickable = true
        web_webView.settings.useWideViewPort = true
        //是否需要避免页面放大缩小操作
        //viewPage.getSettings().setSupportZoom(true);
        //viewPage.getSettings().setBuiltInZoomControls(true);
        web_webView.settings.javaScriptEnabled = true
        web_webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        //webView.getSettings().setSaveFormData(true)
        web_webView.settings.allowFileAccess = true
        web_webView.settings.loadWithOverviewMode = false
        //viewPage.getSettings().setSavePassword(true);
        web_webView.settings.loadsImagesAutomatically = true
        web_webView.settings.domStorageEnabled = true
        web_webView.settings.setAppCacheEnabled(true)
        web_webView.settings.databaseEnabled = true
        context!!.applicationContext.getDir("database", Context.MODE_PRIVATE).path
        //webView.getSettings().setGeolocationDatabasePath(dir);
        web_webView.settings.setGeolocationEnabled(true)
        //webView.addJavascriptInterface(this, "android");
        val appCacheDir = context!!.applicationContext.getDir("cache", Context.MODE_PRIVATE).path
        web_webView.settings.setAppCachePath(appCacheDir)

        //        if(BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ){
        //            WebView.setWebContentsDebuggingEnabled(true);
        //        }
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            web_webView.settings.mixedContentMode =WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }

        //signHeader( viewPage );

        web_webView.loadUrl(urlString)

        web_webView.webViewClient =
                object : WebViewClient() {

                    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                        if (url != null && url.startsWith("tel:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                            return true
                        }

//                        var bundle = Bundle()
//                        bundle.putBoolean(Constants.INTENT_SHOWSHARE,false)
//                        bundle.putString(Constants.INTENT_URL , url )
//                        newIntent<WebActivity>( bundle )

                        view.loadUrl(url)

                        return true

//                        return if (urlIntercepter!!.shouldOverrideUrlLoading(view, url)) {
//                            true
//                        } else {
//                            super.shouldOverrideUrlLoading(view, url)
//                        }

//                        return super.shouldOverrideUrlLoading(view, url);
                    }

//                    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse {
//                        return super.shouldInterceptRequest(view, url)
//                    }

//                    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse {
//                        return super.shouldInterceptRequest(view, request)
//                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                        var url = request.url.toString()

                        if ( url != null && url.startsWith("tel:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse( url))
                            startActivity(intent)
                            return true
                        }


//                        var bundle = Bundle()
//                        bundle.putBoolean(Constants.INTENT_SHOWSHARE,false)
//                        bundle.putString(Constants.INTENT_URL , url )
//                        newIntent<WebActivity>( bundle )
                        view.loadUrl( url )
                        return true


//                        return if (urlIntercepter!!.shouldOverrideUrlLoading(view, url )) {
//                            true
//                        } else {
//                            super.shouldOverrideUrlLoading(view, request)
//                        }

                        //return super.shouldOverrideUrlLoading(view, request)
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                        super.doUpdateVisitedHistory(view, url, isReload)

                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        if (header_title == null) return
                        header_title.text =view?.title
                    }


                    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                        super.onReceivedError(view, request, error)
                        if (web_progressBar == null) return
                        web_progressBar.visibility = View.GONE
                    }


                    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                        super.onReceivedSslError(view, handler, error)
                        //当访问https网页，发生错误时，继续浏览网页
                        handler?.proceed()
                    }

                }


        web_webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String?) {
                super.onReceivedTitle(view, title)
                if (header_title == null) {
                    return
                }
                if (title == null) {
                    return
                }
                header_title.text =title
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if ( web_progressBar == null) return
                if (100 == newProgress) {
                    web_progressBar.visibility =View.GONE
                } else {
                    if (web_progressBar.visibility == View.GONE) web_progressBar.visibility = View.VISIBLE
                    web_progressBar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {

            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {
                openFileChooser(uploadMsg)
            }

            //For Android 4.1
            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String, capture: String) {
                openFileChooser(uploadMsg)
            }

            override fun onShowFileChooser(webView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: WebChromeClient.FileChooserParams): Boolean {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }

            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }

            override fun onJsConfirm(view: WebView?, url: String, message: String, result: JsResult): Boolean {
                //return super.onJsConfirm(view, url, message, result);
                if (view == null || view.context == null) return true

                val tipAlertDialog = TipAlertDialog(view.context, false)
                tipAlertDialog.show("询问", message, View.OnClickListener {
                    tipAlertDialog.dismiss()
                    result.cancel()
                }, View.OnClickListener {
                    tipAlertDialog.dismiss()
                    result.confirm()
                })

                return true
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
         * @return A new instance of fragment WebFragment.
         */
        @JvmStatic
        fun newInstance( url : String? ) =
                WebFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_URL , url)
                    }
                }
    }
}
