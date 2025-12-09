package com.example.lista3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lista3.data.CryptoRepository
import com.example.lista3.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CryptoRepository = CryptoRepository()
) : ViewModel() {

    private val composeHoldings = 10.0
    private val kotlinHoldings = 2.0

    private val composePriceFlow = repository.getComposeCoinPriceStream()
    private val kotlinPriceFlow = repository.getKotlinCoinPriceStream()

    val uiState: StateFlow<UiState?> =
        combine(composePriceFlow, kotlinPriceFlow) { composePrice, kotlinPrice ->
            UiState(
                composeCoinPrice = composePrice,
                kotlinCoinPrice = kotlinPrice,
                composeHoldings = composeHoldings,
                kotlinHoldings = kotlinHoldings
            )
        }
            .flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
}

