package com.example.l4z1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.l4z1.data.GamesRepository
import com.example.l4z1.data.SettingsRepository
import com.example.l4z1.data.GameDatabase
import com.example.l4z1.ui.GamesScreen
import com.example.l4z1.viewmodel.GamesViewModel
import com.example.l4z1.ui.theme.L4z1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Room
        val database = GameDatabase.getDatabase(applicationContext)
        val gamesRepository = GamesRepository(database.gameDao())

        // DataStore
        val settingsRepository = SettingsRepository(applicationContext)

        setContent {
            L4z1Theme {

                val viewModel: GamesViewModel = viewModel(
                    factory = GamesViewModelFactory(
                        gamesRepository,
                        settingsRepository
                    )
                )

                GamesScreen(viewModel = viewModel)
            }
        }
    }
}
