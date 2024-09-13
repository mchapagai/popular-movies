package com.mchapagai.compose.people.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mchapagai.core.model.PeopleCombinedCreditModel
import com.mchapagai.core.response.people.CastCreditResponse


/**
 * [Column] arranges tje widgets vertically.
 * The background gradient is applied to the [Image] using [Brush.linearGradient].
 * [TextOverflow] and MaxLines are used to handle text truncation and limit the number of lines
 * displayed for the title, subtitle, and caption.
 */

@Composable
fun PersonDetailsItemScreen(
    modifier: Modifier = Modifier,
    casts: PeopleCombinedCreditModel?
) {
    Card(
        modifier = modifier
            .wrapContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(casts?.getProfilePath())
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
            )
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                PersonDetailsItems(text = casts?.title ?: "")
                PersonDetailsItems(text = casts?.character ?: "")
                PersonDetailsItems(text = casts?.releaseDateYearOnly() ?: "")
            }
        }
    }
}