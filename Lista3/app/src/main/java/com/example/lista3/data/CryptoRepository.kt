package com.example.lista3.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import kotlin.random.Random

class CryptoRepository {
    fun getComposeCoinPriceStream(): Flow<Double> = flow {
        while (true) {
            delay(3000L)
            emit(Random.nextDouble(10.0, 150.0))
        }
    }.flowOn(Dispatchers.IO)

    fun getKotlinCoinPriceStream(): Flow<Double> = flow {
        while (true) {
            delay(5000L)
            emit(Random.nextDouble(20.0, 300.0))
        }
    }.flowOn(Dispatchers.IO)
}
