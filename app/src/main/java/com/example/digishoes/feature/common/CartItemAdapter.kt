package com.example.digishoes.feature.common

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.priceFormat
import com.example.digishoes.data.CartItem
import com.example.digishoes.data.PurchaseDetail
import com.example.digishoes.service.ImageLoadingService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_purchase_detail.view.*

const val View_TYPE_ITEM_CART = 0
const val View_TYPE_PURCHASE_DETAIL = 1


class CartItemAdapter(
    var cartItems: MutableList<CartItem>,
    val context: Context,
    val imageLoadingService: ImageLoadingService,
    val cartItemListener: CartItemListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var purchaseDetail: PurchaseDetail? = null

    inner class CartItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun binItem(cartItem: CartItem) {
            containerView.productNameTv.text = cartItem.product.title

            containerView.productPreviousPriceTv.text =
                priceFormat(cartItem.product.price + cartItem.product.discount, context)

            containerView.productCurrentPriceTv.text = priceFormat(cartItem.product.price, context)

            containerView.productPreviousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            imageLoadingService.load(containerView.productPictureIv, cartItem.product.image)
            containerView.cartCountTv.text = cartItem.count.toString()


            containerView.progressbarCountCart.visibility =
                if (cartItem.changeProgressBarCartIsVisibility) View.VISIBLE else View.GONE


            containerView.cartCountTv.visibility =
                if (cartItem.changeProgressBarCartIsVisibility) View.INVISIBLE else View.VISIBLE


            containerView.productPictureIv.setOnClickListener {
                cartItemListener.onClickProductImage(cartItem)
            }

            containerView.increaseBtn.setOnClickListener {
                cartItem.changeProgressBarCartIsVisibility = true
                containerView.progressbarCountCart.visibility = View.VISIBLE
                containerView.cartCountTv.visibility = View.INVISIBLE
                cartItemListener.increaseCountProduct(cartItem)
            }

            containerView.decreaseBtn.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeProgressBarCartIsVisibility = true
                    containerView.progressbarCountCart.visibility = View.VISIBLE
                    containerView.cartCountTv.visibility = View.INVISIBLE
                    cartItemListener.decreaseCountProduct(cartItem)
                }
            }
            containerView.removeProductFromCartBtn.setOnClickListener {
                cartItemListener.onRemoveProductFromCart(cartItem)
            }
        }
    }

    class PurchaseDetailViewHolder(override val containerView: View,val context: Context) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(totalPrice: Int, payablePrice: Int, shippingCost: Int) {
            containerView.totalPriceTv.text = priceFormat(totalPrice, context)
            containerView.amountPayableTv.text = priceFormat(payablePrice, context)
            containerView.shippingCostTv.text = priceFormat(shippingCost, context)
        }
    }

    interface CartItemListener {
        fun onClickProductImage(cartItem: CartItem)
        fun increaseCountProduct(cartItem: CartItem)
        fun decreaseCountProduct(cartItem: CartItem)
        fun onRemoveProductFromCart(cartItem: CartItem)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == cartItems.size) {
            View_TYPE_PURCHASE_DETAIL
        } else {
            View_TYPE_ITEM_CART
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == View_TYPE_PURCHASE_DETAIL) {
            PurchaseDetailViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_purchase_detail, parent, false)
            ,context)
        } else {
            CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_cart, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder) {
            holder.binItem(cartItems[position])
        } else if (holder is PurchaseDetailViewHolder) {
            purchaseDetail?.let {
                holder.bind(
                    it.total_price,
                    it.payable_price,
                    it.shipping_cost
                )
            }
        }
    }

    fun increaseCountItem(cartItem: CartItem) {
        val indexOf = cartItems.indexOf(cartItem)
        if (indexOf > -1) {
            cartItems[indexOf].changeProgressBarCartIsVisibility = false
            notifyItemChanged(indexOf)
        }
    }

    fun decreaseCountItem(cartItem: CartItem) {
        val indexOf = cartItems.indexOf(cartItem)
        if (indexOf > -1) {
            cartItems[indexOf].changeProgressBarCartIsVisibility = false
            notifyItemChanged(indexOf)
        }
    }

    fun removeItem(cartItem: CartItem) {
        val indexOf = cartItems.indexOf(cartItem)
        if (indexOf > -1) {
            cartItems.removeAt(indexOf)
            notifyItemRemoved(indexOf)
        }
    }

    override fun getItemCount(): Int = cartItems.size + 1
}

