package com.example.digishoes.common

import androidx.annotation.StringRes

class DigiException(
    val type: Type,
    @StringRes val userFriendlyMessage: Int = 0,
    val serverMessage: String? = null
) : Throwable() {


    enum class Type {
        SIMPLE, AUTH
    }
}