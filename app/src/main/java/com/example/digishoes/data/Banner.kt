package com.example.digishoes.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val id: Int,
    val image: String,
    val link_type: Int,
    val link_value: String
) : Parcelable

const val LATEST_SORT = 0
const val LATEST_POPULAR = 1
const val PRICE_SORT_DES = 2
const val PRICE_SORT_ASC = 3
