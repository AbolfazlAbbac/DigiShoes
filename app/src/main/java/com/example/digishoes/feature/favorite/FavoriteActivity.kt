package com.example.digishoes.feature.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.DigiActivity
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.data.Product
import com.example.digishoes.product.ProductDetails
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.empty_state_view_favorte.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class FavoriteActivity : DigiActivity(), FavoriteAdapter.onEventProductListener {
    val viewModel: FavoriteViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        toolbarView.setBackOnClickListener = View.OnClickListener {
            finish()
        }
        informationBtn.setOnClickListener {
            snackBar(getString(R.string.helpMessageFavoritePage), Snackbar.LENGTH_LONG)
        }


        viewModel.productLiveData.observe(this) {
            if (it.isNotEmpty()) {
                favoriteRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                favoriteRv.adapter = FavoriteAdapter(it as MutableList<Product>, this, get())
            } else {
                showEmptyState(R.layout.empty_state_view_favorte)
                messageEmptyState.text = getString(R.string.favoriteMessage)
            }
        }
    }


    override fun onClickProduct(product: Product) {
        startActivity(Intent(this, ProductDetails::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onLongClickProduct(product: Product) {
        viewModel.removeFavorite(product)
        snackBar(getString(R.string.deleteFavorite), Snackbar.LENGTH_SHORT)
    }
}
