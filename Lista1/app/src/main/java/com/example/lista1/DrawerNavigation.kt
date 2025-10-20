package com.example.lista1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lista1.pages.HistoriaZamowienPage
import com.example.lista1.pages.MojProfilPage
import com.example.lista1.pages.UstawieniaPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerNavigationScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Mój Profil",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch { drawerState.close() }
                            navController.navigate("profil")
                        }
                        .padding(vertical = 8.dp)
                )
                Text(
                    "Historia Zamówień",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch { drawerState.close() }
                            navController.navigate("historia")
                        }
                        .padding(vertical = 8.dp)
                )
                Text(
                    "Ustawienia",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch { drawerState.close() }
                            navController.navigate("ustawienia")
                        }
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Wyloguj",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch { drawerState.close() }
                            navController.navigate("glowna") {
                                popUpTo(0)
                                launchSingleTop = true
                            }
                        }
                        .padding(vertical = 8.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Aplikacja") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "profil",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("profil") { MojProfilPage() }
                composable("historia") { HistoriaZamowienPage() }
                composable("ustawienia") { UstawieniaPage() }
            }
        }
    }
}
