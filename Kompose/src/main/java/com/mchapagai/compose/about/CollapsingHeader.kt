package com.mchapagai.compose.about


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.mchapagai.compose.R
import com.mchapagai.compose.utils.Constants


@Composable
fun CollapsingHeader(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    headerHeightPx: Float
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height = Constants.headerHeight)
            .graphicsLayer {
                // Parallax effect
                if (listState.firstVisibleItemIndex == 0) {
                    translationY = -listState.firstVisibleItemScrollOffset.toFloat() / 3
                    alpha = (-1f / headerHeightPx) * listState.firstVisibleItemScrollOffset + 1
                } else {
                    translationY = 0f
                    alpha = 0f
                }
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = R.drawable.icon_robot)
                    .apply(block = fun ImageRequest.Builder.() {
                        transformations(
                            CircleCropTransformation()
                        )
                    }).build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            Color.Transparent,
//                            Color.Transparent,
//                            MaterialTheme.colorScheme.background
//                        )
//                    )
//                )
        )
    }
}