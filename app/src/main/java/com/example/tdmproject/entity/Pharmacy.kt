package com.example.tdmproject.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pharmacies")
data class Pharmacy(
    @PrimaryKey
    @ColumnInfo(name="pharmacy_id")
    var idPharmacy:Int,
//    @ColumnInfo(name="details")
    var ville:String?="",
    var address:String?="",
    var timing:String?="",
    var phone_num:String?="",
    var caisse_convention:String?="",
    var facebook:String?="",
    var localisation:String?=""):Serializable
