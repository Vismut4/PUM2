package com.example.l4z1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert
    suspend fun insert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Delete
    suspend fun delete(game: Game)

    @Query("SELECT * FROM games")
    fun getAllGamesStream(): Flow<List<Game>>

    @Query("SELECT * FROM games WHERE isFavorite = 1")
    fun getFavoriteGamesStream(): Flow<List<Game>>
}