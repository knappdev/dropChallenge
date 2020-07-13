package com.example.dropchallenge.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Singleton

@Singleton
class InternetHelper constructor(private val context: Context) {

    fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }
}