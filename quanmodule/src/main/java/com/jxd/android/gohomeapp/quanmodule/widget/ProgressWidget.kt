package com.jxd.android.gohomeapp.quanmodule.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.jxd.android.gohomeapp.quanmodule.R


/**
 * 进度组件
 */
class ProgressWidget : LinearLayout {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val layoutInflater = LayoutInflater.from(this.context)
        layoutInflater.inflate(R.layout.quan_layout_progress, this, true)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

    }
}
