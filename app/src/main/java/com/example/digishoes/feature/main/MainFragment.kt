package com.example.digishoes.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.digishoes.R
import com.example.digishoes.common.NikeFragment
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment() {
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
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
            val bannerSliderViewPager = view.findViewById<ViewPager2>(R.id.bannerSliderViewPager)
            bannerSliderViewPager.adapter = bannerSliderAdapter

        }
    }
}