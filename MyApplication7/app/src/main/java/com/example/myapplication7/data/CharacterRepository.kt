package com.example.myapplication7.data

class CharacterRepository(private val api: RickAndMortyApi) {
    suspend fun getCharacters(status: String?): List<Character> {
        return try {
            val response = api.getCharacters(status)
            response.results
        } catch (e: Exception) {
            emptyList()
        }
    }
}