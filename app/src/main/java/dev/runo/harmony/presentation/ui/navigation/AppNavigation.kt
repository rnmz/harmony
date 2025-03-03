package dev.runo.harmony.presentation.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.runo.harmony.presentation.ui.screens.home.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(controller: NavController, paddingValues: PaddingValues) {
    NavHost(
        navController = controller as NavHostController,
        startDestination = HomeScreenRoute,
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        composable<HomeScreenRoute> {
            HomeScreen()
        }
    }
}

@Serializable
object HomeScreenRoute