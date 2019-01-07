package com.jxd.android.gohomeapp.quanmodule.fragment


import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentIncomeBinding
import kotlinx.android.synthetic.main.quan_fragment_income.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 * 收益
 */
class IncomeFragment : BaseBackFragment() , View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        //return inflater.inflate(R.layout.quan_fragment_income, container, false)

        var dataBinding :QuanFragmentIncomeBinding = DataBindingUtil.inflate(inflater, R.layout.quan_fragment_income , container ,false )
        dataBinding.clickHandler = this
        return  dataBinding.root
    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        initLineChart()
    }


    fun initLineChart(){

        income_lineChart.axisRight.isEnabled=false

        var xAxis = income_lineChart.xAxis

        xAxis.setLabelCount(8 , false)
        xAxis.textSize = 10f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = ContextCompat.getColor(context!!, R.color.linecolor)
        //xAxis.axisLineColor = ContextCompat.getColor(context!!, R.color.linecolor)
        xAxis.setDrawGridLines(false)


        var yAxis = income_lineChart.axisLeft
        yAxis.axisLineColor = ContextCompat.getColor(context!!, R.color.linecolor)
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        yAxis.gridColor = ContextCompat.getColor(context!!, R.color.linecolor)
        yAxis.gridLineWidth= 2f
        yAxis.setDrawGridLines(true)


        var legend:Legend = income_lineChart.legend
        legend.textSize = 10f
        legend.direction = Legend.LegendDirection.LEFT_TO_RIGHT
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.form = Legend.LegendForm.LINE
        legend.textColor = Color.RED
        legend.xOffset = 10f
        legend.yOffset = 10f





        var list = ArrayList<Entry>()
        var entry = Entry(1f,200f)
        list.add(entry)
        entry = Entry(2f, 180f)
        list.add(entry)
        entry = Entry(3f, 380f)
        list.add(entry)
        entry = Entry(4f, 580f)
        list.add(entry)
        entry = Entry(5f, 180f)
        list.add(entry)
        var dataSet = LineDataSet(list, "本周期")
        dataSet.setDrawValues(false)
        dataSet.setColor(Color.RED)
        dataSet.axisDependency = YAxis.AxisDependency.LEFT

        var lineData = LineData()
        lineData.addDataSet(dataSet)


        var list2 = ArrayList<Entry>()
        entry = Entry(1f,20f)
        list2.add(entry)
        entry = Entry(2f, 18f)
        list2.add(entry)
        entry = Entry(3f, 38f)
        list2.add(entry)
        entry = Entry(4f, 58f)
        list2.add(entry)
        entry = Entry(5f, 18f)
        list2.add(entry)
        var dataSet2 = LineDataSet(list2, "上周期")
        dataSet2.setColor(Color.GREEN)
        dataSet2.axisDependency = YAxis.AxisDependency.LEFT


        lineData.addDataSet(dataSet2)


        income_lineChart.data = lineData
        income_lineChart.invalidate()

    }

    override fun onClick(v: View?) {
        if(v!!.id==R.id.header_left_image){
            _mActivity.onBackPressed()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IncomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IncomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
