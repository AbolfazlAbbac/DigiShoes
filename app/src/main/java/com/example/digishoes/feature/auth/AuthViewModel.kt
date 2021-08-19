package com.example.digishoes.feature.auth

import com.example.digishoes.common.DigiViewModel
import com.example.digishoes.data.repo.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository) : DigiViewModel() {

    fun login(username: String, password: String): Completable {
        progressBar.value = true
        return userRepository.login(username, password)
            .doFinally {
                progressBar.postValue(false)
            }
    }

    fun signUp(username: String, password: String): Completable {
        progressBar.value = true
        return userRepository.signup(username, password)
            .doFinally {
                progressBar.postValue(false)
            }
    }
}