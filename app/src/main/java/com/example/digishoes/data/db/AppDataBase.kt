package com.example.digishoes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.digishoes.data.Product
import com.example.digishoes.data.source.ProductLocalDataSource

@Database(entities = [Product::class], exportSchema = false, version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun productDao(): ProductLocalDataSource
}