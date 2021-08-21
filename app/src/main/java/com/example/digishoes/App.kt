package com.example.digishoes

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.example.digishoes.data.repo.*
import com.example.digishoes.data.source.*
import com.example.digishoes.feature.auth.AuthViewModel
import com.example.digishoes.feature.cart.CartViewModel
import com.example.digishoes.feature.home.HomeViewModel
import com.example.digishoes.feature.common.ProductAdapter
import com.example.digishoes.feature.common.ProductAdapterPopular
import com.example.digishoes.feature.list.ProductListViewModel
import com.example.digishoes.product.ProductDetailViewModel
import com.example.digishoes.product.comments.CommentsViewModel
import com.example.digishoes.service.FerscoImageLoadingServiceImpl
import com.example.digishoes.service.ImageLoadingService
import com.example.digishoes.service.http.getApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
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


            single<SharedPreferences> { this@App.getSharedPreferences("app_setting", MODE_PRIVATE) }
            single { UserLocalDataSource(get()) }
            single<UserRepository> {
                UserRepositoryImp(
                    UserRemoteDataSource(get()),
                    UserLocalDataSource(get())
                )
            }
            factory { ProductAdapterPopular(get(), androidContext()) }
            factory { (viewType: Int) -> ProductAdapter(viewType, get(), androidContext()) }
            factory<CommentRepository> { CommentRepositoryImp(CommentRemoteDataSource(get())) }
            factory<BannerRepository> { BannerRepositoryImp(BannerRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImp(CartRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentsViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
        }
        startKoin {
            androidContext(this@App)
            modules(myModule)


            val userRepository: UserRepository = get()

            userRepository.loadToken()
        }
    }
}