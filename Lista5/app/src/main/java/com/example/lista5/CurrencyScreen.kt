package com.example.lista5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CurrencyScreen(
    viewModel: CurrencyViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        TextField(
            value = state.amount,
            onValueChange = viewModel::onAmountChange,
            label = { Text("Kwota") },
            modifier = Modifier.fillMaxWidth()
        )

        CurrencyDropdown(
            label = "Z",
            currencies = state.currencies,
            selected = state.fromCurrency,
            onSelect = viewModel::onFromCurrencyChange
        )

        CurrencyDropdown(
            label = "Na",
            currencies = state.currencies,
            selected = state.toCurrency,
            onSelect = viewModel::onToCurrencyChange
        )

        Button(
            onClick = viewModel::onConvert,
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading)
                CircularProgressIndicator(modifier = Modifier.size(18.dp))
            else
                Text("Przelicz")
        }

        Text(
            text = state.result,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

