package com.joaoreis.codewars.completedchallenges.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengesViewModel
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengesViewState

@Composable
fun ChallengeListScreen(
    viewModel: CompletedChallengesViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.viewState.collectAsState().value



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            is CompletedChallengesViewState.ChallengesLoaded -> {
                ChallengeList(items = state.challenges,
                    loadMoreAction = {
                        viewModel.loadMoreChallenges()
                                     },
                    onClickAction = { id -> navController.navigate("challenge/${id}") })
            }
            CompletedChallengesViewState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .testTag("ErrorMessage"),
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
                        .fillMaxHeight()
                        .testTag("LoadingSpinner"),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
