package com.example.lista5.data

interface CurrencyRepository {

    suspend fun getSupportedCurrencies(): List<String>

    suspend fun convertCurrency(
        amount: Double,
        from: String,
        to: String
    ): Double
}