package com.joaoreis.codewars.completedchallenges.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingMoreListItem() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("LoadMoreItem"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
fun LoadingMoreListItem_Preview() {
    LoadingMoreListItem()
}