package com.example.lista3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.lista3.ui.theme.Lista3Theme
import com.example.lista3.viewmodel.MainViewModel
import com.example.lista3.ui.MainScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lista3Theme {
                MainScreen(viewModel)
            }
        }
    }
}
