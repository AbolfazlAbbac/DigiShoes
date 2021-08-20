package com.example.digishoes.data.repo

import com.example.digishoes.data.TokenContainer
import com.example.digishoes.data.source.UserDataSource
import com.example.digishoes.data.TokenResponse
import io.reactivex.Completable

class UserRepositoryImp(
    val userRemoteDataSource: UserDataSource,
    val userLocalDataSource: UserDataSource
) : UserRepository {

    override fun login(username: String, password: String): Completable {
        return userRemoteDataSource.login(username, password).doOnSuccess {
            onSuccess(it)
        }.ignoreElement()
    }

    override fun signup(username: String, password: String): Completable {
        return userRemoteDataSource.signup(username, password).flatMap {
            userRemoteDataSource.login(username, password)
        }.doOnSuccess {
            onSuccess(it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    private fun onSuccess(tokenResponse: TokenResponse) {
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
    }
}