package com.joaoreis.codewars

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joaoreis.codewars.api.BaseUrlModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@UninstallModules(BaseUrlModule::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ChallengeDetailsComponentTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @BindValue
    @JvmField
    var baseUrl: String = ""

    @Test
    fun shouldShowChallengeDetailsWhenNavigatingToDetailsScreenAndDetailsAreAvailable() {
        val mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.enqueue(MockResponse().setBody(TestData.COMPLETED_CHALLENGES))
        mockWebServer.enqueue(MockResponse().setBody(TestData.CHALLENGE_DETAILS))

        baseUrl = mockWebServer.url("/").toString()
        hiltRule.inject()

        launch(MainActivity::class.java)

        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag("ChallengeItem")
            .get(0)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("ChallengeDetailsContent")
            .assert(hasAnyChild(hasText("Valid Braces", substring = true)))
            .assert(hasAnyChild(hasText("Write a function called `validBraces` that takes a string ...", substring = true)))


        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowErrorScreenWhenNavigatingtoDetailsSCreenAndChallengeDetailsFailToLoad() {
        val mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.enqueue(MockResponse().setBody(TestData.COMPLETED_CHALLENGES))
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        baseUrl = mockWebServer.url("/").toString()
        hiltRule.inject()

        launch(MainActivity::class.java)

        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag("ChallengeItem")
            .get(0)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("ChallengeDetailsError")
            .assertExists()


        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowLoadingScreenWhenNavigatingToDetailsScreenAndChallengeDetailsAreLoading() {
        val mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.enqueue(MockResponse().setBody(TestData.COMPLETED_CHALLENGES))
        mockWebServer.enqueue(MockResponse().setBodyDelay(5, TimeUnit.SECONDS).setBody(TestData.CHALLENGE_DETAILS))

        baseUrl = mockWebServer.url("/").toString()
        hiltRule.inject()

        launch(MainActivity::class.java)

        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag("ChallengeItem")
            .get(0)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("ChallengeDetailsLoading")
            .assertExists()

        mockWebServer.shutdown()
    }
}