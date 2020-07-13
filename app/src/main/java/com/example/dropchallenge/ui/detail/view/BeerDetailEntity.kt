package com.example.dropchallenge.ui.list.view

import com.example.dropchallenge.data.model.Hop
import com.example.dropchallenge.data.model.local.HopState
import com.example.dropchallenge.ui.main.model.BeerTypeName

data class BeerDetailEntity (
    val name:String,
    val description:String,
    val imageUrl: String,
    val abv: String,
    val hops:List<HopState>
)