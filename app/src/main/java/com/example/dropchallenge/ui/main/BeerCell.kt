package com.example.dropchallenge.ui.main

import com.example.dropchallenge.ui.main.model.BeerTypeName
import com.example.dropchallenge.ui.main.model.Customer
import java.lang.IllegalStateException

class BeerCell(val id:Int) {
    private val barrelRequests:MutableSet<Int> = mutableSetOf()
    private val classicRequests:MutableSet<Int> = mutableSetOf()

    fun addToBarrelRequest(customer: Int){
        barrelRequests.add(customer)
    }

    fun addToClassicRequest(customer: Int){
        classicRequests.add(customer)
    }

    fun getBarrelRequest():Set<Int> = barrelRequests
    fun getClassicRequest():Set<Int> = classicRequests

    var typeNeeded:BeerTypeName?= null

    fun setNeeded(requestType:BeerTypeName) {
        if(typeNeeded!=null) throw IllegalStateException("Already Set as ${typeNeeded}")
        typeNeeded = requestType
    }

    fun getByType(requestType:BeerTypeName):Set<Int>{
        return when(requestType){
            BeerTypeName.CLASSIC -> getClassicRequest()
            BeerTypeName.BARREL_AGED -> getBarrelRequest()
        }
    }



}