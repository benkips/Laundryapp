package com.mamafua.app.Databasestuff

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cartitems:List<Washitems>)

    @Query("SELECT * FROM cartstuff")
    suspend fun getCartdata(): List<Washitems>


    @Query("DELETE  FROM cartstuff WHERE sid =:cid")
    suspend fun DeleteCartBycId(cid: Int)

    @Query("UPDATE cartstuff SET  quantity =:qnty WHERE sid =:cid")
    suspend fun UpdateCartBycId(qnty: Int,cid: Int)
}