package com.example.digishoes.product

import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.digishoes.R
import com.example.digishoes.databinding.ActivityProductDetailsBinding
import com.example.digishoes.service.ImageLoadingService
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetails : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding

    val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        productDetailViewModel.productLiveData.observe(this) {
            binding.productNameTv.text = it.title
            imageLoadingService.load(binding.productIv, it.image)
            binding.productCurrentPriceTv.text =
                "${String.format("%,d", it.price)} تومان"
            binding.productPreviousPriceTv.text =
                "${String.format("%,d", it.previous_price)} تومان"
            binding.productPreviousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
    }
}