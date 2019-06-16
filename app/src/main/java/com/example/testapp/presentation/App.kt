package com.example.testapp.presentation

import android.app.Application
import com.example.testapp.data.platform.NetworkHandler

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initNetworkHandler()
    }

    private fun initNetworkHandler() {
        NetworkHandler.init(applicationContext)
    }

}