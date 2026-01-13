package com.example.myapplication7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication7.data.CharacterRepository
import com.example.myapplication7.data.CharacterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState> = _uiState.asStateFlow()

    init {
        loadCharacters("Wszyscy")
    }

    fun loadCharacters(filter: String) {
        val statusQuery = if (filter == "Wszyscy") null else filter.lowercase()
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null,
                selectedFilter = filter
            )
        }

        viewModelScope.launch {
            try {
                val characters = repository.getCharacters(statusQuery)
                _uiState.update {
                    it.copy(
                        characters = characters,
                        isLoading = false,
                        error = if (characters.isEmpty()) "Brak postaci dla tego filtra" else null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Błąd sieci: ${e.message}"
                    )
                }
            }
        }
    }
}