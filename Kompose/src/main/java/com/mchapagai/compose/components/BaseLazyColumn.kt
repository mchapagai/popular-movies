package com.mchapagai.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A reusable base composable for a LazyColumn with standard margins applied.
 *
 * @param modifier The modifier to be applied to the [LazyColumn].
 * @param listState The LazyListState to be used for the [LazyColumn].
 * @param content The content to be displayed within the [LazyColumn].
 */

@Composable
fun BaseLazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp), // Standard margin
        state = listState,
        content = content
    )
}