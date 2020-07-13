package com.example.dropchallenge.ui.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dropchallenge.data.model.Beer
import com.example.dropchallenge.data.repository.PunkRepository
import com.example.dropchallenge.ui.base.BaseViewModel
import com.example.dropchallenge.ui.list.view.BeerListItemEntity
import com.example.dropchallenge.ui.main.model.BeerTypeName
import com.example.dropchallenge.utils.InternetHelper
import com.example.dropchallenge.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class BeerListViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    internetHelper: InternetHelper,
    private val punkRepository: PunkRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, internetHelper) {

    private val beerCellListMLD = MutableLiveData<List<BeerListItemEntity>>()
    val beerCellListLD: LiveData<List<BeerListItemEntity>> = beerCellListMLD


    private val loadingMLD: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLD: LiveData<Boolean> = loadingMLD

    private val beerCellMap = hashMapOf<Int, BeerTypeName>()

    fun passArguments(beerCellList: List<String>) {
        beerCellList.mapNotNull { BeerTypeName.values().find { name -> name.type == it } }
            .forEachIndexed { index, beerTypeName ->
                beerCellMap[index + 1] = beerTypeName
            }
        loadingMLD.postValue(true)
        punkRepository.getBeerList(1)
            .subscribeOn(schedulerProvider.io())
            .subscribe({ it ->
                loadingMLD.postValue(false)
                it.subList(0, beerCellList.size).mapNotNull {
                    mapToEntity(it, beerCellMap)
                }.let { entityList ->
                    beerCellListMLD.postValue(entityList)
                }
            }, {
                handleNetworkError(it)
                loadingMLD.postValue(false)
                beerCellListMLD.postValue(emptyList())
            }).let {
                compositeDisposable.add(it)
            }

    }

    private fun mapToEntity(beer: Beer, beerCellMap: Map<Int, BeerTypeName>): BeerListItemEntity? {
        val beerTypeName: BeerTypeName = beerCellMap[beer.id] ?: return null
        return BeerListItemEntity(beer.id, beer.name, beer.imageUrl, beer.abv, beerTypeName)
    }
}