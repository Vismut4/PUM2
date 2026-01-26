package com.example.lista5.data

import retrofit2.http.GET
import retrofit2.http.Query

interface FrankfurterApi {

    @GET("v1/currencies")
    suspend fun getCurrencies(): Map<String, String>

    @GET("v1/latest")
    suspend fun getRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): RatesResponse
}