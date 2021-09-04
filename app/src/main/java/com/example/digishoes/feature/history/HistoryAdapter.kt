package com.example.digishoes.feature.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.convertDpToPixel
import com.example.digishoes.common.priceFormat
import com.example.digishoes.data.OrderHistoryItem
import com.example.digishoes.view.DigiImageView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order_history.view.*

class HistoryAdapter(val context: Context, val listOrderHistoryItem: List<OrderHistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolderHistory>() {

    val layoutParams: LinearLayout.LayoutParams

    init {
        val size = convertDpToPixel(100f, context).toInt()
        val margin = convertDpToPixel(8f, context).toInt()
        layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
    }

    inner class ViewHolderHistory(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(orderHistoryItem: OrderHistoryItem) {
            containerView.orderHistoryPayablePriceTv.text =
                priceFormat(orderHistoryItem.payable, context)
            containerView.orderHistoryIdTv.text = orderHistoryItem.id.toString()
            containerView.orderHistoryll.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView = DigiImageView(context)
                imageView.layoutParams = layoutParams
                imageView.setImageURI(it.product.image)
                containerView.orderHistoryll.addView(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistory {
        return ViewHolderHistory(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderHistory, position: Int) =
        holder.bind(listOrderHistoryItem[position])

    override fun getItemCount(): Int = listOrderHistoryItem.size
}