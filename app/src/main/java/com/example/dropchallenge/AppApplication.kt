package com.example.dropchallenge

import android.app.Application
import com.example.dropchallenge.di.component.ApplicationComponent
import com.example.dropchallenge.di.component.DaggerApplicationComponent
import com.example.dropchallenge.di.module.ApplicationModule

class AppApplication:Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

        applicationComponent.inject(this)
    }
}