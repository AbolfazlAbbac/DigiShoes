package com.example.digishoes.feature.main

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.implementSpringAnimationTrait
import com.example.digishoes.data.Product
import com.example.digishoes.service.ImageLoadingService
import com.example.digishoes.view.DigiImageView

class ProductAdapterPopular(val imageLoadingService: ImageLoadingService, val context: Context) :
    RecyclerView.Adapter<ProductAdapterPopular.ViewHolder>() {
    var productClickListener: ProductAdapter.onClickListener? = null

    var products = ArrayList<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTv: TextView = itemView.findViewById(R.id.productNameTv)
        val productCurrentPriceTv: TextView = itemView.findViewById(R.id.productCurrentPriceTv)
        val productPreviousPriceTv: TextView = itemView.findViewById(R.id.productPreviousPriceTv)
        val productImageIV: DigiImageView = itemView.findViewById(R.id.productImageIv)
        fun bind(product: Product) {
            val toman: String = context.getString(R.string.toman)

            productNameTv.text = product.title
            productCurrentPriceTv.text = "${String.format("%,d", product.price)} $toman"
            productPreviousPriceTv.text = "${String.format("%,d", product.previous_price)} $toman"
            productPreviousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            imageLoadingService.load(productImageIV, product.image)
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                productClickListener?.productOnClickListener(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position])


    override fun getItemCount(): Int = products.size
    interface onClickListener {
        fun productOnClickListener(product: Product)
    }
}