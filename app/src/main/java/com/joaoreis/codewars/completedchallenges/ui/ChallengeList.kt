package com.joaoreis.codewars.completedchallenges.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengeListItem
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengeUIModel
import com.joaoreis.codewars.completedchallenges.presentation.LoadMoreError
import com.joaoreis.codewars.completedchallenges.presentation.LoadingMore

@Composable
fun ChallengeList(
    items: List<CompletedChallengeListItem>,
    loadMoreAction: () -> Unit = {},
    onClickAction: (String) -> Unit = {}
) {
    val lazyColumnListState = rememberLazyListState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -6) >=
                    (lazyColumnListState.layoutInfo.totalItemsCount - 5)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value) {
            loadMoreAction()
        }
    }

    LazyColumn(
        Modifier.testTag("ChallengesList"),
        state = lazyColumnListState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) {
            when (it) {
                is CompletedChallengeUIModel -> {
                    ChallengeListItem(
                        Modifier.clickable { onClickAction(it.id) },
                        name = it.name,
                        completedAt = it.completedAt,
                        languages = it.languages
                    )
                }
                LoadMoreError -> {
                    LoadMoreErrorItem(
                        Modifier.clickable { loadMoreAction() }
                    )
                }
                LoadingMore -> {
                    LoadingMoreListItem()
                }
            }

        }
    }
}

@Preview
@Composable
fun ChallengeList_Preview() {
    val list = listOf(
        CompletedChallengeUIModel(
            id = "id",
            name = "First challenge",
            completedAt = "01/01/2023",
            languages = listOf("kotlin, java")
        ),
        CompletedChallengeUIModel(
            id = "id",
            name = "Second challenge",
            completedAt = "01/01/2023",
            languages = listOf("javascript")
        )
    )
    ChallengeList(items = list)
}