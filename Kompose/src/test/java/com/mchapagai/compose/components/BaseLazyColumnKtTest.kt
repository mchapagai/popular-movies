package com.mchapagai.compose.components

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test


class BaseLazyColumnKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun baseLazyColumnTest() {
        // Start the app
        composeTestRule.setContent {
            BaseLazyColumn(listState = rememberLazyListState()) {
                item { Text("Hello World") }
                item { Text("Lazy Column") }
            }
        }
        composeTestRule.onNodeWithText("Hello World").assertExists()
        composeTestRule.onNodeWithText("Lazy Column").assertExists()
    }

}