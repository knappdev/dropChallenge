package com.example.dropchallenge.ui.list.view

import com.example.dropchallenge.ui.main.model.BeerTypeName

data class BeerListItemEntity (
    val id:Int,
    val name:String,
    val imageUrl: String,
    val abv: Float,
    val beerType: BeerTypeName
)