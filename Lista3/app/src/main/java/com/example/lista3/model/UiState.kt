package com.example.lista3.model

data class UiState(
    val composeCoinPrice: Double,
    val kotlinCoinPrice: Double,
    val composeHoldings: Double,
    val kotlinHoldings: Double
) {
    val totalValue: Double
        get() = composeCoinPrice * composeHoldings + kotlinCoinPrice * kotlinHoldings
}


