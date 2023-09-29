package com.joaoreis.codewars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joaoreis.codewars.challengedetails.ui.ChallengeDetailsScreen
import com.joaoreis.codewars.completedchallenges.ui.ChallengeListScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Codewars"
                )
            }
        )
    }, backgroundColor = Color.Transparent) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            NavHost(navController = navController, startDestination = "completed_challenges") {
                composable("completed_challenges") { ChallengeListScreen(navController = navController) }
                composable("challenge/{challengeId}",
                    arguments = listOf(navArgument("challengeId") { type = NavType.StringType })) { backStackEntry ->
                    ChallengeDetailsScreen(challengeId = backStackEntry.arguments?.getString("challengeId")!!)
                }
            }
        }
    }
}