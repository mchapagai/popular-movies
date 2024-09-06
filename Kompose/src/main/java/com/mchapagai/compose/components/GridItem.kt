package com.mchapagai.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mchapagai.compose.R

@Composable
fun GridItem(image: String?) {
    Box(
        Modifier
            .padding(2.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.captain_america_poster),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// @Composable
//fun MovieGridItem(imageUrl: String) {
//    Box(
//        modifier = Modifier
//            .padding(4.dp)
//            .fillMaxWidth()
//    ) {
//        AsyncImage(
//            model = imageUrl,
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .aspectRatio(2 / 3f), // Adjust aspect ratio as needed
//            contentScale = ContentScale.Crop
//        )
//
//        IconButton(
//            onClick = { /* Handle favorite button click */ },
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(8.dp)
//        ) {
//            Image(
//                Icons.Filled.Favorite,
//                contentDescription = null,
//                modifier = Modifier.size(40.dp)
//            )
//        }
//    }
//}