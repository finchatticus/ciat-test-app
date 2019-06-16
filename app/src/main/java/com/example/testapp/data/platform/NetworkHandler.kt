package com.example.testapp.data.platform

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkHandler private constructor(private var context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: NetworkHandler
            private set

        fun init(context: Context) {
            instance = NetworkHandler(context)
        }
    }

    private val Context.networkInfo: NetworkInfo? get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

    val isConnected
        get() = context.networkInfo?.isConnected ?: false

}