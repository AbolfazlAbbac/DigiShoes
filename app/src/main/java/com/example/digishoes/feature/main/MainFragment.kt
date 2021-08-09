package com.example.digishoes.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.digishoes.R
import com.example.digishoes.common.NikeFragment
import com.example.digishoes.data.Product
import com.example.digishoes.databinding.ActivityMainBinding.inflate
import com.example.digishoes.databinding.FragmentCartBinding
import com.example.digishoes.databinding.FragmentMainBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainFragment : NikeFragment() {
    val mainViewModel: MainViewModel by viewModel()
    val productAdapter: ProductAdapter by inject()
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

        mainViewModel.productLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.productLiveData_Popular.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productAdapterPopular.products = it as ArrayList<Product>
        }

        mainViewModel.progressBar.observe(viewLifecycleOwner) {
            setProgressbarIndicator(it)
        }

        mainViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            binding.bannerSliderViewPager.adapter = bannerSliderAdapter
            binding.dotsIndicator.setViewPager2(binding.bannerSliderViewPager)
        }
    }
}