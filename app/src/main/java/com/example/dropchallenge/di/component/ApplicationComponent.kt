
package com.example.dropchallenge.di.component

import android.app.Application
import com.example.dropchallenge.AppApplication
import com.example.dropchallenge.data.remote.GithubApi
import com.example.dropchallenge.data.remote.PunkApi
import com.example.dropchallenge.data.repository.BeerRepository
import com.example.dropchallenge.data.repository.GithubRepository
import com.example.dropchallenge.data.repository.PunkRepository
import com.example.dropchallenge.di.module.ApplicationModule
import com.example.dropchallenge.utils.InternetHelper
import com.example.dropchallenge.utils.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(app: AppApplication)
    fun getApplication(): Application
    fun getSchedulerProvider(): SchedulerProvider
    fun getCompositeDisposable(): CompositeDisposable
    fun getPunkService(): PunkApi
    fun getGithubService(): GithubApi
    fun getNetworkHelper(): InternetHelper
    fun getPunkRepository(): PunkRepository
    fun getGithubRepository(): GithubRepository
    fun getBeerRepository(): BeerRepository
}