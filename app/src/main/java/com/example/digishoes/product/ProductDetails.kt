package com.example.digishoes.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.R
import com.example.digishoes.common.EXTRA_KEY_ID
import com.example.digishoes.common.DigiActivity
import com.example.digishoes.common.DigiCompletableObserver
import com.example.digishoes.common.priceFormat
import com.example.digishoes.data.Comment
import com.example.digishoes.databinding.ActivityProductDetailsBinding
import com.example.digishoes.product.comments.CommentList
import com.example.digishoes.service.ImageLoadingService
import com.example.digishoes.view.scroll.ObservableScrollViewCallbacks
import com.example.digishoes.view.scroll.ScrollState
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetails : DigiActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    val commentAdapter = CommentAdapter()
    val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        productDetailViewModel.productLiveData.observe(this) {
            binding.productNameTv.text = it.title
            imageLoadingService.load(binding.productIv, it.image)
            binding.productCurrentPriceTv.text = priceFormat(it.price, this)
            binding.productPreviousPriceTv.text = priceFormat(it.previous_price, this)
            binding.productPreviousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            binding.toolbarTitleTv.text = it.title
        }

        productDetailViewModel.progressBar.observe(this) {
            setProgressbarIndicator(it)
        }

        productDetailViewModel.commentLiveData.observe(this) {
            commentAdapter.comments = it as ArrayList<Comment>
            if (it.size > 3) {
                binding.viewAllComments.visibility = View.VISIBLE
                binding.viewAllComments.setOnClickListener {
                    startActivity(Intent(this, CommentList::class.java).apply {
                        putExtra(EXTRA_KEY_ID, productDetailViewModel.productLiveData.value!!.id)
                    })
                }
            }
        }

        initView()

        binding.addToCartBtnProductDetail.setOnClickListener {
            productDetailViewModel.onClickAddToCartBtn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DigiCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        Snackbar.make(
                            rootView as CoordinatorLayout,
                            "به سبد خرید اضافه شد",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                })
        }

        productDetailToolbar.setBackOnClickListener = View.OnClickListener {
            finish()
        }
    }

    fun initView() {
        binding.commentsRvDetails.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.commentsRvDetails.adapter = commentAdapter
        binding.commentsRvDetails.isNestedScrollingEnabled = true


        val productImage = binding.productIv
        productImage.post {
            val toolbar = binding.toolbarView
            val productImageHeight = binding.productIv.height

            binding.observableScrollView.addScrollViewCallbacks(object :
                ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    Timber.i("productIv height is -> $productImageHeight")
                    toolbar.alpha = scrollY.toFloat() / productImageHeight.toFloat()
                    productImage.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}