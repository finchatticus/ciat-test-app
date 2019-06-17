package com.example.testapp.data.repository

import android.annotation.SuppressLint
import com.example.testapp.data.db.source.LocalUserDataSource
import com.example.testapp.data.net.source.RemoteUserDataSource
import com.example.testapp.data.platform.NetworkHandler
import com.example.testapp.domain.repository.UserRepository
import com.example.testapp.domain.source.MySuccess
import java.io.File

private const val DEFAULT_PER_PAGE = 3

object UserRepositoryImpl : UserRepository {

    @SuppressLint("StaticFieldLeak")
    private val networkHandler = NetworkHandler.instance
    private val remoteUserDS = RemoteUserDataSource()
    private val localUserDS = LocalUserDataSource()
    private var imageDirectory: File? = null

    override fun getUsers(page: Int) = when {
        networkHandler.isConnected -> {
            val userResult = remoteUserDS.getUsers(page)
            if (userResult is MySuccess) {
                localUserDS.save(userResult.data.users, imageDirectory)
            }
            userResult
        }
        else -> localUserDS.getUsersPerPage(page, DEFAULT_PER_PAGE)
    }

    override fun getUser(id: Int) = when {
        networkHandler.isConnected -> remoteUserDS.getUser(id)
        else -> localUserDS.getUser(id)
    }

    fun setImageDirectory(imageDirectory: File?) {
        this.imageDirectory = imageDirectory
    }

}