package com.example.tdmproject.roomdao


import androidx.room.*
import com.example.tdmproject.entity.Pharmacy

@Dao
interface PharmacyDao {

    @Query("select * from pharmacies")
    fun getPharmacies():List<Pharmacy>

    @Query("select * from pharmacies where pharmacy_id=:idPharmacy")
    fun getPharmacyById(idPharmacy:Int):Pharmacy

    @Insert
    fun addPharmacies(pharmacies:List<Pharmacy>)

    @Update
    fun updatePharmacy(pharmacy: Pharmacy)

}