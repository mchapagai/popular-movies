package com.mchapagai.compose.people


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mchapagai.compose.utils.Constants
import com.mchapagai.core.response.people.CastCreditResponse
import com.mchapagai.core.response.people.PersonResponse

/**
 * [LazyColumn] is used for efficient vertical scrolling and item composition.
 */
@Composable
fun PersonDetailsContent(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    response: PersonResponse?,
    castItems: List<CastCreditResponse>?,
//    onClickItem: () -> Unit = {},
) = LazyColumn(
    state = listState,
    contentPadding = PaddingValues(
        bottom = Constants.navigationBarInsetDp,
        top = Constants.statusBarInsetDp / 2
    ),
    content = {
        item {
            Spacer(
                modifier = modifier.height(height = Constants.headerHeight)
            )
        }
        item {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = response?.name ?: "",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = response?.knownForDepartment ?: "",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        item {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = response?.formattedBirthday ?: "", textAlign = TextAlign.Center)
                VerticalDivider()
                if (response != null) {
                    Text(text = response.placeOfBirth, textAlign = TextAlign.Center)
                }
            }
        }
        item {
            Text(
                text = response?.biography ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
        item {
            Text(
                text = "Cast/Credits",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
        }
        item {
            CreditCastView(castItems = castItems)
        }
    }
)

@Composable
fun CreditCastView(castItems: List<CastCreditResponse>?) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (castItems != null) {
            if (castItems.isNotEmpty()) {
                castItems.forEach { cast ->
                    PersonDetailsItemScreen(
                        image = cast.getProfilePath(),
                        title = cast.title ?: "",
                        subtitle = cast.character ?: "",
                        caption = cast.releaseDateYearOnly()
                    )
                }
            }
        }
    }
}

/**
 * [Column] arranges tje widgets vertically.
 * The background gradient is applied to the [Image] using [Brush.linearGradient].
 * [TextOverflow] and MaxLines are used to handle text truncation and limit the number of lines
 * displayed for the title, subtitle, and caption.
 */

@Composable
fun PersonDetailsItemScreen(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    subtitle: String,
    caption: String?,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), // Replace with your gradient colors
                            start = Offset.Zero,
                            end = Offset.Infinite
                        )
                    )
            )
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                ItemTextView(text = title)
                ItemTextView(text = subtitle)
                ItemTextView(text = caption ?: "")
            }
        }
    }
}


@Composable
fun ItemTextView(text: String) {

    val maxChars = 25
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