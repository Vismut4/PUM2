package com.example.pum2l2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

data class Movie(val id: Int, val title: String)

suspend fun fetchPopularMovies(): List<Movie> {
    delay(2000)
    return listOf(
        Movie(1, "Korutynowy Jeździec"),
        Movie(2, "Imperium Kodu Kontratakuje"),
        Movie(3, "Władca Wątków: Drużyna Scope’a"),
        Movie(4, "Async: Początek"),
        Movie(5, "Gdzie jest Nemo... w Pamięci?")
    )
}

suspend fun fetchMovieDetails(movieId: Int): Map<String, String> {
    delay(1500)
    return mapOf(
        "Reżyser" to "Krzysztof Kompilator",
        "Rok" to "2025",
        "Gatunek" to "Sci-Fi"
    )
}

suspend fun fetchMovieReviews(movieId: Int): List<String> {
    delay(3000)
    return listOf(
        "Anna: Świetny film!",
        "Piotr: Mógłby być lepszy."
    )
}

suspend fun fetchSimilarMovies(movieId: Int): List<String> {
    delay(2000)
    val allMovies = listOf(
        "Korutynowy Jeździec",
        "Imperium Kodu Kontratakuje",
        "Władca Wątków: Drużyna Scope’a",
        "Async: Początek",
        "Gdzie jest Nemo... w Pamięci?"
    )
    return allMovies.filterIndexed { index, _ -> index + 1 != movieId }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "movie_list") {
        composable("movie_list") {
            MovieListScreen(navController)
        }
        composable("movie_details/{movieId}/{movieTitle}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
            val title = backStackEntry.arguments?.getString("movieTitle") ?: ""
            MovieDetailsScreen(movieId, title, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(navController: NavHostController) {
    var isLoading by remember { mutableStateOf(true) }
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            delay(3000)
            movies = fetchPopularMovies()
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Kino GURU - Popularne Filmy", fontWeight = FontWeight.Bold)
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies) { movie ->
                    MovieItem(movie) {
                        navController.navigate("movie_details/${movie.id}/${movie.title}")
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star Icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(movieId: Int, movieTitle: String, navController: NavHostController) {
    var isLoading by remember { mutableStateOf(true) }
    var details by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var reviews by remember { mutableStateOf<List<String>>(emptyList()) }
    var similar by remember { mutableStateOf<List<String>>(emptyList()) }
    var loadTime by remember { mutableStateOf(0L) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(movieId) {
        scope.launch {
            val time = measureTimeMillis {
                val detailsDeferred = async { fetchMovieDetails(movieId) }
                val reviewsDeferred = async { fetchMovieReviews(movieId) }
                val similarDeferred = async { fetchSimilarMovies(movieId) }

                details = detailsDeferred.await()
                reviews = reviewsDeferred.await()
                similar = similarDeferred.await()
            }
            loadTime = time
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movieTitle) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("<-")
                    }
                }
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Opis", style = MaterialTheme.typography.titleLarge)
                    details.forEach { (key, value) ->
                        Text("$key: $value")
                    }
                }

                item {
                    Text("Recenzje", style = MaterialTheme.typography.titleLarge)
                    reviews.forEach { review ->
                        Text("• $review")
                    }
                }

                item {
                    Text("Podobne filmy", style = MaterialTheme.typography.titleLarge)
                    similar.forEach { title ->
                        Text("$title")
                    }
                }

                item {
                    Spacer(Modifier.height(12.dp))
                    Text("Całkowity czas pobierania: ${loadTime / 1000.0} sekundy")
                }
            }
        }
    }
}
