package com.example.tdmproject.retrofit

import com.example.tdmproject.entity.Command
import com.example.tdmproject.entity.Pharmacy
import com.example.tdmproject.entity.User
import okhttp3.MultipartBody
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

    @Multipart
    @POST("uploadPicture")
    fun uploadPicture(@Part multipartBody:MultipartBody.Part):Call<String>
    //fun uploadPicture(@Path("multipartBody") multipartBody:MultipartBody.Part):Call<String>

    @POST("addCommand")
    fun addCommand(@Body command: Command):Call<String>

    @GET("getlistcommands/{nss}")
    fun getCommands(@Path("nss") nss:Int):Call<List<Command>>

}
