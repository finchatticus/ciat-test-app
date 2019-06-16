package com.example.testapp.data.net.mapper

import com.example.testapp.data.net.dto.UserResponse
import com.example.testapp.domain.mapper.Mapper
import com.example.testapp.domain.model.User

class UserResponseToUserMapper : Mapper<UserResponse, User> {

    override fun map(from: UserResponse) =  when {
        from.id == null
                || from.email == null
                || from.first_name == null
                || from.last_name == null
                || from.avatar == null ->
            throw NullPointerException("Some field in UserResponse is null")
        else ->
            User(from.id, from.email, from.first_name, from.last_name, from.avatar)
    }

}