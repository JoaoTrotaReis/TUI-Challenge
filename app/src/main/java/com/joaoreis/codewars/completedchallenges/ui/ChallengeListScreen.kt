package com.joaoreis.codewars.completedchallenges.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengesViewModel
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengesViewState

@Composable
fun ChallengeListScreen(viewModel: CompletedChallengesViewModel) {
    val state = viewModel.viewState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadCompletedChallenges()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when(state) {
            is CompletedChallengesViewState.ChallengesLoaded -> {
                ChallengeList(items = state.challenges)
            }
            CompletedChallengesViewState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("There was an error loading the data")
                }
            }
            CompletedChallengesViewState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
