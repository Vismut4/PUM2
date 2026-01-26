package com.example.lista5.domain

import com.example.lista5.data.CurrencyRepository
import javax.inject.Inject

class GetSupportedCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getSupportedCurrencies()
    }
}