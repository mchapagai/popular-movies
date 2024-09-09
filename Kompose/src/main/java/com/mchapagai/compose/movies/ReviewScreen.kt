package com.mchapagai.compose.movies


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mchapagai.core.response.common.ReviewResponse

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReviewScreen(review: List<ReviewResponse>, modifier: Modifier = Modifier) {
    FlowColumn {
        if (review.isNotEmpty()) {
            review.forEach { review ->
                ReviewItems(review = review)
            }
        }
    }
}

@Composable
fun ReviewItems(review: ReviewResponse, modifier: Modifier = Modifier) {
    Column {
        UnderlinedBoldText(text = review.author)
        Text(
            text = review.content,
            maxLines = 3,
        )
    }
}

@Composable
fun UnderlinedBoldText(text: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append(text)
            }
        },
        textDecoration = TextDecoration.Underline
    )
}

@Preview
@Composable
fun ReviewScreenPreview() {
    ReviewScreen(
        listOf(
            ReviewResponse(
                author = "Author",
                content = "Content",
                reviewId = "",
                url = ""
            )
        )
    )
}