package com.example.l4z1.data

import kotlinx.coroutines.flow.Flow

class GamesRepository(private val dao: GameDao) {

    fun getAllGamesStream(): Flow<List<Game>> = dao.getAllGamesStream()
    fun getFavoriteGamesStream(): Flow<List<Game>> = dao.getFavoriteGamesStream()

    suspend fun addGame(game: Game) = dao.insert(game)
    suspend fun updateGame(game: Game) = dao.update(game)
    suspend fun deleteGame(game: Game) = dao.delete(game)
}