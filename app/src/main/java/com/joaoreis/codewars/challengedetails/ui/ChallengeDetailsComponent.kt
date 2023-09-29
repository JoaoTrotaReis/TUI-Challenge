package com.joaoreis.codewars.challengedetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetails
import com.joaoreis.codewars.challengedetails.presentation.ChallengeDetailsUIModel

@Composable
fun ChallengeDetailsComponent(challengeDetails: ChallengeDetailsUIModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .testTag("ChallengeDetailsContent")
    ) {
        Text(text = challengeDetails.name,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = challengeDetails.description)
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = challengeDetails.languages.joinToString(", "))
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = challengeDetails.tags.joinToString(", "))
    }
}

@Composable
@Preview
fun ChallengeDetailsComponent_Preview() {
    val challengeDetails = ChallengeDetailsUIModel(
        name = "Challenge name",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In urna felis, pulvinar et orci et, rutrum vehicula tortor. Sed gravida eleifend nulla nec ultricies. Maecenas non odio sit amet enim facilisis pellentesque. Sed purus mauris, tristique elementum accumsan nec, sollicitudin vitae massa. Nunc nec aliquam lectus. Curabitur tempus, purus a finibus varius, eros nibh condimentum purus, sit amet condimentum quam ipsum vitae erat. Quisque pellentesque mauris leo. Suspendisse euismod mauris sem, in accumsan elit iaculis ac. Vestibulum justo mi, dapibus at elit suscipit, volutpat rutrum augue.",
        tags = listOf("Tag"),
        languages = listOf("Kotlin", "Java")
    )

    ChallengeDetailsComponent(challengeDetails = challengeDetails)
}