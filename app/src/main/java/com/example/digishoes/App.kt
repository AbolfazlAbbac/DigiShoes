package com.example.digishoes

import android.app.Application
import android.os.Bundle
import com.example.digishoes.data.repo.BannerRepository
import com.example.digishoes.data.repo.BannerRepositoryImp
import com.example.digishoes.data.repo.ProductRepository
import com.example.digishoes.data.repo.ProductRepositoryImp
import com.example.digishoes.data.source.BannerDataSource
import com.example.digishoes.data.source.BannerRemoteDataSource
import com.example.digishoes.data.source.ProductLocalDataSource
import com.example.digishoes.data.source.ProductRemoteDataSource
import com.example.digishoes.feature.main.MainViewModel
import com.example.digishoes.feature.main.ProductAdapter
import com.example.digishoes.feature.main.ProductAdapterPopular
import com.example.digishoes.product.ProductDetailViewModel
import com.example.digishoes.service.FerscoImageLoadingServiceImpl
import com.example.digishoes.service.ImageLoadingService
import com.example.digishoes.service.http.getApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.scope.getViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)
        val myModule = module {
            single { getApiServiceInstance() }
            single<ImageLoadingService> { FerscoImageLoadingServiceImpl() }
            factory<ProductRepository> {
                ProductRepositoryImp(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            factory { ProductAdapterPopular(get()) }
            factory { ProductAdapter(get()) }
            factory<BannerRepository> { BannerRepositoryImp(BannerRemoteDataSource(get())) }
            viewModel { MainViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle) }
        }
        startKoin {
            androidContext(this@App)
            modules(myModule)
        }
    }
}