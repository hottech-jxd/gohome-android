package com.jxd.android.gohomeapp.quanmodule.fragment


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.gyf.barlibrary.ImmersionBar
import com.jxd.android.gohomeapp.libcommon.base.BaseBackFragment
import com.jxd.android.gohomeapp.libcommon.base.BaseFragment
import com.jxd.android.gohomeapp.libcommon.bean.ApiResultCodeEnum
import com.jxd.android.gohomeapp.libcommon.bean.ProfitStatBean
import com.jxd.android.gohomeapp.libcommon.bean.ProfitStatDataBean
import com.jxd.android.gohomeapp.libcommon.util.DateUtils
import com.jxd.android.gohomeapp.libcommon.util.showToast

import com.jxd.android.gohomeapp.quanmodule.R
import com.jxd.android.gohomeapp.quanmodule.R.id.income_lineChart
import com.jxd.android.gohomeapp.quanmodule.R.mipmap.x
import com.jxd.android.gohomeapp.quanmodule.databinding.QuanFragmentIncomeBinding
import com.jxd.android.gohomeapp.quanmodule.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.quan_layout_common_header.*
import kotlinx.android.synthetic.main.quan_fragment_cash.*
import kotlinx.android.synthetic.main.quan_fragment_income.*
import kotlinx.android.synthetic.main.quan_fragment_income.view.*
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

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
class IncomeFragment : BaseBackFragment() , View.OnClickListener , OnChartValueSelectedListener , SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var dataBinding:QuanFragmentIncomeBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?  ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.quan_fragment_income , container ,false )
        dataBinding!!.clickHandler = this
        dataBinding!!.userViewModel=ViewModelProviders.of(this).get(UserViewModel::class.java)
        dataBinding!!.statsData = ProfitStatBean(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, null, BigDecimal.ZERO)
        return  dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ImmersionBar.with(this).statusBarColor(R.color.default_status_color).init()


        header_title.text="收益中心"

        income_refreshview.setOnRefreshListener(this)

        initLineChart()

        dataBinding!!.userViewModel!!.liveDataProfitStat.observe(this,android.arch.lifecycle.Observer { it->

            income_refreshview.isRefreshing=false

            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }

            setChart(it.resultData)

        })


        UserViewModel.liveDataMyResult.observe(this, android.arch.lifecycle.Observer {  it->
            if(it!!.resultCode!=ApiResultCodeEnum.SUCCESS.code){
                showToast(it.resultMsg)
                return@Observer
            }
            if(it.resultData==null || it.resultData!!.data==null ) return@Observer

            income_balance.text = it.resultData!!.data!!.userBalance.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()
        })

        dataBinding!!.userViewModel!!.hasError.observe(this, android.arch.lifecycle.Observer { it->
            if(it==false) return@Observer
            income_refreshview.isRefreshing=false
        })

        dataBinding!!.userViewModel!!.error.observe(this, android.arch.lifecycle.Observer { it->
            if(TextUtils.isEmpty(it))return@Observer
            income_refreshview.isRefreshing=false
            showToast(it!!)
        })
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)



        dataBinding!!.userViewModel!!.getProfitStat()
    }

    override fun onRefresh() {

        dataBinding!!.userViewModel!!.getProfitStat()
    }

    private fun initLineChart(){

        income_lineChart.setNoDataText("暂无统计数据")
        income_lineChart.description.isEnabled=false
        income_lineChart.axisRight.isEnabled=false
        income_lineChart.setOnChartValueSelectedListener(this)
        //income_lineChart.animateXY(1500,1500)
        income_lineChart.axisRight.isEnabled=false
        var myMarkerView = MyMarkerView(context , R.layout.layout_markerview)
        income_lineChart.marker = myMarkerView
        myMarkerView.chartView = income_lineChart
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
        legend.textColor = Color.BLACK
        legend.xOffset = 0f
        legend.yOffset = 10f
        legend.yEntrySpace = 0f
        //legend.formToTextSpace = 50f
        //legend.mNeededHeight = 80f


    }

    fun setChart(data: ProfitStatBean?){
        if(data==null )return

        dataBinding!!.statsData = data


        var stats = data.trendsProfit
        if(stats==null) return
        var list = ArrayList<Entry>()
        var x = 0f
        for(item in stats) {
            x++
            var entry = Entry1( item  , x, item.profitAmount.toFloat() )
            list.add(entry)
        }

        var dataSet = LineDataSet(list, "本周期")
        dataSet.setDrawValues(false)
        dataSet.setCircleColor(Color.BLACK)
        dataSet.setDrawCircles(false)
        dataSet.setColor(Color.RED)
        dataSet.setDrawCircleHole(false)
        dataSet.valueTextColor =Color.BLACK
        dataSet.axisDependency = YAxis.AxisDependency.LEFT

        var lineData = LineData()
        lineData.addDataSet(dataSet)


//        var list2 = ArrayList<Entry>()
//        var entry2 = Entry2("02/01", "￥112", 1f,20f)
//        list2.add(entry2)
//        entry2 = Entry2("03/01", "￥113", 2f, 18f)
//        list2.add(entry2)
//        entry2 = Entry2("04/01","￥114", 3f, 38f)
//        list2.add(entry2)
//        entry2 = Entry2("05/01","￥115", 4f, 58f)
//        list2.add(entry2)
//        entry2 = Entry2("06/01","￥116", 5f, 18f)
//        list2.add(entry2)
//        var dataSet2 = LineDataSet(list2, "上周期")
//        dataSet2.setColor(Color.GREEN)
//        dataSet2.setDrawCircles(false)
//        dataSet2.setDrawCircleHole(false)
//        dataSet2.setCircleColor(Color.BLACK)
//        dataSet2.setDrawValues(false)
//        dataSet2.axisDependency = YAxis.AxisDependency.LEFT
//        lineData.addDataSet(dataSet2)


        income_lineChart.data = lineData
        income_lineChart.invalidate()
    }

    override fun onClick(v: View?) {
        if(v!!.id==R.id.header_left_image){
            _mActivity.onBackPressed()
        }
    }

    override fun onNothingSelected() {

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

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


    class MyMarkerView: MarkerView{
        var tvDate1 :TextView?=null
        var tvContent1:TextView?=null
        var tvDate2 :TextView?=null
        var tvContent2:TextView?=null

        constructor(context: Context?, layoutResource: Int) : super(context, layoutResource){
            tvDate1 = findViewById(R.id.markerview_date1)
            tvContent1 = findViewById(R.id.markerview_content1 )
            tvDate2 = findViewById(R.id.markerview_date2)
            tvContent2= findViewById(R.id.markerview_content2)
        }

        override fun getOffset(): MPPointF {
            return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }

        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            super.refreshContent(e, highlight)

            for(dataset  in chartView.data.dataSets) {
                //if( currentDataSet ==null || currentDataSet!=null && dataset != currentDataSet) {
                    var en = dataset.getEntriesForXValue(e!!.x)
                if(en==null || en.size<1) continue
                var eee = en[0]
                    if (eee is Entry1) {
                        //var e1 = eee
                        tvDate1!!.text = DateUtils.formatDate( eee.data.profitData)
                        tvContent1!!.text = "￥"+ eee.data.profitAmount.setScale(2,BigDecimal.ROUND_HALF_UP).toString() //Utils.formatNumber(e.x , 0, true)

                    }
//                    else if( eee is Entry2 ) {
//                        var e2 = eee as Entry2
//                        tvDate2!!.text = e2.date
//                        tvContent2!!.text = e2.amount //Utils.formatNumber(e.getY(), 0, true)
//                    }
                }


            super.refreshContent(e, highlight)
        }
    }

    class Entry1(var data :ProfitStatDataBean , x:Float,  y:Float): Entry( x,  y) {

    }

    class Entry2(var date:String , var amount:BigDecimal , x:Float,  y:Float): Entry( x,  y){

    }

}
