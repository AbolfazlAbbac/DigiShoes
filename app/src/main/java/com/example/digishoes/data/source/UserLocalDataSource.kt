package com.example.digishoes.data.source

import android.content.SharedPreferences
import com.example.digishoes.data.TokenContainer
import com.example.digishoes.data.TokenResponse
import io.reactivex.Single

const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"

class UserLocalDataSource(val sharedPreferences: SharedPreferences) : UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signup(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString(ACCESS_TOKEN, null),
            sharedPreferences.getString(REFRESH_TOKEN, null)
        )
    }

    override fun saveToken(token: String, refresh_token: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, token)
            putString(REFRESH_TOKEN, refresh_token)
        }.apply()
    }
}