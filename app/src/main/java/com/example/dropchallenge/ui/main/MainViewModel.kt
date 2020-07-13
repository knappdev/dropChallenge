package com.example.dropchallenge.ui.main

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dropchallenge.data.repository.GithubRepository
import com.example.dropchallenge.domain.CustomerRequestProcessor
import com.example.dropchallenge.ui.base.BaseViewModel
import com.example.dropchallenge.ui.list.view.BeerListActivity
import com.example.dropchallenge.utils.InternetHelper
import com.example.dropchallenge.utils.SampleInput
import com.example.dropchallenge.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.InputStreamReader

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    internetHelper: InternetHelper,
    private val githubRepository: GithubRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, internetHelper) {

    private val outputListMLD = MutableLiveData<String>()
    fun outputListLD(): LiveData<String> = outputListMLD

    private val showOrHideDetailButtonMLD = MutableLiveData<Boolean>()
    fun showOrHideDetailButtonLD(): LiveData<Boolean> = showOrHideDetailButtonMLD

    private val displayOutput = mutableListOf<String>()

    fun startProcess() {
        displayOutput.clear()

        outputListMLD.postValue("Fetching...")

        if (true) {
            val list = SampleInput.input6.split("\n")
            processInputList(list)
        } else {
            githubRepository.getInputFile().subscribeOn(schedulerProvider.io()).subscribe({

                val inputLines = try {
                    InputStreamReader(it.byteStream()).readLines()
                } catch (e: Exception) {
                    emptyList<String>()
                }
                processInputList(inputLines)
            }, {
                handleNetworkError(it)
                outputListMLD.postValue("Fail to get input file")
            }).let {
                compositeDisposable.add(it)
            }
        }
    }

    private fun processInputList(inputLines: List<String>){
        if(!internetConnectionWithMessage()) return

        showOrHideDetailButtonMLD.postValue(false)
        val outputList = CustomerRequestProcessor().processInputList(inputLines)
        displayOutput.addAll(outputList)

        val printString = if (displayOutput.isEmpty()) {
            "No solution exists"
        } else {
            displayOutput.toString()
        }
        outputListMLD.postValue(printString)
        showOrHideDetailButtonMLD.postValue(true)
    }

    fun onClickDetailButton(context: Context) {
        ContextCompat.startActivity(
            context,
            BeerListActivity.getIntent(context, displayOutput)
                .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK },
            null
        )
    }

}