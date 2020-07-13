package com.example.dropchallenge.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.dropchallenge.AppApplication
import com.example.dropchallenge.di.component.ActivityComponent
import com.example.dropchallenge.di.component.DaggerActivityComponent
import com.example.dropchallenge.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        initView(savedInstanceState)
        initObservers()
    }

    protected open fun initObservers() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    private fun showMessage(message: String) = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

    private fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as AppApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun initView(savedInstanceState: Bundle?)
}