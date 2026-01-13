package com.example.l4z1.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingsRepository(private val context: Context) {

    companion object {
        private val SHOW_ONLY_FAVORITES = booleanPreferencesKey("show_only_favorites")
    }

    val showOnlyFavoritesFlow: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[SHOW_ONLY_FAVORITES] ?: false }

    suspend fun setShowOnlyFavorites(showOnly: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SHOW_ONLY_FAVORITES] = showOnly
        }
    }
}