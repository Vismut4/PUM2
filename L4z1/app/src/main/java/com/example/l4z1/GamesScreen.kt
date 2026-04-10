package com.example.l4z1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.l4z1.data.Game

@Composable
fun GamesScreen(viewModel: GamesViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    var newTitle by remember { mutableStateOf("") }
    var newGenre by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Filtr Show Only Favorites
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Pokaż tylko ulubione")
            Switch(
                checked = uiState.showOnlyFavorites,
                onCheckedChange = { viewModel.setShowOnlyFavorites(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("Tytuł") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            TextField(
                value = newGenre,
                onValueChange = { newGenre = it },
                label = { Text("Gatunek") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Button(onClick = {
                viewModel.addGame(newTitle, newGenre)
                newTitle = ""
                newGenre = ""
            }) {
                Text("Dodaj")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.games) { game ->
                GameItem(game = game, onToggleFavorite = { viewModel.toggleFavorite(game) })
            }
        }
    }
}

@Composable
fun GameItem(game: Game, onToggleFavorite: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleFavorite() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = game.title, style = MaterialTheme.typography.titleMedium)
            Text(text = game.genre, style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(onClick = onToggleFavorite) {
            if (game.isFavorite) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Ulubione",
                    tint = Color.Red
                )
            } else {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Nieulubione")
            }
        }
    }
}