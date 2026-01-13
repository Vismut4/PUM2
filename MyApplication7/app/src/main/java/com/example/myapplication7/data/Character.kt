package com.example.myapplication7.data

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val image: String
)

data class CharactersResponse(
    val results: List<Character>
)

data class CharacterUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedFilter: String = "All"
)