package com.mamafua.app.Repo

import androidx.room.withTransaction
import com.mamafua.app.Databasestuff.CartDatabase
import com.mamafua.app.Databasestuff.Washitems
import com.mamafua.app.models.Endorder
import javax.inject.Inject

class CartRepo @Inject constructor(
    private val db: CartDatabase
)  {
    private  val cartDao=db.cartDao()

    //adding to cart
    suspend fun addtocart(cartstuff:List<Washitems>)=
        db.withTransaction {
            cartDao.insertCart(cartstuff)
        }
    //getting from cart
    suspend fun getcartstuff():List<Washitems> =
        cartDao.getCartdata()
    //delete from cart
    suspend fun deletecartstuff(id:Int)=
        cartDao.DeleteCartBycId(id)
    //update
    suspend fun updatecartstuff(qnty: Int,id:Int)=
        cartDao.UpdateCartBycId(qnty,id)



}