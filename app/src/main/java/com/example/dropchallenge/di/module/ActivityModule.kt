package com.example.dropchallenge.di.module

import androidx.lifecycle.ViewModelProviders
import com.example.dropchallenge.data.repository.BeerRepository
import com.example.dropchallenge.data.repository.GithubRepository
import com.example.dropchallenge.data.repository.PunkRepository
import com.example.dropchallenge.ui.base.BaseActivity
import com.example.dropchallenge.ui.list.viewmodel.BeerDetailViewModel
import com.example.dropchallenge.ui.list.viewmodel.BeerListViewModel
import com.example.dropchallenge.ui.main.MainViewModel
import com.example.dropchallenge.utils.InternetHelper
import com.example.dropchallenge.utils.SchedulerProvider
import com.example.dropchallenge.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        internetHelper: InternetHelper,
        punkRepository: GithubRepository
    ): MainViewModel =
        ViewModelProviders.of(activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(schedulerProvider, compositeDisposable, internetHelper, punkRepository)
        }).get(MainViewModel::class.java)

    @Provides
    fun provideBeerDetailViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        internetHelper: InternetHelper,
        punkRepository: PunkRepository,
        beerRepository: BeerRepository
    ): BeerDetailViewModel =
        ViewModelProviders.of(activity, ViewModelProviderFactory(BeerDetailViewModel::class) {
            BeerDetailViewModel(
                schedulerProvider,
                compositeDisposable,
                internetHelper,
                punkRepository,
                beerRepository

            )
        }).get(BeerDetailViewModel::class.java)

    @Provides
    fun provideBeerListViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        internetHelper: InternetHelper,
        punkRepository: PunkRepository
    ): BeerListViewModel =
        ViewModelProviders.of(activity, ViewModelProviderFactory(BeerListViewModel::class) {
            BeerListViewModel(
                schedulerProvider,
                compositeDisposable,
                internetHelper,
                punkRepository
            )
        }).get(BeerListViewModel::class.java)
}