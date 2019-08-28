package com.example.tdmproject


import android.app.Application
import com.example.tdmproject.roomdatabase.RoomService


class App: Application(){
    override fun onCreate() {
        super.onCreate()
        RoomService.context = applicationContext
    }
}