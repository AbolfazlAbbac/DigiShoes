package com.example.digishoes.data.source

import com.sevenlearn.nikestore.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(username: String, password: String): Single<TokenResponse>

    fun signup(username: String, password: String): Single<TokenResponse>

    fun loadToken()

    fun saveToken(token: String, refresh_token: String)
}