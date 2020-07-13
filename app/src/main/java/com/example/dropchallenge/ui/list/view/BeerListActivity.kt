package com.example.dropchallenge.ui.list.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.dropchallenge.R
import com.example.dropchallenge.di.component.ActivityComponent
import com.example.dropchallenge.ui.base.BaseActivity
import com.example.dropchallenge.ui.list.viewmodel.BeerDetailViewModel
import com.example.dropchallenge.ui.list.viewmodel.BeerListViewModel
import kotlinx.android.synthetic.main.activity_beer_list.*

class BeerListActivity : BaseActivity<BeerListViewModel>() {

    lateinit var adapter: BeerListRecyclerViewAdapter

    override fun provideLayoutId(): Int {
        return R.layout.activity_beer_list
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter = BeerListRecyclerViewAdapter(emptyList())
        list.adapter = adapter
        adapter.setOnClickListner(object: BeerListRecyclerViewAdapter.OnClickListener {
            override fun onClick(item: BeerListItemEntity) {
              startActivity(BeerDetailActivity.getIntent(applicationContext,item.id))
            }
        })
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.beerCellListLD.observe(this, Observer {
            adapter.setItems(it)

        })
        viewModel.loadingLD.observe(this, Observer {
            progress_bar.isVisible = it
        })

        viewModel.passArguments(getArgument())
    }

    private fun getArgument(): List<String> {
        return intent.getStringArrayExtra(EXTRA_LIST).orEmpty().toList()
    }

    companion object {
        const val EXTRA_LIST = "LIST"
        fun getIntent(context: Context, displayOutput: List<String>) =
            Intent(context, BeerListActivity::class.java).apply {
                putExtra(EXTRA_LIST, displayOutput.toTypedArray())
            }
    }
}