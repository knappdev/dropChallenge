package com.example.dropchallenge.data.model

import com.google.gson.annotations.SerializedName

data class Beer (
    val id:Int,

    val name:String,

    val description: String,

    val abv: Float,

    @SerializedName("image_url")
    val imageUrl:String,

    val method: Method,

    val ingredients:Ingredient

)