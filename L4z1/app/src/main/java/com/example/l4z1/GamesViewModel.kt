package com.example.l4z1


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.l4z1.data.Game
import com.example.l4z1.data.GamesRepository
import com.example.l4z1.data.SettingsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GamesViewModel(
    private val gamesRepository: GamesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val showOnlyFavoritesFlow = settingsRepository.showOnlyFavoritesFlow

    private val gamesFlow: Flow<List<Game>> = showOnlyFavoritesFlow.flatMapLatest { showOnly ->
        if (showOnly) gamesRepository.getFavoriteGamesStream()
        else gamesRepository.getAllGamesStream()
    }

    val uiState: StateFlow<GamesUiState> = combine(
        gamesFlow,
        showOnlyFavoritesFlow
    ) { games, showOnly ->
        GamesUiState(
            games = games,
            showOnlyFavorites = showOnly
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = GamesUiState()
    )

    fun addGame(title: String, genre: String) {
        if (title.isBlank() || genre.isBlank()) return
        val newGame = Game(title = title, genre = genre)
        viewModelScope.launch {
            gamesRepository.addGame(newGame)
        }
    }

    fun toggleFavorite(game: Game) {
        val updatedGame = game.copy(isFavorite = !game.isFavorite)
        viewModelScope.launch {
            gamesRepository.updateGame(updatedGame)
        }
    }

    fun setShowOnlyFavorites(showOnly: Boolean) {
        viewModelScope.launch {
            settingsRepository.setShowOnlyFavorites(showOnly)
        }
    }
}