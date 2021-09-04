package com.example.digishoes.feature.history

import androidx.lifecycle.MutableLiveData
import com.example.digishoes.common.DigiSingleObserver
import com.example.digishoes.common.DigiViewModel
import com.example.digishoes.common.asyncNetwork
import com.example.digishoes.data.OrderHistoryItem
import com.example.digishoes.data.repo.shipping.OrderRepository

class OrderHistoryViewModel(val orderRepository: OrderRepository) : DigiViewModel() {
    val order = MutableLiveData<List<OrderHistoryItem>>()

    init {
        orderRepository.list()
            .asyncNetwork()
            .doFinally {
                progressBar.value = false
            }
            .subscribe(object : DigiSingleObserver<List<OrderHistoryItem>>(compositeDisposable) {
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    order.value = t
                }
            })
    }
}