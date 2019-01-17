package com.jxd.android.gohomeapp.quanmodule


import android.arch.lifecycle.ViewModelProviders
import android.content.ClipboardManager
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseActivity
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.bean.Constants
import com.jxd.android.gohomeapp.libcommon.bean.OrderBean
import com.jxd.android.gohomeapp.libcommon.bean.SearchGoodsBean
import com.jxd.android.gohomeapp.libcommon.util.KeybordUtils
import com.jxd.android.gohomeapp.libcommon.util.SPUtils
import com.jxd.android.gohomeapp.libcommon.util.showToast
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider2
import com.jxd.android.gohomeapp.quanmodule.adapter.RecommandAdapter
import com.jxd.android.gohomeapp.quanmodule.adapter.SearchResultAdapter
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanActivitySearchBinding
import com.jxd.android.gohomeapp.quanmodule.fragment.SearchResultFragment
import com.jxd.android.gohomeapp.quanmodule.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.layout_clipboard.*
import kotlinx.android.synthetic.main.layout_search_header.*
import kotlinx.android.synthetic.main.layout_search_header.view.*
import kotlinx.android.synthetic.main.quan_activity_search.*
import kotlinx.android.synthetic.main.quan_fragment_tab.*
import me.gujun.android.taggroup.TagGroup
import java.security.Key
import java.util.*
import kotlin.collections.ArrayList

