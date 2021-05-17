package com.mamafua.app.Databasestuff

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartstuff")
data class Washitems(
    @PrimaryKey val sid: Int,
    val service: String,
    val price: Int,
    val images: String,
    val quantity: Int

)