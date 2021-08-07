package com.example.digishoes.data.source

import com.example.digishoes.data.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners(): Single<List<Banner>>
}