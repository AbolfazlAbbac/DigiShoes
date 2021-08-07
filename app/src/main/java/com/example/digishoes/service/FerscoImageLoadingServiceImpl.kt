package com.example.digishoes.service

import com.example.digishoes.view.DigiImageView
import com.facebook.drawee.view.SimpleDraweeView

class FerscoImageLoadingServiceImpl : ImageLoadingService {
    override fun load(imageView: DigiImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw IllegalArgumentException("Image Must be Instance of SimpleDraweeView")
    }
}