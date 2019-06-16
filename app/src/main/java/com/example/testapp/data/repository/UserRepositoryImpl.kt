package com.example.testapp.data.repository

import android.annotation.SuppressLint
import com.example.testapp.data.net.source.RemoteUserDataSource
import com.example.testapp.data.platform.NetworkHandler
import com.example.testapp.domain.repository.UserRepository

object UserRepositoryImpl : UserRepository {

    @SuppressLint("StaticFieldLeak")
    private val networkHandler = NetworkHandler.instance
    private val remoteUserDataSource = RemoteUserDataSource()

    override fun getUsers(page: Int) = when {
        networkHandler.isConnected -> remoteUserDataSource.getUsers(page)
        else -> remoteUserDataSource.getUsers(page)
    }

    override fun getUser(id: Int) = when {
        networkHandler.isConnected -> remoteUserDataSource.getUser(id)
        else -> remoteUserDataSource.getUser(id)
    }

}