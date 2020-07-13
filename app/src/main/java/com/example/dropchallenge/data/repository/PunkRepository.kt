package com.example.dropchallenge.data.repository

import com.example.dropchallenge.data.model.Beer
import com.example.dropchallenge.data.remote.PunkApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PunkRepository @Inject constructor(private val punkApi: PunkApi) {

    fun getBeer(id: Int): Single<List<Beer>> {
        return punkApi.getBeer(id)
    }

    fun getBeerList(page:Int): Single<List<Beer>> {
        return punkApi.getBeerList(page)
    }

    fun getBeerListByIds(ids:List<String>):Single<List<Beer>>{
        return punkApi.getBeerListByIds(ids.joinToString { "|" })
    }
}