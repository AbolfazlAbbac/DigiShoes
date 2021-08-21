package com.example.digishoes.data.source

import com.example.digishoes.data.MessageResponse
import com.example.digishoes.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(username: String, password: String): Single<TokenResponse>

    fun signup(username: String, password: String): Single<MessageResponse>

    fun loadToken()

    fun saveToken(token: String, refresh_token: String)
}