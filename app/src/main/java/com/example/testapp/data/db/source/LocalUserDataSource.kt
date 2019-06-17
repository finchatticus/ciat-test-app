package com.example.testapp.data.db.source

import android.annotation.SuppressLint
import com.example.testapp.data.db.AppDatabase
import com.example.testapp.data.db.dto.UserDto
import com.example.testapp.data.db.mapper.UserDtoToUserMapper
import com.example.testapp.data.db.mapper.UserToUserDtoMapper
import com.example.testapp.domain.mapper.Mapper
import com.example.testapp.domain.model.User
import com.example.testapp.domain.model.UserList
import com.example.testapp.domain.source.MyResult
import com.example.testapp.domain.source.MySuccess
import com.example.testapp.presentation.util.PicassoSaveLocalTarget
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.io.File

class LocalUserDataSource {

    @SuppressLint("StaticFieldLeak")
    private val dao = AppDatabase.instance.userDao()
    private val userToUserDtoMapper = UserToUserDtoMapper()
    private val userDtoToUserMapper: Mapper<UserDto, User> = UserDtoToUserMapper()

    fun save(users: List<User>, imageDirectory: File?) {
        GlobalScope.launch(Dispatchers.IO) {
            imageDirectory?.run {
                users.forEach { user ->
                    delay(200)
                    withContext(Dispatchers.Main) {
                        Picasso.get()
                            .load(user.avatarPath)
                            .into(PicassoSaveLocalTarget(imageDirectory, user.id.toString()))
                    }
                }
            }
        }
        userToUserDtoMapper.setImageDirectory(imageDirectory)
        dao.insertAll(userToUserDtoMapper.map(users))
    }

    fun getUsersPerPage(page: Int, perPage: Int): MyResult<UserList> {
        val users = userDtoToUserMapper.map(dao.getPerPage(page))
        val total = dao.getRowCount()
        val userList = UserList(page, perPage, total, total / perPage, users)
        return MySuccess(userList)
    }

    fun getUser(id: Int): MyResult<User> {
        val userDTO = dao.getById(id)
        val user = userDtoToUserMapper.map(userDTO)
        return MySuccess(user)
    }

}