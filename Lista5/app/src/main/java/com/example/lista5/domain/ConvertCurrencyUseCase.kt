package com.example.lista5.domain

import com.example.lista5.data.CurrencyRepository
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        amount: Double,
        from: String,
        to: String
    ): Double {
        return repository.convertCurrency(amount, from, to)
    }
}