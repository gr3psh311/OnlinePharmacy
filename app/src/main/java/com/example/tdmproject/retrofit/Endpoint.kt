package com.example.tdmproject.retrofit

import com.example.tdmproject.entity.Pharmacy
import com.example.tdmproject.entity.User
import retrofit2.Call
import retrofit2.http.*


interface Endpoint {

    @GET("getlistpharmacies")
    fun getPharmacies():Call<List<Pharmacy>>

    @GET("getpharmacydetail/{id}")
    fun getDetailPharmacy(@Path("id") id:Int):Call<Pharmacy>


    @POST("adduser")
    fun adduser(@Body user: User):Call<String>

    @GET("authuser/{phone}/{password}")
    fun authuser(@Path("phone") phone: String, @Path("password") password: String):Call<User>

}
