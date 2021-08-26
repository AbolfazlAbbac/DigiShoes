package com.example.digishoes.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.digishoes.R
import com.example.digishoes.common.DigiActivity
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.data.Product
import com.example.digishoes.databinding.ActivityProductListBinding
import com.example.digishoes.feature.common.ProductAdapter
import com.example.digishoes.feature.common.VIEW_TYPE_LARGE
import com.example.digishoes.feature.common.VIEW_TYPE_SMALL
import com.example.digishoes.product.ProductDetails
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductList : DigiActivity(), ProductAdapter.onClickListener {

    val productListViewModel: ProductListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_DATA
            )
        )
    }

    val productAdapter: ProductAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }
    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val gridManger = GridLayoutManager(this, 2)
        binding.productListRv.layoutManager = gridManger
        binding.productListRv.adapter = productAdapter
        productListViewModel.productLiveData.observe(this) {
            Timber.i("List Products -> $it")
            productAdapter.products = it as ArrayList<Product>
        }

        binding.selectedSortView.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    R.array.sortArray,
                    productListViewModel.sort
                ) { dialog, sort ->
                    dialog.dismiss()
                    productListViewModel.selectChangeSort(sort)
                }.setTitle(R.string.sort)
            dialog.show()
        }

        productListViewModel.selectedSortLiveData.observe(this) {
            binding.selectedSortTv.text = getString(it)
        }

        binding.viewTypeiv.setOnClickListener {
            if (productAdapter.viewType == VIEW_TYPE_SMALL) {
                gridManger.spanCount = 1
                binding.viewTypeiv.setImageResource(R.drawable.ic_baseline_crop_square_24)
                productAdapter.viewType = VIEW_TYPE_LARGE
            } else {
                gridManger.spanCount = 2
                binding.viewTypeiv.setImageResource(R.drawable.ic_grid)
                productAdapter.viewType = VIEW_TYPE_SMALL
            }
        }
        

        binding.toolbarView.setBackOnClickListener = View.OnClickListener {
            finish()
        }

        productListViewModel.progressBar.observe(this) {
            setProgressbarIndicator(it)
        }

        productAdapter.productClickListener = this
    }

    override fun productOnClickListener(product: Product) {
        startActivity(Intent(this, ProductDetails::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}