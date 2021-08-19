package com.example.digishoes.data

import timber.log.Timber

object TokenContainer {
    var token: String? = null
        private set
    var refresh_token: String? = null
        private set

    fun update(token: String?, refresh_token: String?) {
        Timber.i(
            "Token login -> ${
                token?.substring(
                    0,
                    10
                )
            } and refresh_token -> ${refresh_token?.substring(0, 10)}"
        )

        this.token = token
        this.refresh_token = refresh_token
    }
}