package com.example.dropchallenge.domain

import com.example.dropchallenge.ui.main.BeerCell
import com.example.dropchallenge.ui.main.model.BeerRequest
import com.example.dropchallenge.ui.main.model.BeerTypeName
import com.example.dropchallenge.ui.main.model.Customer
import java.util.*

class CustomerRequestProcessor {

    private val permanentCustomerFulfilled = mutableSetOf<Int>()
    private val decidedBeerCell: HashMap<Int, BeerTypeName?> = HashMap()
    private var maxTypeNumber:Int? = null

    fun processInputList(lineList: List<String>): List<String> {
        val status = validateLineArray(lineList)
        if (status != InputDataStatus.VALID) return emptyList()

        val firstLine = lineList[0]
        val tempTypeNumber = try {
            val typeNumber = firstLine.toInt()
            if (typeNumber !in 1..999) {
                throw IllegalArgumentException("Invalid Type Number")
            }
            typeNumber
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } ?: return emptyList()

        maxTypeNumber = tempTypeNumber
        try {
            // Maps BeerCell requests to customerId
            val beerCellRequestMap = mutableSetOf<BeerCell>()
            val customerLineList: ArrayList<String> = ArrayList(lineList)
            customerLineList.removeAt(0)
            val customerList = customerLineList.map { line ->
                val result = regex.findAll(line.replace(" ", ""))
                result
            }.mapIndexed { index: Int, sequence: Sequence<MatchResult> ->
                val listOfBeerRequest = processCustomerRequest(sequence)
                updateBeerCellRequestMap(index, beerCellRequestMap, listOfBeerRequest)
                Customer(index, listOfBeerRequest)
            }

            processBeerCellRequest(beerCellRequestMap, customerList)
            prepareUnrequestedBeerCells()
            return getDisplayList(customerList)
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private fun prepareUnrequestedBeerCells() {
        maxTypeNumber?.let{max->
            for (i in 1..max){
                if(!decidedBeerCell.containsKey(i)){
                    decidedBeerCell[i] = BeerTypeName.CLASSIC
                }
            }
        }

    }


    private fun getDisplayList(customerList: List<Customer>): List<String> {
        if (!didAllCustomerRequestFulfilled(customerList)) {
            return emptyList()
        }
        return decidedBeerCell.keys.sorted().mapNotNull { decidedBeerCell[it] }.map { it.type }
    }

    private fun didAllCustomerRequestFulfilled(customerList: List<Customer>): Boolean {
        return permanentCustomerFulfilled.size == customerList.size
    }

    private fun updateBeerCellRequestMap(
        customerId: Int,
        beerTypeRequestMap: MutableSet<BeerCell>,
        setOfBeerRequest: Set<BeerRequest>
    ) {
        setOfBeerRequest.forEach { beerRequest ->
            val beerCell = beerTypeRequestMap.find { it.id == beerRequest.beerTypeNumber } ?: run {
                val newCell = BeerCell(beerRequest.beerTypeNumber)
                beerTypeRequestMap.add(newCell)
                newCell
            }
            when (beerRequest.beerTypeName) {
                BeerTypeName.BARREL_AGED -> {
                    beerCell.addToBarrelRequest(customerId)
                }
                BeerTypeName.CLASSIC -> {
                    beerCell.addToClassicRequest(customerId)
                }
            }
        }
    }

    private fun isNeeded(
        cell: BeerCell,
        requestType: BeerTypeName,
        customerId: Int,
        customerList: List<Customer>
    ): Boolean {
        val customer = getCustomerById(customerId, customerList)
        val beerRequest = BeerRequest(cell.id, requestType)
        if (customerHasOtherRequest(customer, beerRequest)) return false
        // Ignore other requests
        // WHEN customer has no other requests
        fulfillWith(cell, requestType)
        return true
    }

    private fun fulfillWith(cell: BeerCell, requestType: BeerTypeName) {
        cell.setNeeded(requestType)
        decidedBeerCell[cell.id] = requestType
        permanentCustomerFulfilled.addAll(cell.getByType(requestType))//Mark all customers as fullfilled
    }

    private fun customerHasOtherRequest(customer: Customer, currentRequest: BeerRequest): Boolean {
        val list =
            customer.beerRequest.filterNot { it.beerTypeNumber == currentRequest.beerTypeNumber }
        if (list.isEmpty()) {
            return false
        }

        return list.filterNot { isBeerRequestObsolete(it) }.isNotEmpty()
    }

    private fun isBeerRequestObsolete(customerBeerRequest: BeerRequest): Boolean {
        val beerTypeName: BeerTypeName =
            decidedBeerCell[customerBeerRequest.beerTypeNumber] ?: return false
        return customerBeerRequest.beerTypeName != beerTypeName
    }


    private fun getCustomerById(id: Int, customerList: List<Customer>): Customer {
        return customerList.find { it.id == id }
            ?: throw IllegalStateException("Customer Not Found")
    }


    private fun isValidBeerRequest(beerRequest: BeerRequest, maxBeerTypeNumber: Int): Boolean {
        return beerRequest.beerTypeNumber in 1..maxBeerTypeNumber
    }

    private fun processCustomerRequest(sequence: Sequence<MatchResult>): Set<BeerRequest> {
        return sequence.map {
            it.value
        }.map {
            val beerTypeNumber = regexInteger.find(it)?.value?.toInt()
                ?: throw IllegalArgumentException("Invalid type number")

            maxTypeNumber?.let {
                if(beerTypeNumber !in 1..it){
                    throw IllegalArgumentException("Invalid type number")
                }
            }
            val beerTypeName = it.replace(beerTypeNumber.toString(), "")
            BeerRequest(
                beerTypeNumber,
                BeerTypeName.values().find { name -> name.type == beerTypeName }
                    ?: throw IllegalArgumentException("Beer type not found ${beerTypeName}")
            )
        }.toSet()
    }

    private fun checkForValidity(beerRequest: BeerRequest, beerTypeQuantity: Int) {
        isValidBeerRequest(beerRequest, beerTypeQuantity).let { valid ->
            if (!valid) throw IllegalStateException("Invalid beer type ${beerRequest.beerTypeNumber}. Max allowed ${beerTypeQuantity}")
        }
    }


    private fun processBeerCellRequest(
        beerTypeRequestMap: MutableSet<BeerCell>,
        customerList: List<Customer>
    ) {
        beerTypeRequestMap.forEach beerCell@{ cell ->
            cell.getClassicRequest().forEach classicRequest@{ customerId ->
                if (isNeeded(cell, BeerTypeName.CLASSIC, customerId, customerList)) {
                    return@beerCell
                }
            }

            cell.getBarrelRequest().forEach { customerId ->
                if (isNeeded(cell, BeerTypeName.BARREL_AGED, customerId, customerList)) {
                    return@beerCell
                }
            }

            fulfillWith(cell, BeerTypeName.CLASSIC)
        }
    }

    private fun validateLineArray(lineList: List<String>): InputDataStatus {
        if (lineList.isEmpty()) return InputDataStatus.NO_DATA

        val firstLine = lineList[0]
        if (!firstLine.matches(regexInteger)) {
            return InputDataStatus.INVALID_BEER_TYPE
        }
        if (lineList.size < 2) {
            return InputDataStatus.MISSING_CUSTOMERS
        }
        return InputDataStatus.VALID
    }

    companion object {
        private val regex = Regex("([0-9]{1,3}[BC]+)")
        private val regexInteger = Regex("[0-9]{1,3}")
    }

}