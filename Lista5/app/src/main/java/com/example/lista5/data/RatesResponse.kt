package com.example.lista5.data

data class RatesResponse(
    val base: String,
    val rates: Map<String, Double>
)