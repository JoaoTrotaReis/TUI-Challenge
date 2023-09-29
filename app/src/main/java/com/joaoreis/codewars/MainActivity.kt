package com.joaoreis.codewars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengesViewModel
import com.joaoreis.codewars.completedchallenges.ui.ChallengeListScreen
import com.joaoreis.codewars.ui.theme.CodewarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CompletedChallengesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodewarsTheme {
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
                        ChallengeListScreen(viewModel)
                    }
                }
            }
        }
    }
}