package com.example.testapp.presentation.users

import com.example.testapp.data.repository.UserRepositoryImpl
import com.example.testapp.domain.repository.UserRepository
import com.example.testapp.domain.source.MyError
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
                val result = withContext(Dispatchers.IO) { userRepository.getUsers(nextPage) }
                when (result) {
                    is MySuccess -> {
                        totalPages = result.data.totalPage
                        nextPage++
                        v?.showUsers(result.data.users)
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