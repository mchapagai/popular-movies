package com.mchapagai.compose.people.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun PersonDetailsItems(text: String) {
    val maxChars = 20
    val truncatedText = remember(text) {
        if (text.length > maxChars) {
            AnnotatedString("${text.substring(0, maxChars)}...")
        } else {
            AnnotatedString(text)
        }
    }

    Text(
        text = truncatedText,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}