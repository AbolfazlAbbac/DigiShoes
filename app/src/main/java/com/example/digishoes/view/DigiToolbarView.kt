package com.example.digishoes.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.digishoes.R

class DigiToolbarView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var setBackOnClickListener: View.OnClickListener? = null
        set(value) {
            field = value
            findViewById<ImageView>(R.id.backBtn).setOnClickListener(setBackOnClickListener)
        }

    init {
        inflate(context, R.layout.view_toolbar, this)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.DigiToolbarView)
            val title = a.getString(R.styleable.DigiToolbarView_dt_title)

            if (title != null && title.isNotEmpty()) {
                val toolbar: TextView = findViewById(R.id.toolbarTitleTv)
                toolbar.text = title
            }
            a.recycle()
        }
    }

}