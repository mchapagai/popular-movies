package com.mchapagai.compose.components.collapsingtoolbar


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mchapagai.compose.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolBar(
    modifier: Modifier = Modifier,
//    showToolBar: Boolean,
    canNavigateBack: Boolean,
    onClickToolbar: () -> Unit = {}
) = AnimatedVisibility(
//    visible = showToolBar,
    visible = true,
    enter = fadeIn(animationSpec = tween(durationMillis = 500)),
    exit = fadeOut(animationSpec = tween(durationMillis = 500)),
    modifier = modifier
) {
    if (canNavigateBack) {
        TopAppBar(
            modifier = Modifier
//                .background(color = MaterialTheme.colorScheme.background)
                .statusBarsPadding(),
            navigationIcon = {
                IconButton(
                    onClick = onClickToolbar,
                    modifier = Modifier
                        .padding(horizontal = Constants.doubleContentPadding)
                        .size(size = 36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            title = { },
        )
    }
}