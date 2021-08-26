package com.example.digishoes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
	val image: String,
	val price: Int,
	val discount: Int,
	val id: Int,
	val title: String,
	val previous_price: Int,
	val status: Int
) : Parcelable

