package com.example.digishoes.data.repo

import io.reactivex.Completable

interface UserRepository {

    fun login(username: String, password: String): Completable

    fun signup(username: String, password: String): Completable

    fun loadToken()

    fun signOut()

    fun getUserName(): String


}