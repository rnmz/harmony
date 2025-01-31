package dev.runo.harmony.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(controller: NavController, paddingValues: PaddingValues) {
    NavHost(
        navController = controller as NavHostController,
        startDestination = Home,
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        composable<Home> {  }
        composable<Latest> {  }
        composable<Info> {  }
    }
}

@Serializable
object Home

@Serializable
object Latest

@Serializable
object Info

