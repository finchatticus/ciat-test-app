package com.example.testapp.data.net.source

import com.example.testapp.data.exception.ServerError
import com.example.testapp.data.net.api.UserApiService
import com.example.testapp.data.net.dto.BasePaginationListResponse
import com.example.testapp.data.net.dto.BaseResponse
import com.example.testapp.data.net.dto.UserResponse
import com.example.testapp.data.net.handleNetResponse
import com.example.testapp.data.net.mapper.BaseUserResponseToUserMapper
import com.example.testapp.data.net.mapper.UserResponseToUserMapper
import com.example.testapp.data.net.mapper.UsersResponseToUserListMapper
import com.example.testapp.domain.mapper.Mapper
import com.example.testapp.domain.model.User
import com.example.testapp.domain.model.UserList
import com.example.testapp.domain.source.MyError
import com.example.testapp.domain.source.MySuccess
import com.example.testapp.domain.source.Result
import retrofit2.Response

class RemoteUserDataSource {

    private val api: UserApiService = UserApiService.instance
    private val userMapper: Mapper<UserResponse, User> = UserResponseToUserMapper()
    private val baseUserMapper: Mapper<BaseResponse<UserResponse>, User> = BaseUserResponseToUserMapper(userMapper)
    private val usersMapper: Mapper<BasePaginationListResponse<UserResponse>, UserList> = UsersResponseToUserListMapper(userMapper)

    fun getUsers(page: Int) = handleNetResponse {
        handleUsersResponse(api.getUsers(page).execute())
    }

    fun getUser(id: Int) = handleNetResponse {
        handleUserResponse(api.getUser(id).execute())
    }

    private fun handleUsersResponse(response: Response<BasePaginationListResponse<UserResponse>>): Result<UserList> {
        val usersResponse = response.body()
        return when {
            response.isSuccessful && usersResponse != null -> MySuccess(usersMapper.map(usersResponse))
            else -> MyError(ServerError("Get users some error occurred"))
        }
    }

    private fun handleUserResponse(response: Response<BaseResponse<UserResponse>>): Result<User> {
        val userResponse = response.body()
        return when {
            response.isSuccessful && userResponse != null -> MySuccess(baseUserMapper.map(userResponse))
            else -> MyError(ServerError("Get user some error occurred"))
        }
    }

}