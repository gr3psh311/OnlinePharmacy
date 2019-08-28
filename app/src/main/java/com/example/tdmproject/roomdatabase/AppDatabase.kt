package com.example.tdmproject.roomdatabase


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tdmproject.entity.Pharmacy
import com.example.tdmproject.roomdao.PharmacyDao

@Database(entities = arrayOf(Pharmacy::class),version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getPharmacyDao():PharmacyDao

}