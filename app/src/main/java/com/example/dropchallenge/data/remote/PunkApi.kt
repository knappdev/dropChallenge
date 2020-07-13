package com.example.dropchallenge.data.remote

import com.example.dropchallenge.data.model.Beer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkApi {

    @GET("beers")
    fun getBeerList(@Query("page") page:Int): Single<List<Beer>>

    @GET("beers/{id}")
    fun getBeer(@Path("id")id:Int): Single<List<Beer>>

    @GET("beers")
    fun getBeerListByIds(@Query("ids") joinToString: String): Single<List<Beer>>

}