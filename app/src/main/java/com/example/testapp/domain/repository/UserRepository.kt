package com.example.testapp.domain.repository

import com.example.testapp.domain.model.User
import com.example.testapp.domain.model.UserList
import com.example.testapp.domain.source.MyResult

interface UserRepository {

    fun getUsers(page: Int): MyResult<UserList>

    fun getUser(id: Int): MyResult<User>

}