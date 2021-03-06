package com.example.digishoes.data.source

import com.example.digishoes.data.MessageResponse
import com.example.digishoes.service.http.ApiService
import com.google.gson.JsonObject
import com.example.digishoes.data.TokenResponse
import io.reactivex.Single

const val CLIENT_ID = 2
const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

class UserRemoteDataSource(val apiService: ApiService) : UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        return apiService.login(JsonObject().apply {
            addProperty("username", username)
            addProperty("password", password)
            addProperty("grant_type", "password")
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        })
    }

    override fun signup(username: String, password: String): Single<MessageResponse> {
        return apiService.signup(JsonObject().apply {
            addProperty("email",username)
            addProperty("password",password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refresh_token: String) {
        TODO("Not yet implemented")
    }

    override fun signOut() {

    }

    override fun getUserName(): String {
        TODO("Not yet implemented")
    }

    override fun saveUsername(username: String) {
        TODO("Not yet implemented")
    }


}