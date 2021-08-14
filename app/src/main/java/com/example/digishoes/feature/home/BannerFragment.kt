package com.example.digishoes.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.digishoes.R
import com.example.digishoes.common.EXTRA_KEY_DATA
import com.example.digishoes.data.Banner
import com.example.digishoes.service.ImageLoadingService
import com.example.digishoes.view.DigiImageView
import org.koin.android.ext.android.inject

class BannerFragment : Fragment() {
    val imageLoadingService: ImageLoadingService by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageView =
            inflater.inflate(R.layout.fragment_banner, container, false) as DigiImageView
        val banner = requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)
            ?: throw IllegalStateException("Banner Can't be Null")
        imageLoadingService.load(imageView, banner.image)
        return imageView
    }

    companion object{
        fun newInstance(banner : Banner) : BannerFragment {
            return BannerFragment().apply {
                arguments=Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA,banner)
                }
            }
        }
    }
}