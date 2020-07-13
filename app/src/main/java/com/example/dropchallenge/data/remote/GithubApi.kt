package com.example.dropchallenge.data.remote

import io.reactivex.Single
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET

interface GithubApi {

    @GET("LuigiPapinoDrop/d8ed153d5431bbad23e1e1c6b5ba1e3c/raw/4ec1c8064e51838240e941679d3ac063460685c2/code_challenge_richer.txt")
    fun get(): Single<ResponseBody>
}