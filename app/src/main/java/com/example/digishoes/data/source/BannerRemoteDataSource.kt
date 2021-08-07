package com.example.digishoes.data.source

import com.example.digishoes.data.Banner
import com.example.digishoes.service.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService) : BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanner()
}