package com.example.dropchallenge.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dropchallenge.R
import com.example.dropchallenge.utils.InternetHelper
import com.example.dropchallenge.utils.Resource
import com.example.dropchallenge.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable,
    private val internetHelper: InternetHelper
) : ViewModel() {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()

    protected fun internetConnectionWithMessage(): Boolean =
        if (internetHelper.isInternetAvailable()) {
            true
        } else {
            messageStringId.postValue(Resource.error(R.string.no_internet_error))
            false
        }

    protected fun handleNetworkError(error: Throwable?) {
        error?.let {
            messageString.postValue(Resource.error(it.message))
        }
    }

}