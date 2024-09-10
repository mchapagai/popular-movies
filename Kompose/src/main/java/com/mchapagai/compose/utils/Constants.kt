package com.mchapagai.compose.utils


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Constants {
    const val SECURE_IMAGE_ENDPOINT: String = "https://image.tmdb.org/t/p/w500/"
    const val PERSON_ID_INTENT: String = "profile_id"
    const val SECURE_BASE_URL: String = "https://image.tmdb.org/t/p/w342"
    val doubleContentPadding: Dp = 16.dp

    val contentPadding: Dp = 8.dp

    val headerHeight: Dp = 250.dp
    val toolbarHeight: Dp = 56.dp

    val titlePaddingStart: Dp = 16.dp
    val titlePaddingEnd: Dp = 72.dp

    const val titleFontScaleStart = 1f
    const val titleFontScaleEnd = 0.66f


    val navigationBarInsetDp: Dp
        @Composable
        get() = with(LocalDensity.current) {
            WindowInsets.navigationBars.getBottom(density = this).toDp()
        }

    val statusBarInsetDp: Dp
        @Composable
        get() = with(LocalDensity.current) {
            WindowInsets.statusBars.getTop(density = this).toDp()
        }
}
