package com.mchapagai.compose.movies

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mchapagai.compose.R
import com.mchapagai.core.response.common.VideoResponse

private val MOVIE_DETAILS_VIDEO_WIDTH = 180.dp
private val MOVIE_DETAILS_VIDEO_HEIGHT = 100.dp

@Composable
fun TrailerView(videos: List<VideoResponse>) {
    val context = LocalContext.current

    LazyRow(
        horizontalArrangement = Arrangement
            .spacedBy(4.dp)
    ) {
        items(videos.size) { video ->
            MovieVideoItem(
                thumbnailUrl = videos[video].thumbnailUrl(),
                title = videos[video].name
            ) {
                if (videos[video].isYoutubeVideo()) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + videos[video].key)
                    )
                    startActivity(context, intent, null)
                }
            }
        }
    }
}
@Composable
fun MovieVideoItem(
    thumbnailUrl: String,
    title: String,
    onVideoClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(MOVIE_DETAILS_VIDEO_WIDTH)
            .height(MOVIE_DETAILS_VIDEO_HEIGHT)
            .clickable {
                onVideoClick()
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Trailer",
            placeholder = painterResource(id = R.drawable.captain_america_poster),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = painterResource(id = R.drawable.icon_play),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
        )

        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 16.dp)
                .fillMaxWidth(),
        )
    }
}