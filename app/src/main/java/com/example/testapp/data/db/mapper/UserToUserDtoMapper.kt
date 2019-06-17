package com.example.testapp.data.db.mapper

import com.example.testapp.data.db.dto.UserDto
import com.example.testapp.domain.mapper.Mapper
import com.example.testapp.domain.model.User
import java.io.File

class UserToUserDtoMapper : Mapper<User, UserDto> {

    private var imageDirectory: File? = null

    override fun map(from: User)
            = UserDto(from.id, from.email, from.firstName, from.lastName, if (imageDirectory == null) "" else "file://${imageDirectory?.absolutePath}/${from.id}")

    fun setImageDirectory(imageDirectory: File?) {
        this.imageDirectory = imageDirectory
    }

}