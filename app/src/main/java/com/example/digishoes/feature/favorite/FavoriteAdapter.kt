package com.example.digishoes.feature.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.data.Product
import com.example.digishoes.service.ImageLoadingService
import com.example.digishoes.view.DigiImageView

class FavoriteAdapter(
    private val products: MutableList<Product>,
    val favoriteProductEvent: onEventProductListener,
    val imageLoadingService: ImageLoadingService
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.favoriteTextView)
        val image: DigiImageView = itemView.findViewById(R.id.favoriteImage)

        fun bindProduct(product: Product) {
            text.text = product.title
            imageLoadingService.load(image, product.image)

            itemView.setOnClickListener {
                favoriteProductEvent.onClickProduct(product)
            }
            itemView.setOnLongClickListener {
                favoriteProductEvent.onLongClickProduct(product)
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

    interface onEventProductListener {
        fun onClickProduct(product: Product)
        fun onLongClickProduct(product: Product)
    }
}