package com.example.lista1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lista1.pages.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenWithDrawer(navController: NavHostController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navItemList = listOf(
        NavItem("Glowna", Icons.Default.Home),
        NavItem("Kategorie", Icons.Default.Star),
        NavItem("Koszyk", Icons.Default.ShoppingCart),
        NavItem("Ulubione", Icons.Default.Favorite)
    )

    val drawerItems = listOf(
        Triple("Mój Profil", "profil", Icons.Default.Person),
        Triple("Historia Zamówień", "historia", Icons.Default.CheckCircle),
        Triple("Ustawienia", "ustawienia", Icons.Default.Settings),
        Triple("Wyloguj", "logout", null)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(300.dp)
                .background(Color.White)
                .padding(16.dp)
            ) {
                Column {
                    drawerItems.forEach { (label, route, _) ->
                        Text(
                            text = label,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch { drawerState.close() }
                                    if (route == "logout") {
                                        navController.navigate("glowna") {
                                            popUpTo(0)
                                            launchSingleTop = true
                                        }
                                    } else {
                                        navController.navigate(route)
                                    }
                                }
                                .padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    navItemList.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentRoute == navItem.label.lowercase(),
                            onClick = { navController.navigate(navItem.label.lowercase()) },
                            icon = { Icon(navItem.icon, contentDescription = navItem.label) },
                            label = { Text(navItem.label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "glowna",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("glowna") { GlownaPage() }
                composable("kategorie") { KategoriePage() }
                composable("koszyk") { KoszykPage(onCheckoutClick = { navController.navigate("adres") }) }
                composable("ulubione") { UlubionePage() }

                composable("adres") { AdresPage(onNext = { navController.navigate("platnosc") }) }
                composable("platnosc") { PlatnoscPage(onNext = { navController.navigate("podsumowanie") }) }
                composable("podsumowanie") {
                    PodsumowaniePage(onFinish = {
                        navController.navigate("glowna") { popUpTo(0); launchSingleTop = true }
                    })
                }

                composable("profil") { MojProfilPage() }
                composable("historia") { HistoriaZamowienPage() }
                composable("ustawienia") { UstawieniaPage() }
            }
        }
    }
}
