package com.mchapagai.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CircleImageView(modifier: Modifier = Modifier, image: String) {
    AsyncImage(
        modifier = modifier
            .padding(end = 4.dp)
            .size(80.dp)
            .clip(CircleShape),
        model = image,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}