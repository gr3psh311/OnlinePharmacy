package com.example.tdmproject.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name="nss")
    var nss:Int,
    var name:String?="",
    var firstname:String?="",
    var address:String?="",
    var phone_num:String?="",
    var password:String?=""):Serializable
