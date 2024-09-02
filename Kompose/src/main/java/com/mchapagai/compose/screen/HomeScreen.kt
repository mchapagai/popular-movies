package com.mchapagai.compose.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mchapagai.compose.utils.MediaType

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var selectedMediaType by remember { mutableStateOf(MediaType.MOVIE) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HomeScreenTopBar(
            selectedMediaType = selectedMediaType,
            onMediaTypeSelected = { type ->
                selectedMediaType = type
            }
        )
        // Conditionally render ScreenA or ScreenB
        if (selectedMediaType == MediaType.MOVIE) {
            ScreenA(
                name = "Movies"
            )
        } else {
            ScreenB(
                name = "Tv Shows"
            )
        }
    }
}

@Composable
fun ScreenA(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun ScreenB(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "SCREENB $name!",
        modifier = modifier
    )
}

@Composable
fun HomeScreenTopBar(
    selectedMediaType: MediaType,
    onMediaTypeSelected: (MediaType) -> Unit
) {

    Row(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {

        Box(
            contentAlignment = Center
        ) {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Filled.Info,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(28.dp),
                    contentDescription = "profile picture"
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val mediaTypes = listOf(MediaType.MOVIE, MediaType.SHOW)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                mediaTypes.forEachIndexed { index, filmType ->
                    Text(
                        text = if (filmType == MediaType.MOVIE) "Movies" else "Tv Shows",
                        fontWeight = if (selectedMediaType == mediaTypes[index]) FontWeight.Bold else Light,
                        fontSize = if (selectedMediaType == mediaTypes[index]) 24.sp else 16.sp,
                        color = if (selectedMediaType == mediaTypes[index])
                            MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.78F),
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onMediaTypeSelected(mediaTypes[index])
                            }
                    )
                }
            }

            val animOffset = animateDpAsState(
                targetValue = when (mediaTypes.indexOf(selectedMediaType)) {
                    0 -> (-35).dp
                    else -> 30.dp
                },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy
                ), label = "DpAnimation"
            )

            Box(
                modifier = Modifier
                    .width(46.dp)
                    .height(2.dp)
                    .offset(x = animOffset.value)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }

        IconButton(
            onClick = { }
        ) {
            Icon(
                Icons.Filled.Search,
                modifier = Modifier.size(28.dp),
                contentDescription = "search icon",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
