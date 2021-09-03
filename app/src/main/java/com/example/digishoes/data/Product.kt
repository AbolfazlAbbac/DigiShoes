package com.example.digishoes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "products")
@Parcelize
data class Product(
    val image: String,
    val price: Int,
    val discount: Int,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val previous_price: Int,
    val status: Int
) : Parcelable {
    var isFavorite: Boolean = false
}

