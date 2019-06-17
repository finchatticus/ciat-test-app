package com.example.testapp.presentation

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.example.testapp.data.db.AppDatabase
import com.example.testapp.data.platform.NetworkHandler
import com.example.testapp.data.repository.UserRepositoryImpl


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initRoom()
        initNetworkHandler()
        initLocalImagesStorage()
    }

    private fun initRoom() {
        AppDatabase.init(applicationContext)
    }

    private fun initNetworkHandler() {
        NetworkHandler.init(applicationContext)
    }

    private fun initLocalImagesStorage() {
        val cw = ContextWrapper(this)
        val imageDirectory = cw.getDir("images", Context.MODE_PRIVATE)
        UserRepositoryImpl.setImageDirectory(imageDirectory)
    }

}