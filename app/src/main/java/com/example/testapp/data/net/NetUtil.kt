package com.example.testapp.data.net

import android.util.Log
import com.example.testapp.BuildConfig
import com.example.testapp.data.exception.NetworkConnectionError
import com.example.testapp.data.platform.NetworkHandler
import com.example.testapp.domain.source.Result
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private val okHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
    if (BuildConfig.DEBUG)
        addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
}.build()

private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(GsonBuilder().create())

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.API_URL)
    .addConverterFactory(gsonConverterFactory)
    .client(okHttpClient)
    .build()

internal inline fun <T : Any> handleNetResponse(networkHandler: NetworkHandler = NetworkHandler.instance, response: () -> Result<T>): Result<T> {
    return if (networkHandler.isConnected) {
        return try {
            response()
        } catch (ste: SocketTimeoutException) {
            Result.Error(ste)
        } catch (uhe: UnknownHostException) {
            Result.Error(uhe)
        } catch (ie: IOException) {
            Result.Error(ie)
        } catch (e: Exception) {
            Log.e("NetworkHandler","Net response exception: ", e)
            Result.Error(e)
        }
    } else {
        Result.Error(NetworkConnectionError())
    }
}