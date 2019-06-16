package com.example.testapp.presentation.user

import com.example.testapp.data.exception.NetworkConnectionError
import com.example.testapp.data.repository.UserRepositoryImpl
import com.example.testapp.domain.model.User
import com.example.testapp.domain.repository.UserRepository
import com.example.testapp.domain.source.MyError
import com.example.testapp.domain.source.MyResult
import com.example.testapp.domain.source.MySuccess
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UserPresenter(private var v: UserContract.View?, private val idUser: Int, private val userRepository: UserRepository = UserRepositoryImpl) : UserContract.Presenter, CoroutineScope {

    private val parentJob = Job()

    init {
        v?.presenter = this
    }

    override fun start() {
        loadUser()
    }

    override fun loadUser() {
        launch {
            v?.showLoading()
            withContext(Dispatchers.IO) { userRepository.getUser(idUser) }
                .updateUser()
            v?.hideLoading()
        }
    }

    override fun viewDestroyed(changingConfigurations: Boolean) {
        parentJob.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private fun MyResult<User>.updateUser() {
        when (this) {
            is MySuccess -> v?.showUser(this.data)
            is MyError -> when (this.exception) {
                is NetworkConnectionError -> v?.showNoInternetConnection()
                else -> v?.showSomeErrorOccurred()
            }
        }
    }

}