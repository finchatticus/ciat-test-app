package com.example.testapp.presentation.users

import com.example.testapp.data.exception.NetworkConnectionError
import com.example.testapp.data.repository.UserRepositoryImpl
import com.example.testapp.domain.model.UserList
import com.example.testapp.domain.repository.UserRepository
import com.example.testapp.domain.source.MyError
import com.example.testapp.domain.source.MyResult
import com.example.testapp.domain.source.MySuccess
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UsersPresenter(private var v: UsersContract.View?, private val userRepository: UserRepository = UserRepositoryImpl) : UsersContract.Presenter, CoroutineScope {

    private val parentJob = Job()
    private var nextPage: Int = 1
    private var totalPages: Int = 0

    init {
        v?.presenter = this
    }

    override fun start() {
    }

    override fun loadNextUsers() {
        launch {
            if (nextPage <= totalPages || totalPages == 0) {
                v?.showLoading()
                withContext(Dispatchers.IO) { userRepository.getUsers(nextPage) }
                    .updateUsers()
                v?.hideLoading()
            }
        }
    }

    override fun openUserScreen(idUser: Int) {
        v?.showUserScreen(idUser)
    }

    override fun viewDestroyed(changingConfigurations: Boolean) {
        parentJob.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private fun MyResult<UserList>.updateUsers() {
        when (this) {
            is MySuccess -> {
                totalPages = this.data.totalPage
                nextPage++
                v?.showUsers(this.data.users)
            }
            is MyError -> when (this.exception) {
                is NetworkConnectionError -> v?.showNoInternetConnection()
                is NullPointerException -> v?.showNoInternetConnection()
                else -> v?.showSomeErrorOccurred()
            }
        }
    }

}