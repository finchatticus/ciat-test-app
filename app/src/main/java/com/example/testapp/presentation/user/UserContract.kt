package com.example.testapp.presentation.user

import com.example.testapp.domain.model.User
import com.example.testapp.presentation.base.BasePresenter
import com.example.testapp.presentation.base.BaseView

interface UserContract {

    interface View : BaseView<Presenter> {

        fun showLoading()

        fun hideLoading()

        fun showUser(user: User)

        fun showNoInternetConnection()

        fun showSomeErrorOccurred()

    }

    interface Presenter : BasePresenter {

        fun loadUser()

    }

}