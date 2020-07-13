package com.example.dropchallenge.di.module

import android.app.Application
import com.example.dropchallenge.AppApplication
import com.example.dropchallenge.data.remote.ApiBuilder
import com.example.dropchallenge.data.remote.GithubApi
import com.example.dropchallenge.data.remote.PunkApi
import com.example.dropchallenge.utils.InternetHelper
import com.example.dropchallenge.utils.RxSchedulerProvider
import com.example.dropchallenge.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AppApplication) {
    private val BASE_URL = "https://api.punkapi.com/v2/"
    private val BASE_URL_GITHUB = "https://gist.githubusercontent.com/"

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun providePunkService(): PunkApi =
        ApiBuilder.create(
            BASE_URL
        )

    @Provides
    @Singleton
    fun provideGithubService(): GithubApi =
        ApiBuilder.createGithubService(
            BASE_URL_GITHUB
        )

    @Singleton
    @Provides
    fun provideNetworkHelper(): InternetHelper = InternetHelper(application)
}