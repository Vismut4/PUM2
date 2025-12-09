package com.example.lista3z2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.lista3z2.ui.SerialVerificationScreen
import com.example.lista3z2.ui.theme.Lista3z2Theme
import com.example.lista3z2.viewmodel.SerialVerificationViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SerialVerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lista3z2Theme {
                SerialVerificationScreen(viewModel)
            }
        }
    }
}
