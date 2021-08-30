package com.example.digishoes.feature.main

import androidx.lifecycle.ViewModel
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.DigiViewModel
import com.example.digishoes.data.CartItem
import com.example.digishoes.data.CartItemCount
import com.example.digishoes.data.TokenContainer
import com.example.digishoes.data.repo.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository) : DigiViewModel() {

    fun changeItemCountBadge() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            cartRepository.getItemCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object : DigiSingleObserver<CartItemCount>(compositeDisposable) {
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }
                })
        }
    }
}