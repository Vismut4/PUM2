package com.example.myapplication7.data

import retrofit2.http.GET
import retrofit2.http.Query
interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("status") status: String? = null
    ): CharactersResponse
}