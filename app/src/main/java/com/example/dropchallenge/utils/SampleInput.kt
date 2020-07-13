package com.example.dropchallenge.utils

object SampleInput {

    val input1: String = "5\n" +
            "1 B 3 C 5 C\n" +
            "2 C 3 B 4 C\n" +
            "5 B"

    val input2: String = "1\n" +
            "1 C\n" +
            "1 B"

    val input3: String = "5\n" +
            "2 B\n" +
            "5 C\n" +
            "1 C\n" +
            "5 C 1 C 4 B\n" +
            "3 C\n" +
            "5 C\n" +
            "3 C 5 C 1 C\n" +
            "3 C\n" +
            "2 B\n" +
            "5 C 1 C\n" +
            "2 B\n" +
            "5 C\n" +
            "4 B\n" +
            "5 C 4 B"

    val input4 = "2\n" +
            "1 C 2 B\n" +
            "1 B"

    val input5 = "2\n" +
            "1 B 2 C\n" +
            "1 C 2 B"

    val input6 = "13\n" +
            "13 B 2 C\n" +
            "1 C 2 B"

    val inputWithInvalidTypeNumber = "13A\n" +
            "13 B 2 C\n" +
            "1 C 2 B"

    val inputWithInvalidType = "13\n" +
            "13 D 2 C\n" +
            "1 C 2 B"

    val inputWithTypeNumber100 = "100\n" +
            "13 C 2 C\n" +
            "1 C 2 C"

    val inputWithInvalidTypeNumberCustomerRequest = "100\n" +
            "101 C 2 C\n" +
            "1 C 2 C"


}