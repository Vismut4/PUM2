package com.example.lista3.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lista3.viewmodel.MainViewModel
import com.example.lista3.model.UiState
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState(initial = null)

    Surface(modifier = Modifier.fillMaxSize()) {
        if (uiState == null) {
            LoadingView()
        } else {
            DashboardView(uiState!!)
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
            Text("Ładowanie danych...")
        }
    }
}

@Composable
fun DashboardView(state: UiState) {
    val fmt = NumberFormat.getCurrencyInstance(Locale.getDefault())

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("Crypto Dashboard", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        CoinCard(
            title = "ComposeCoin",
            price = fmt.format(state.composeCoinPrice),
            amount = state.composeHoldings,
            total = fmt.format(state.composeCoinPrice * state.composeHoldings)
        )

        Spacer(modifier = Modifier.height(12.dp))

        CoinCard(
            title = "KotlinCoin",
            price = fmt.format(state.kotlinCoinPrice),
            amount = state.kotlinHoldings,
            total = fmt.format(state.kotlinCoinPrice * state.kotlinHoldings)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Całkowita wartość portfela", style = MaterialTheme.typography.titleMedium)
                Text(fmt.format(state.totalValue), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

@Composable
fun CoinCard(title: String, price: String, amount: Double, total: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text("Cena: $price")
            Text("Ilość: $amount")
            Text("Wartość: $total")
        }
    }
}

