package com.example.digishoes.data.repo

import com.example.digishoes.data.Banner
import com.example.digishoes.data.source.BannerDataSource
import com.example.digishoes.data.source.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImp(val bannerRemoteDataSource: BannerDataSource) : BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}