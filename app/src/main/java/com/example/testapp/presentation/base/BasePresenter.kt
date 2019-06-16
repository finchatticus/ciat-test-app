package com.example.testapp.presentation.base

interface BasePresenter {

    fun start()

    fun viewDetached(changingConfigurations: Boolean)

}