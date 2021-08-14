package com.example.digishoes.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.common.NikeFragment
import com.example.digishoes.data.LATEST_POPULAR
import com.example.digishoes.data.LATEST_SORT
import com.example.digishoes.data.Product
import com.example.digishoes.databinding.FragmentMainBinding
import com.example.digishoes.feature.common.ProductAdapter
import com.example.digishoes.feature.common.ProductAdapterPopular
import com.example.digishoes.feature.common.VIEW_TYPE_ROUNDED
import com.example.digishoes.feature.list.ProductList
import com.example.digishoes.product.ProductDetails
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class HomeFragment : NikeFragment(), ProductAdapter.onClickListener,
    ProductAdapterPopular.onClickListener {
    val homeViewModel: HomeViewModel by viewModel()
    val productAdapter: ProductAdapter by inject { parametersOf(VIEW_TYPE_ROUNDED) }
    val productAdapterPopular: ProductAdapterPopular by inject()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productLatestRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.productLatestRv.adapter = productAdapter

        binding.productPopularRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.productPopularRv.adapter = productAdapterPopular

        homeViewModel.productLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productAdapter.products = it as ArrayList<Product>
        }
        productAdapter.productClickListener = this
        productAdapterPopular.productClickListener = this

        homeViewModel.productLiveData_Popular.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productAdapterPopular.products = it as ArrayList<Product>
        }

        homeViewModel.progressBar.observe(viewLifecycleOwner) {
            setProgressbarIndicator(it)
        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            binding.bannerSliderViewPager.adapter = bannerSliderAdapter
            binding.dotsIndicator.setViewPager2(binding.bannerSliderViewPager)
        }

        binding.viewAllLatestBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductList::class.java).apply {
                putExtra(EXTRA_KEY_DATA, LATEST_SORT)
            })
        }

        binding.viewAllPopularBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductList::class.java).apply {
                putExtra(EXTRA_KEY_DATA, LATEST_POPULAR)
            })
        }
    }

    override fun productOnClickListener(product: Product) {
        startActivity(Intent(requireContext(), ProductDetails::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}