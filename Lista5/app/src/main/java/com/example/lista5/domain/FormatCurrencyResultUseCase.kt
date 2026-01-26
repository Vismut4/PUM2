package com.example.lista5.domain

import jakarta.inject.Inject

class FormatCurrencyResultUseCase @Inject constructor() {

    operator fun invoke(
        amount: Double,
        from: String,
        result: Double,
        to: String
    ): String {
        return "%.2f %s = %.2f %s".format(amount, from, result, to)
    }
}
