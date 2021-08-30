package com.example.digishoes.data

import androidx.annotation.StringRes

data class EmptyState(
    var mostShow: Boolean,
    @StringRes var textEmptyState: Int = 0,
    var Cba: Boolean = false,
    var image: Boolean = false
)
