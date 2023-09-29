package com.joaoreis.codewars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joaoreis.codewars.challengedetails.ui.ChallengeDetailsScreen
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengesViewModel
import com.joaoreis.codewars.completedchallenges.ui.ChallengeListScreen
import com.joaoreis.codewars.ui.theme.CodewarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodewarsTheme {
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
        }
    }
}