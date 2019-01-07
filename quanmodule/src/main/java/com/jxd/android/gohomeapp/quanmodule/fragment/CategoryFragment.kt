package com.jxd.android.gohomeapp.quanmodule.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.jxd.android.gohomeapp.libcommon.base.ARouterPath
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentCategoryBinding
import com.jxd.android.gohomeapp.quanmodule.generated.callback.OnClickListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@Route(path = ARouterPath.QuanFragmentCategoryPath)
class CategoryFragment : BaseBackFragment() , View.OnClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Autowired
    @JvmField var categoryId:Long=0

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
        //return inflater.inflate(R.layout.quan_fragment_category, container, false)

        var dataBinding : QuanFragmentCategoryBinding = DataBindingUtil
            .inflate(inflater , R.layout.quan_fragment_category , container , false)

        dataBinding.clickHandler = this

        return dataBinding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        initView()
    }



    override fun initView() {
        super.initView()

        var fragment = RecommandFragment.newInstance(categoryId.toString())
        this.loadRootFragment(R.id.category_container , fragment, false, true)
    }



    override fun onClick(v: View?) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}