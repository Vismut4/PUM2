package com.example.l4z1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.l4z1.data.GamesRepository
import com.example.l4z1.data.SettingsRepository

class GamesViewModelFactory(
    private val gamesRepository: GamesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GamesViewModel::class.java)) {
            return GamesViewModel(gamesRepository, settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}