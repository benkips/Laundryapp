package com.mamafua.app.Repo
import com.mamafua.app.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    companion object{
        const val BASE_URL="http://mamafua.howtoinkenya.co.ke/mobile/"
    }
    //login to accounts
    @POST("login")
    @FormUrlEncoded
    suspend  fun loginclients(@Field("email") email: String?,
                              @Field("pass") password: String?): Users

    //Register to accounts
    @POST("registration")
    @FormUrlEncoded
    suspend  fun Registerclients(@Field("email") email: String?,
                                 @Field("pass") password: String?,
                                 @Field("name") name: String?,
                                 @Field("phone") phoneno: String?,
                                 @Field("location") location: String?): Returnstatus


    //locations
    @POST("retrivelocation")
    @FormUrlEncoded
    suspend  fun getlocations(@Field("locs") locs: String?): Locationdata

    //Getting categories
    @GET("getcategories")
    suspend  fun getcategoriez(): Categorydata

    //Getting services for categories
    @POST("getservices")
    @FormUrlEncoded
    suspend  fun getserviceforcategories(@Field("pg") pag: Int?,
                                 @Field("count") count: Int?,
                                 @Field("srch") search: String?,
                                 @Field("cid") cid: String?):Serviceofcategories

    //send json body order
    @POST("retrivelocation")
    suspend fun sendorder(@Body endorder : Endorder): Returnstatus



}