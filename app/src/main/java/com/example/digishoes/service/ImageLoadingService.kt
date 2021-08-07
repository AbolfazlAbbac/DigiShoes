package com.example.digishoes.service

import android.widget.ImageView
import com.example.digishoes.view.DigiImageView

interface ImageLoadingService {
    fun load(imageView: DigiImageView, imageUrl: String)
}