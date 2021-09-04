package com.example.digishoes.feature.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.DigiActivity
import kotlinx.android.synthetic.main.activity_order_history.*
import org.koin.android.ext.android.inject

class OrderHistory : DigiActivity() {
    val viewModel: OrderHistoryViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        viewModel.progressBar.observe(this) {
            setProgressbarIndicator(it)
        }

        orderHistoryRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        viewModel.order.observe(this) {
            orderHistoryRv.adapter = HistoryAdapter(this, it)
        }

        toolbarView.setBackOnClickListener = View.OnClickListener {
            finish()
        }
    }
}