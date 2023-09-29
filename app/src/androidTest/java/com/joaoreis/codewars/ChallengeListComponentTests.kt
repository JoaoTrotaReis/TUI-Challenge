package com.joaoreis.codewars

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joaoreis.codewars.api.BaseUrlModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@UninstallModules(BaseUrlModule::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ChallengeListComponentTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @BindValue
    @JvmField
    var baseUrl: String = ""

    @Test
    fun shouldShowLoadingScreenWhenLaunchedAndShipmentsAreLoading() {
        val mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        baseUrl = mockWebServer.url("/").toString()
        hiltRule.inject()

        launch(MainActivity::class.java)

        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("LoadingSpinner")
            .assertExists()

        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowErrorScreenWhenLaunchedAndShipmentsFailToLoad() {
        val mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        baseUrl = mockWebServer.url("/").toString()
        hiltRule.inject()

        launch(MainActivity::class.java)

        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("ErrorMessage")
            .onChildren()
            .filter(hasText("There was an error loading the data"))

        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowShipmentsWhenLaunchedAndShipmentsAreLoaded() {
        val mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.enqueue(MockResponse().setBody(TestData.COMPLETED_CHALLENGES))

        baseUrl = mockWebServer.url("/").toString()
        hiltRule.inject()

        launch(MainActivity::class.java)

        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag("ChallengeItem")
            .assertCountEquals(1)

        composeTestRule.onAllNodesWithTag("ChallengeItem")
            .get(0)
            .assert(hasText("Multiples of 3 and 5", substring = true))

        mockWebServer.shutdown()
    }

    @Test
    fun shouldNavigateToChallengeDetailsWhenChallengeIsTapped() {
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

        composeTestRule
            .onNodeWithTag("ChallengeDetails")
            .assertExists()

        mockWebServer.shutdown()
    }
}