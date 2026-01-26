package com.example.lista5

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lista5.domain.ConvertCurrencyUseCase
import com.example.lista5.domain.FormatCurrencyResultUseCase
import com.example.lista5.domain.GetSupportedCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCurrencies: GetSupportedCurrenciesUseCase,
    private val convertCurrency: ConvertCurrencyUseCase,
    private val formatResult: FormatCurrencyResultUseCase
) : ViewModel() {

    var uiState by mutableStateOf(CurrencyUiState())
        private set

    init {
        loadCurrencies()
    }

    private fun loadCurrencies() = viewModelScope.launch {
        uiState = uiState.copy(isLoading = true)
        val list = getCurrencies()
        uiState = uiState.copy(
            currencies = list,
            fromCurrency = list.first(),
            toCurrency = list[1],
            isLoading = false
        )
    }

    fun onAmountChange(value: String) {
        uiState = uiState.copy(amount = value)
    }

    fun onFromCurrencyChange(value: String) {
        uiState = uiState.copy(fromCurrency = value)
    }

    fun onToCurrencyChange(value: String) {
        uiState = uiState.copy(toCurrency = value)
    }

    fun onConvert() = viewModelScope.launch {
        uiState = uiState.copy(isLoading = true)

        val amount = uiState.amount.toDoubleOrNull()
        if (amount == null) {
            uiState = uiState.copy(isLoading = false)
            return@launch
        }

        val result = convertCurrency(
            amount,
            uiState.fromCurrency,
            uiState.toCurrency
        )

        uiState = uiState.copy(
            result = formatResult(
                amount,
                uiState.fromCurrency,
                result,
                uiState.toCurrency
            ),
            isLoading = false
        )
    }
}
