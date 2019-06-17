package com.example.testapp.data.db.mapper

import com.example.testapp.data.db.dto.UserDto
import com.example.testapp.domain.mapper.Mapper
import com.example.testapp.domain.model.User

class UserDtoToUserMapper : Mapper<UserDto, User> {

    override fun map(from: UserDto)
            = User(from.id, from.email, from.firstName, from.lastName, from.avatarPath)

}