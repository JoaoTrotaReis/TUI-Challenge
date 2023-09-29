package com.joaoreis.codewars.completedchallenges.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaoreis.codewars.ui.theme.CodewarsTheme

@Composable
fun ChallengeListItem(
    modifier: Modifier = Modifier,
    name: String,
    completedAt: String,
    languages: List<String>
) {
    Surface(elevation = 3.dp) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("ChallengeItem")
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold
            )
            Text(text = languages.joinToString(", "))
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = completedAt)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CodewarsTheme {
        ChallengeListItem(
            name = "Name of the challenge",
            completedAt = "22/09/2023",
            languages = listOf("kotlin, java, javascript")
        )
    }
}