package com.openappslabs.fiveg.ui.navigation

import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openappslabs.fiveg.ui.screens.aboutscreen.AboutScreen
import com.openappslabs.fiveg.ui.screens.homescreen.HomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home,
            enterTransition = Navimation.enterTransition,
            exitTransition = Navimation.exitTransition,
            popEnterTransition = Navimation.popEnterTransition,
            popExitTransition = Navimation.popExitTransition
        ) {
            composable<Screen.Home> { backStackEntry ->
                HomeScreen(onAboutClick = {
                    if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        navController.navigate(Screen.About)
                    }
                })
            }
            composable<Screen.About> { backStackEntry ->
                AboutScreen(onBackClick = {
                    if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                })
            }
        }
    }
}