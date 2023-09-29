package com.joaoreis.codewars.completedchallenges.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengeUIModel

@Composable
fun ChallengeList(items: List<CompletedChallengeUIModel>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items) {
            ChallengeListItem(name = it.name, completedAt = it.completedAt, languages = it.languages)
        }
    }
}

@Preview
@Composable
fun ChallengeList_Preview() {
    val list = listOf(
        CompletedChallengeUIModel(name = "First challenge", completedAt = "01/01/2023", languages = listOf("kotlin, java")),
        CompletedChallengeUIModel(name = "Second challenge", completedAt = "01/01/2023", languages = listOf("javascript"))
    )
    ChallengeList(items = list)
}