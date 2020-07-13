package com.example.dropchallenge.ui.main.model

data class Customer(
        val id: Int,
        val beerRequest: Set<BeerRequest>
    )