package com.joaoreis.codewars.challengedetails.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.joaoreis.codewars.challengedetails.presentation.ChallengeDetailsViewModel
import com.joaoreis.codewars.challengedetails.presentation.ChallengeDetailsViewState

@Composable
fun ChallengeDetailsScreen(
    viewModel: ChallengeDetailsViewModel = hiltViewModel(),
    challengeId: String
) {
    val state = viewModel.viewState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadChallengeDetails(challengeId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("ChallengeDetails")
    ) {
        when (state) {
            is ChallengeDetailsViewState.ChallengeDetailsLoaded -> {
                ChallengeDetailsComponent(challengeDetails = state.challenge)
            }

            ChallengeDetailsViewState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .testTag("ChallengeDetailsError"),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("There was an error loading the data")
                }
            }

            ChallengeDetailsViewState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .testTag("ChallengeDetailsLoading"),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
@Preview
fun ChallengeDetailsScreen_Preview() {

}