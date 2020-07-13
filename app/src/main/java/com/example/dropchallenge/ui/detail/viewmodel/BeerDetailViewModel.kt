package com.example.dropchallenge.ui.list.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dropchallenge.data.model.Beer
import com.example.dropchallenge.data.model.Hop
import com.example.dropchallenge.data.repository.BeerRepository
import com.example.dropchallenge.data.repository.PunkRepository
import com.example.dropchallenge.ui.base.BaseViewModel
import com.example.dropchallenge.ui.list.view.BeerDetailEntity
import com.example.dropchallenge.utils.InternetHelper
import com.example.dropchallenge.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class BeerDetailViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    internetHelper: InternetHelper,
    private val punkRepository: PunkRepository,
    private val beerRepository: BeerRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, internetHelper) {

    private val beerDetailMLD = MutableLiveData<BeerDetailEntity>()
    val beerDetailLD: LiveData<BeerDetailEntity> = beerDetailMLD


    private val loadingMLD: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLD: LiveData<Boolean> = loadingMLD

    private val notAvailableMLD = MutableLiveData<Boolean>()
    val notAvailableLD: LiveData<Boolean> = notAvailableMLD

    val beer: Beer? = null

    fun passArguments(beerId: Int) {
        if (beerId == -1 || !internetConnectionWithMessage()) {
            notAvailableMLD.postValue(true)
            return
        }
        loadingMLD.postValue(true)
        punkRepository.getBeer(beerId)
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                loadingMLD.postValue(false)
                processBeerDetail(it.firstOrNull())

            }, {
                Log.e("TAG", "error", it)
                handleNetworkError(it)
                notAvailableMLD.postValue(true)
                loadingMLD.postValue(false)
            }).let {
                compositeDisposable.add(it)
            }
    }

    private fun processBeerDetail(nullableBeer: Beer?) {
        val beer = nullableBeer ?: run {
            notAvailableMLD.postValue(true)
            return
        }
        beerRepository.updateBeer(beer)
        beerDetailMLD.postValue(mapToEntity(beer))
    }

    private fun mapToEntity(beer: Beer): BeerDetailEntity {
        return BeerDetailEntity(
            beer.name,
            beer.description,
            beer.imageUrl,
            beer.abv.toString(),
            beerRepository.getHopStateList(beer.id)
        )
    }

    fun onClickHop(hop: Hop, isChecked: Boolean) {
        beer?.let { beer ->
            beerRepository.updateBeer(beer, hop, isChecked)
        }
    }
}