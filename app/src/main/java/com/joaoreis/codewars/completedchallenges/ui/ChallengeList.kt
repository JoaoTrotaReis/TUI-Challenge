package com.joaoreis.codewars.completedchallenges.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengeUIModel

@Composable
fun ChallengeList(items: List<CompletedChallengeUIModel>, onClickAction: (String) -> Unit = {}) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items) {
            ChallengeListItem(
                Modifier.clickable { onClickAction(it.id) },
                name = it.name,
                completedAt = it.completedAt,
                languages = it.languages)
        }
    }
}

@Preview
@Composable
fun ChallengeList_Preview() {
    val list = listOf(
        CompletedChallengeUIModel(id = "id", name = "First challenge", completedAt = "01/01/2023", languages = listOf("kotlin, java")),
        CompletedChallengeUIModel(id = "id", name = "Second challenge", completedAt = "01/01/2023", languages = listOf("javascript"))
    )
    ChallengeList(items = list)
}