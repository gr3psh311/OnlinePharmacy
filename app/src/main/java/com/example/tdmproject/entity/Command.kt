package com.example.tdmproject.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "commands")
data class Command(
    @PrimaryKey
    @ColumnInfo(name="cmd_id")
    var cmd_id:Int,
    var path:String,
    var user_id:Int,
    var pharmacy_address:String,
    var etat:String,
    var date:String):Serializable
