package com.example.lista5.data

import javax.inject.Inject


class CurrencyRepositoryImpl @Inject constructor(
    private val api: FrankfurterApi
) : CurrencyRepository {

    override suspend fun getSupportedCurrencies(): List<String> {
        return api.getCurrencies().keys.sorted()
    }

    override suspend fun convertCurrency(
        amount: Double,
        from: String,
        to: String
    ): Double {
        val response = api.getRates(from, to)
        val rate = response.rates[to] ?: error("Brak kursu")
        return amount * rate
    }
}