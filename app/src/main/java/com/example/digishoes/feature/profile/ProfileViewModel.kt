package com.example.digishoes.feature.profile

import com.example.digishoes.common.DigiViewModel
import com.example.digishoes.data.TokenContainer
import com.example.digishoes.data.repo.UserRepository
import com.example.digishoes.data.repo.shipping.OrderRepository

class ProfileViewModel(private val userRepository: UserRepository) : DigiViewModel() {

    val getUserName: String
        get() = userRepository.getUserName()

    val isSignIn: Boolean
        get() = TokenContainer.token != null

    fun signOut() = userRepository.signOut()
}