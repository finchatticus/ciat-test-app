package com.example.testapp.data.net.mapper

import com.example.testapp.data.net.dto.BasePaginationListResponse
import com.example.testapp.data.net.dto.UserResponse
import com.example.testapp.domain.mapper.Mapper
import com.example.testapp.domain.model.User
import com.example.testapp.domain.model.UserList

class UsersResponseToUserListMapper(private val userMapper: Mapper<UserResponse, User>) : Mapper<BasePaginationListResponse<UserResponse>, UserList> {

    override fun map(from: BasePaginationListResponse<UserResponse>): UserList {
        val data = from.data
        val page = from.page
        val perPage = from.per_page
        val total = from.total
        val totalPages = from.total_pages
        return when {
            data == null
                    || page == null
                    || perPage == null
                    || total == null
                    || totalPages == null ->
                throw NullPointerException("Some field in UsersResponse is null")
            else -> UserList(page, perPage, total, totalPages, userMapper.map(data))
        }
    }

}