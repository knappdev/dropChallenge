package com.example.dropchallenge.ui.main.model

data class BeerRequest(
        val beerTypeNumber: Int,
        val beerTypeName: BeerTypeName
    ){
        fun getBeerType() = "${beerTypeNumber}${beerTypeName}"

    }