package com.mchapagai.compose.people

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mchapagai.compose.people.views.PersonCreditsView
import com.mchapagai.compose.utils.Constants
import com.mchapagai.core.model.PeopleCombinedCreditModel
import com.mchapagai.core.response.people.PersonResponse

/**
 * [LazyColumn] is used for efficient vertical scrolling and item composition.
 */
@Composable
fun PersonDetailsContent(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    response: PersonResponse?,
    castItems: List<PeopleCombinedCreditModel>?,
    isLoading: Boolean
) = LazyColumn(
    state = listState,
    modifier = modifier
        .padding(16.dp)
) {
    if (isLoading) {
        item {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    } else if (response == null) {
        item {
            Text(
                text = "Error loading data",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
    } else {
        item {
            Spacer(
                modifier = modifier.height(height = Constants.headerHeight)
            )
        }
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = response.personName(),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = response.birthDayAndPlace(),
                    textAlign = TextAlign.Center
                )
            }
        }
        item {
            Text(
                text = response.biography,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Justify
            )
        }
        item {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Cast/Credits")
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
                    .drawBehind {
                        val gradient = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Yellow,
                                Color.DarkGray
                            )
                        )
                        drawLine(
                            brush = gradient,
                            strokeWidth = 2f,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height)
                        )
                    },
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
        }
        item {
            PersonCreditsView(castItems = castItems)
        }
    }
}

