package com.example.digishoes.feature.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.DigiActivity
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.data.Product
import com.example.digishoes.databinding.ActivityProductListBinding
import com.example.digishoes.feature.common.ProductAdapter
import com.example.digishoes.feature.common.VIEW_TYPE_SMALL
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductList : DigiActivity() {

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
    }
}