package com.example.digishoes.feature.main

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

class ProductAdapterPopular(val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<ProductAdapterPopular.ViewHolder>() {

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
            productNameTv.text = product.title
            productCurrentPriceTv.text = "${String.format("%,d", product.price)} تومان"
            productPreviousPriceTv.text = "${String.format("%,d", product.previous_price)} تومان"
            productPreviousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            imageLoadingService.load(productImageIV, product.image)
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
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
}