package com.example.dropchallenge.ui.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.dropchallenge.R
import com.example.dropchallenge.di.component.ActivityComponent
import com.example.dropchallenge.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity<MainViewModel>() {

    override fun provideLayoutId(): Int {
       return R.layout.main_activity
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
       activityComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        processButton.setOnClickListener {
            viewModel.startProcess()
        }
        detailButton.setOnClickListener {
            viewModel.onClickDetailButton(applicationContext)
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.outputListLD().observe(this, Observer {
            message.text = it
        })
        viewModel.showOrHideDetailButtonLD().observe(this, Observer {
            detailButton.isVisible = it
        })
    }
}