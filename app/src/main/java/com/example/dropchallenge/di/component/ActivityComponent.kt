package com.example.dropchallenge.di.component

import com.example.dropchallenge.ui.main.MainActivity
import com.example.dropchallenge.di.ActivityScope
import com.example.dropchallenge.di.module.ActivityModule
import com.example.dropchallenge.ui.list.view.BeerDetailActivity
import com.example.dropchallenge.ui.list.view.BeerListActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: BeerListActivity)
    fun inject(activity: BeerDetailActivity)
}