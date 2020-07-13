package com.example.dropchallenge.domain

import com.example.dropchallenge.utils.SampleInput
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CustomerRequestProcessorTest {

    @Test
    fun testInput1(){
        val target = CustomerRequestProcessor()
        val outputList = target.processInputList(SampleInput.input1.split("\n"))
        assertEquals(listOf("C","C","C","C","B"),outputList)
    }

    @Test
    fun testInputWithInvalidTypeNumber(){
        val target = CustomerRequestProcessor()
        val outputList = target.processInputList(SampleInput.inputWithInvalidTypeNumber.split("\n"))
        assertEquals(emptyList<String>(),outputList)
    }

    @Test
    fun testInputWithInvalidType(){
        val target = CustomerRequestProcessor()
        val outputList = target.processInputList(SampleInput.inputWithInvalidType.split("\n"))
        assertEquals(emptyList<String>(),outputList)
    }

    @Test
    fun testInputWithTypeNumber100(){
        val target = CustomerRequestProcessor()
        val outputList = target.processInputList(SampleInput.inputWithTypeNumber100.split("\n"))
        val expectedList = mutableListOf<String>()
        repeat(100) {
            expectedList.add("C")
        }
        assertEquals(expectedList,outputList)
    }

    @Test
    fun testInputWithTypeNumberCustomerRequest(){
        val target = CustomerRequestProcessor()
        val outputList = target.processInputList(SampleInput.inputWithInvalidTypeNumberCustomerRequest.split("\n"))
        assertEquals(emptyList<String>(),outputList)
    }
}