package com.example.testapp.presentation.users

import com.example.testapp.data.repository.UserRepositoryImpl
import com.example.testapp.domain.repository.UserRepository
import com.example.testapp.domain.source.MyError
import com.example.testapp.domain.source.MySuccess
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UsersPresenter(private var v: UsersContract.View?, private val userRepository: UserRepository = UserRepositoryImpl) : UsersContract.Presenter, CoroutineScope {

    private val parentJob = Job()
    private var currentPage: Int = 0
    private var totalPages: Int = 1

    init {
        v?.presenter = this
    }

    override fun start() {

    }

    override fun loadNextUsers() {
        launch {
            if (currentPage < totalPages) {
                v?.showLoading()
                val result = withContext(Dispatchers.IO) { userRepository.getUsers(++currentPage) }
                when (result) {
                    is MySuccess -> {
                        v?.showUsers(result.data.users)
                        totalPages = result.data.totalPage
                    }
                    is MyError -> v?.showDebugMessage(result.exception.localizedMessage)
                }
                v?.hideLoading()
            }
        }
    }

    override fun openUserScreen(idUser: Int) {
        v?.showUserScreen(idUser)
    }

    override fun viewDetached(changingConfigurations: Boolean) {
        parentJob.cancel()
        v?.presenter = null
        v = null
    }

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

}