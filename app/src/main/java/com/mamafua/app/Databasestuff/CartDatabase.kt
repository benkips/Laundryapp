package com.mamafua.app.Databasestuff
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Washitems::class],version=1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}