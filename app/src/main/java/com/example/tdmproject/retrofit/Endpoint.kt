package com.example.tdmproject.retrofit

import com.example.tdmproject.entity.Pharmacy
import retrofit2.Call
import retrofit2.http.*


interface Endpoint {

    @GET("getlistpharmacies")
    fun getPharmacies():Call<List<Pharmacy>>

    @GET("getpharmacydetail/{id}")
    fun getDetailPharmacy(@Path("id") id:Int):Call<Pharmacy>


}
