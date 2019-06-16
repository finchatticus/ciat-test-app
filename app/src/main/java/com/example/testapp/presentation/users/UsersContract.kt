package com.example.testapp.presentation.users

import com.example.testapp.domain.model.User
import com.example.testapp.presentation.base.BasePresenter
import com.example.testapp.presentation.base.BaseView

interface UsersContract {

    interface View : BaseView<Presenter> {

        fun showLoading()

        fun hideLoading()

        fun showUsers(items: List<User>)

        fun showNoInternetConnection()

        fun showSomeErrorOccured()

        fun showUserScreen(idUser: Int)
    }

    interface Presenter : BasePresenter {

        fun loadNextUsers()

        fun openUserScreen(idUser: Int)

    }

}