package com.example.l4z1

import com.example.l4z1.data.Game

data class GamesUiState(
    val games: List<Game> = emptyList(),
    val showOnlyFavorites: Boolean = false
)