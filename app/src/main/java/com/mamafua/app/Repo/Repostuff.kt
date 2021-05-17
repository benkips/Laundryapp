package com.mamafua.app.Repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import androidx.room.withTransaction
import com.mamafua.app.Databasestuff.CartDatabase
import com.mamafua.app.Databasestuff.Washitems
import com.mamafua.app.models.Endorder
import com.mamafua.app.paging.Servicedatasource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repostuff @Inject constructor(
    private val apiInterface: ApiInterface,
) : Baserepository() {

//    suspend fun getSearchresults(query: String) =
//        safeApiCall{apiInterface.searchresults(query)}
//
//    suspend fun  getteachingfolders()= safeApiCall {
//        apiInterface.getfolders("teachings") }
//
//    suspend fun  getteachingpdfs()= safeApiCall {
//        apiInterface.getfolders("magazines") }

    //loginclient
    suspend fun login(email: String, pass: String) = safeApiCall {
        apiInterface.loginclients(email, pass)
    }

    //register client
    suspend fun register(
        email: String,
        pass: String,
        name: String,
        phone: String,
        location: String
    ) = safeApiCall {
        apiInterface.Registerclients(email, pass, name, phone, location)
    }

    //retrivelocations
    suspend fun locations(loco: String) = safeApiCall {
        apiInterface.getlocations(loco)
    }

    //retrive categorys
    suspend fun getcategorys() = safeApiCall {
        apiInterface.getcategoriez()
    }
    //sendorder to server
    suspend fun sendordertoserver(snd: Endorder)=safeApiCall {
        apiInterface.sendorder(snd)
    }

    //retrive services for categories
    fun getSearchresults(query: String, cid: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { Servicedatasource(apiInterface, query, cid) }
        ).liveData





}