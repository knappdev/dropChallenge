package com.example.dropchallenge.ui.list.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.dropchallenge.R
import com.example.dropchallenge.data.model.Hop
import com.example.dropchallenge.di.component.ActivityComponent
import com.example.dropchallenge.ui.base.BaseActivity
import com.example.dropchallenge.ui.list.viewmodel.BeerDetailViewModel
import kotlinx.android.synthetic.main.activity_beer_detail.*
import kotlinx.android.synthetic.main.switch_item.view.*

class BeerDetailActivity : BaseActivity<BeerDetailViewModel>() {

    override fun provideLayoutId(): Int {
        return R.layout.activity_beer_detail
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initObservers() {
        super.initObservers()
        viewModel.beerDetailLD.observe(this, Observer {
            notAvailableView.isVisible = (it == null)
            Glide.with(imageView).load(it.imageUrl).into(imageView)
            nameView.text = it.name
            abvView.text = it.abv
            descriptionView.text = it.description
            hopsLayout.removeAllViews()
            it.hops.map {
                val view = LayoutInflater.from(this).inflate(R.layout.switch_item,null,false)
                view.tag = it.hop
                view.setOnClickListener {
                    viewModel.onClickHop(it.tag as Hop, it.switchView.isChecked )
                }
                view.switchLabelView.text = it.hop.name
                view.switchView.isChecked = it.selected
                view.switchView.setOnCheckedChangeListener { buttonView, isChecked ->
                    viewModel.onClickHop(it.hop, isChecked)
                }
                view
            }.forEach { hopView ->
                hopsLayout.addView(hopView)
            }

        })
        viewModel.loadingLD.observe(this, Observer {
            progress_bar.isVisible = it
        })

        viewModel.notAvailableLD.observe(this, Observer {
            notAvailableView.isVisible = it
        })

        viewModel.passArguments(getArgument())
    }

    private fun getArgument(): Int {
        return intent.getIntExtra(EXTRA_BEER_ID,-1)
    }

    companion object {
        const val EXTRA_BEER_ID = "EXTRA_BEER_ID"
        fun getIntent(context: Context, beerId: Int) =
            Intent(context, BeerDetailActivity::class.java).apply {
                putExtra(EXTRA_BEER_ID, beerId)
            }
    }
}