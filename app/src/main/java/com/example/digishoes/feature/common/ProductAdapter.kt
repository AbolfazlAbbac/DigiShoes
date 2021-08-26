package com.example.digishoes.feature.common

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.utils.Utils
import com.example.digishoes.R
import com.example.digishoes.common.implementSpringAnimationTrait
import com.example.digishoes.common.priceFormat
import com.example.digishoes.data.Product
import com.example.digishoes.service.ImageLoadingService
import com.example.digishoes.view.DigiImageView
import java.lang.IllegalStateException

const val VIEW_TYPE_LARGE = 0
const val VIEW_TYPE_ROUNDED = 1
const val VIEW_TYPE_SMALL = 2

class ProductAdapter(
    var viewType: Int = VIEW_TYPE_ROUNDED,
    val imageLoadingService: ImageLoadingService,
    val context: Context
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var productClickListener: onClickListener? = null
    var products = ArrayList<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val productNameTv: TextView = itemView.findViewById(R.id.productNameTv)
        val productImageView: DigiImageView = itemView.findViewById(R.id.productImageIv)
        val productPreviousPriceTv: TextView = itemView.findViewById(R.id.productPreviousPriceTv)
        val productCurrentPriceTv: TextView = itemView.findViewById(R.id.productCurrentPriceTv)
        fun bind(product: Product) {

            imageLoadingService.load(productImageView, product.image)
            productNameTv.text = product.title
            productCurrentPriceTv.text = priceFormat(product.price, context)
            productPreviousPriceTv.text = priceFormat(product.previous_price,context)
            productPreviousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                productClickListener?.productOnClickListener(product)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val resourceID = when (viewType) {
            VIEW_TYPE_ROUNDED -> R.layout.item_product
            VIEW_TYPE_SMALL -> R.layout.item_product_small
            VIEW_TYPE_LARGE -> R.layout.item_product_large

            else -> throw IllegalStateException("ViewType is Not Found - Class ProductAdapter")
        }
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(resourceID, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position])

    override fun getItemCount(): Int = products.size
    interface onClickListener {
        fun productOnClickListener(product: Product)
    }
}