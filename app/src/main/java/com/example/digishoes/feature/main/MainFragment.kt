package com.example.digishoes.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.viewpager2.widget.ViewPager2
import com.example.digishoes.R
import com.example.digishoes.common.NikeFragment
import com.example.digishoes.databinding.ActivityMainBinding.inflate
import com.example.digishoes.databinding.FragmentCartBinding
import com.example.digishoes.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainFragment : NikeFragment() {
    val mainViewModel: MainViewModel by viewModel()
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
        mainViewModel.productLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
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