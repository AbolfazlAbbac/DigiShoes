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
            onSuccess(username, it)
        }.ignoreElement()
    }

    override fun signup(username: String, password: String): Completable {
        return userRemoteDataSource.signup(username, password).flatMap {
            userRemoteDataSource.login(username, password)
        }.doOnSuccess {
            onSuccess(username, it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null, null)
    }

    override fun getUserName(): String = userLocalDataSource.getUserName()


    private fun onSuccess(username: String, tokenResponse: TokenResponse) {
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        userLocalDataSource.saveUsername(username)
    }
}