package com.example.testapp.data.net.mapper

import com.example.testapp.data.net.dto.BaseResponse
import com.example.testapp.data.net.dto.UserResponse
import com.example.testapp.domain.mapper.Mapper
import com.example.testapp.domain.model.User

class BaseUserResponseToUserMapper(private val userResponseToUserMapper: Mapper<UserResponse, User>) : Mapper<BaseResponse<UserResponse>, User> {

    override fun map(from: BaseResponse<UserResponse>) = when (val data = from.data) {
        null -> throw NullPointerException("Field \"data\" in BaseResponse is null")
        else -> userResponseToUserMapper.map(data)
    }

}