package com.jxd.android.gohomeapp.quanmodule.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.bean.FavoriteBean
import com.jxd.android.gohomeapp.libcommon.util.showToast

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.adapter.FavoriteAdapter
import com.jxd.android.gohomeapp.quanmodule.adapter.ItemDevider3
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentFavoriteBinding
import kotlinx.android.synthetic.main.layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_favorite.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FavoriteFragment : BaseBackFragment() ,View.OnClickListener
    , BaseQuickAdapter.OnItemChildClickListener
    , SwipeRefreshLayout.OnRefreshListener
    , BaseQuickAdapter.RequestLoadMoreListener {
    private var param1: String? = null
    private var param2: String? = null
    var favoriteAdapter: FavoriteAdapter?=null
    var data= ArrayList<FavoriteBean>()
    var selectedAll=false



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
        //return inflater.inflate(R.layout.quan_fragment_favorite, container, false)
        var dataBinding:QuanFragmentFavoriteBinding =DataBindingUtil.inflate(inflater , R.layout.quan_fragment_favorite, container , false)
        dataBinding.clickHandler = this
        return dataBinding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }


    override fun initView(){
        header_title.text="收藏夹"
        header_right_text.text="批量删除"


        favoriteAdapter = FavoriteAdapter(data)
        favoriteAdapter!!.setOnLoadMoreListener(this , favorite_recyclerview)
        favoriteAdapter!!.onItemChildClickListener =this
        favorite_recyclerview.layoutManager= LinearLayoutManager(this.context)
        favorite_recyclerview.addItemDecoration( ItemDevider3(this.context!! , 1f , R.color.linecolor , 15f ))
        favorite_recyclerview.adapter = favoriteAdapter
        favorite_refreshview.setOnRefreshListener(this)

        for(i in 0..10){
            data.add(FavoriteBean(1, false , 0 ,"测试" ,"", "","", "http://app.infunpw.com/commons/images/cinema/cinema_films/3823.jpg" ,null,"","","","","",""))
        }
        favoriteAdapter!!.notifyDataSetChanged()

        //presenter=FavoritePresenter(this)
        //presenter!!.favoriteList(pageIndex)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.header_left_image -> {
                _mActivity.onBackPressed()
            }
            R.id.header_right_text -> {
                batchDelete()
            }
        }
    }

    private fun batchDelete(){
        showToast("todo")
    }

    override fun onRefresh() {

    }

    override fun onLoadMoreRequested() {

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        (adapter!!.getItem(position) as FavoriteBean).selected=  !(adapter!!.getItem(position) as FavoriteBean).selected
        adapter.notifyItemChanged(position)

        selectedAll=true
        for(bean in data){
            if(!bean.selected){
                selectedAll=false
                break
            }
        }

        favorite_select.setCompoundDrawables( if(selectedAll) ContextCompat.getDrawable(this.context!! , R.mipmap.selected) else ContextCompat.getDrawable(this.context!! , R.mipmap.unselected) , null,null,null )

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
