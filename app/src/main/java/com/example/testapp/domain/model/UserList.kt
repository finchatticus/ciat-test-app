package com.example.testapp.domain.model

data class UserList(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPage: Int,
    val users: List<User>
)