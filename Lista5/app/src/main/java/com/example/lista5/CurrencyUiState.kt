package com.example.lista5

data class CurrencyUiState(
    val currencies: List<String> = emptyList(),
    val fromCurrency: String = "",
    val toCurrency: String = "",
    val amount: String = "",
    val result: String = "",
    val isLoading: Boolean = false
)
