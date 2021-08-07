package com.example.digishoes.data.repo

import com.example.digishoes.data.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanners(): Single<List<Banner>>
}