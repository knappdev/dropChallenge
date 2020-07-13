package com.example.dropchallenge.data.repository

import com.example.dropchallenge.data.model.Beer
import com.example.dropchallenge.data.model.Hop
import com.example.dropchallenge.data.model.local.HopState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeerRepository @Inject constructor() {

    val beerList: HashMap<Int, Beer> = hashMapOf()
    val beerToHopStateMap: HashMap<Int, ArrayList<HopState>> = hashMapOf()

    fun getBeer(id: Int): Beer? {
        return beerList[id]
    }
    fun updateBeer(beer: Beer){
        beerList[beer.id] = beer
    }

    fun updateBeer(beer: Beer, hop: Hop, selected: Boolean) {
        beerList[beer.id] = beer
        val stateList = beerToHopStateMap[beer.id] ?: ArrayList()
        val hopState = stateList.find { it.hop.name == hop.name }
        if (hopState != null) {
            stateList.remove(hopState)
        }
        stateList.add(HopState(hop, selected))
    }

    fun getHopStateList(id: Int): List<HopState> {
        return beerToHopStateMap[id]?:buildDefaultHopState(id)
    }

    private fun buildDefaultHopState(id: Int):List<HopState>{
        return getBeer(id)?.let{beer ->
            beer.ingredients.hops.map { hop -> HopState(hop, false) }
        }?: emptyList()
    }
}