@Route(path=ARouterPath.QuanActivitySearch)
class SearchActivity : BaseActivity()
    , TextView.OnEditorActionListener
    , BaseQuickAdapter.RequestLoadMoreListener
    , BaseQuickAdapter.OnItemClickListener
    , TextWatcher
    , View.OnFocusChangeListener
    , View.OnClickListener
    , TagGroup.OnTagClickListener {

    var tags : ArrayList<String>?=null
    var hotTags =ArrayList<String>()
    var dataBinding:QuanActivitySearchBinding?=null
    var page:Int=0
    var searchAdapter:SearchResultAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this,R.layout.quan_activity_search)
        dataBinding!!.goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel::class.java)

        initView()

        checkClipBoard()
    }

    override fun initView(){

        hotTags.add("s手动发送")
        hotTags.add("任务")
        hotTags.add("df")
        hotTags.add("打发")
        hotTags.add("reer玩儿玩儿")
        hotTags.add("沃尔沃斯蒂芬")
        search_tags_hot.setTags(hotTags)

        search_tags.setOnTagClickListener(this)
        search_tags_hot.setOnTagClickListener(this)
        search_delete.setOnClickListener(this)
        search_cancel.setOnClickListener(this)

        clipboard_close.setOnClickListener(this)
        clipboard_pinduoduo.setOnClickListener(this)

        search_key.setOnEditorActionListener(this)
        search_key.addTextChangedListener(this)
        search_key.onFocusChangeListener =this

        //KeybordUtils.openKeybord( this ,  search_key)

        tags = ArrayList( SPUtils.getInstance(this, Constants.PREF_SEARCH_FILENAME).readStringSet(Constants.PREF_KEY , Collections.emptySet() ))
        search_tags.setTags(tags)


        searchAdapter= SearchResultAdapter(ArrayList())
        searchAdapter!!.onItemClickListener=this
        searchAdapter!!.setOnLoadMoreListener(this,search_recyclerView)

        search_recyclerView.layoutManager= GridLayoutManager(this,2)
        search_recyclerView.adapter=searchAdapter

        search_recyclerView.addItemDecoration( ItemDevider2(this , 15f , R.color.white ) )

//        if(findFragment(SearchResultFragment::class.java)==null){
//            this.loadRootFragment(R.id.search_container , SearchResultFragment.newInstance("",""))
//        }
        search_recyclerView.visibility=View.GONE
        search_scrollview.visibility=View.VISIBLE

        dataBinding!!.goodsViewModel!!.liveDataSearchResult
            .observe(this,android.arch.lifecycle.Observer { it->

                if(search_recyclerView.visibility==View.GONE){
                    search_recyclerView.visibility=View.VISIBLE
                }
                if(search_scrollview.visibility==View.VISIBLE){
                    search_scrollview.visibility=View.GONE
                }


                if(it!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
                    showToast(it.resultMsg)
                    return@Observer
                }


                search_fouse.requestFocus()
                KeybordUtils.closeKeyboard(this, search_key)
                search_recyclerView.requestFocus()

                var datas: ArrayList<SearchGoodsBean>?
                if (it.resultData == null || it.resultData!!.list ==null ) {
                    searchAdapter!!.loadMoreEnd(false)
                } else {
                    datas = it.resultData!!.list
                    if (  datas!!.size < 1) {
                        searchAdapter!!.loadMoreEnd(false)
                    } else {
                        searchAdapter!!.loadMoreComplete()
                        page++
                    }
                    searchAdapter!!.addData(datas)
                }

            })

        dataBinding!!.goodsViewModel!!.loading.observe(this, android.arch.lifecycle.Observer { it->
            search_progress.visibility = if(it==null || !it) View.GONE else View.VISIBLE
        })

        dataBinding!!.goodsViewModel!!.error.observe(this, android.arch.lifecycle.Observer { it->
            if(TextUtils.isEmpty(it)) return@Observer
            search_progress.visibility=View.GONE
            showToast(it!!)
        })


        dataBinding!!.goodsViewModel!!.liveDataHotSearch.observe(this,android.arch.lifecycle.Observer{it->

            if(it!!.resultCode!= ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            if(it.resultData==null||it.resultData!!.data==null) return@Observer

            var hotTags=  it.resultData!!.data
            search_tags_hot.setTags(hotTags)

        })


        dataBinding!!.goodsViewModel!!.getHotSearch()

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(hasFocus){
            search_scrollview.visibility=View.VISIBLE
            search_recyclerView.visibility=View.GONE
        }
    }

    private fun checkClipBoard(){
        val cm =  this.getSystemService (Context.CLIPBOARD_SERVICE) as ClipboardManager
        if(cm.hasPrimaryClip()){
            var clipData = cm.primaryClip
            if( clipData !=null && clipData.itemCount>0 ){
                var text = clipData.getItemAt(0).coerceToText(this)
                clipboard_text.text = text
                search_clipboard.visibility = View.VISIBLE
                KeybordUtils.closeKeyboard(this , search_key)
                return
            }
        }
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var goodsid = searchAdapter!!.getItem(position)!!.goodsId
        ARouter.getInstance().build(ARouterPath.QuanActivityGoodsDetailPath).withString("goodsId",goodsid).navigation()
    }

    override fun onTagClick(tag: String?) {
        page=0
        searchAdapter!!.setNewData(ArrayList())
        dataBinding!!.goodsViewModel!!.search(tag , page+1)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if( actionId == EditorInfo.IME_ACTION_SEARCH){
            goSearch()
        }
        return false
    }

    private fun goSearch(){
        var key = search_key.text.toString()
        if(TextUtils.isEmpty(key))return

        if(!tags!!.contains( key )) {
            tags!!.add(key)
            SPUtils.getInstance(this, Constants.PREF_SEARCH_FILENAME).writeStringSet(Constants.PREF_KEY, tags!!.toSet())
            search_tags.setTags(tags)
        }

        page=0
        searchAdapter!!.setNewData(ArrayList())

        KeybordUtils.closeKeyboard(this , search_key)

        dataBinding!!.goodsViewModel!!.search(key, page+1)
    }



    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.search_delete->{
                delete()
            }
            R.id.search_cancel->{
                KeybordUtils.closeKeyboard(this)
                finish()
            }
            R.id.clipboard_close->{
                search_clipboard.visibility=View.GONE
            }
            R.id.clipboard_pinduoduo->{
                search_clipboard.visibility=View.GONE
                search_key.setText(clipboard_text.text )
                goSearch()
            }
        }

    }

    fun delete(){
        tags!!.clear()
        SPUtils.getInstance(this,Constants.PREF_SEARCH_FILENAME).clear()
        search_key.setText("")
        search_tags.setTags(tags)
    }

    override fun onDestroy() {
        super.onDestroy()
        KeybordUtils.closeKeyboard(this)
    }

    override fun onLoadMoreRequested() {
        var key = search_key.text.toString()
        dataBinding!!.goodsViewModel!!.search(key, page+1)
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        var key  = search_key.text.trim()
        if(TextUtils.isEmpty(key)){
            page=0
            searchAdapter!!.setNewData(ArrayList())
            search_recyclerView.visibility=View.GONE
            search_scrollview.visibility=View.VISIBLE
        }

    }
}
