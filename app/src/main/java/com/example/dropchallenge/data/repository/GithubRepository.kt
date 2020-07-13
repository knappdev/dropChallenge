package com.example.dropchallenge.data.repository

import com.example.dropchallenge.data.remote.GithubApi
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(private val githubApi: GithubApi){

    fun getInputFile(): Single<ResponseBody> {
       return githubApi.get()
    }
}