package com.example.testapp.data.net.api

import com.example.testapp.data.net.dto.BasePaginationListResponse
import com.example.testapp.data.net.dto.BaseResponse
import com.example.testapp.data.net.dto.UserResponse
import com.example.testapp.data.net.retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

    companion object {
        val instance: UserApiService = retrofit.create(UserApiService::class.java)
    }

    @GET("/api/users")
    fun getUsers(@Query("page") page: Int): Call<BasePaginationListResponse<UserResponse>>

    @GET("/api/users/{id}")
    fun getUser(@Path("id") id: Int): Call<BaseResponse<UserResponse>>

}