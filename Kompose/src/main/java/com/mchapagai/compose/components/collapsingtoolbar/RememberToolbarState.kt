package com.mchapagai.compose.components.collapsingtoolbar

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

/**
 * Calculates and remembers the header height in pixels and the visibility of the toolbar based on
 * the scroll state of a LazyList
 * @param listState The [LazyListState] of the scrollable content. The [LazyListState] is used to
 * determine the scroll position and calculate the visibility of the first item.
 * @param headerHeight The height of the header section in [Dp]
 * @param toolbarHeight The height of the toolbar in [Dp]
 *
 * @return A [Pair] containing the header height in pixels and boolean indication whether the
 * toolbar should be shown
 */
@Composable
fun rememberToolbarState(
    listState: LazyListState,
    headerHeight: Dp,
    toolbarHeight: Dp
): Pair<Float, Boolean> {
    /*
     * headerHeightPx and toolbarHeightPx converts the headerHeight and toolbarHeight from [Dp] to
     * pixels using LocalDensity.current. This is necessary because scroll offsets are measured in
     * pixels
     */
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }
    val toolbarBottom = headerHeightPx - toolbarHeightPx

    /*
     * derivedStateOf creates a state variable that is automatically updated when the values it
     * depends on change.
     * derivedStateOf checks if the first visible item is scrolled beyond the toolbar bottom
     * position. If it is, showToolbar is set to true, indicating that the toolbar should be
     * visible. Otherwise, it is set to false
     */
    val showToolbar by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0) {
                listState.firstVisibleItemScrollOffset >= toolbarBottom
            } else true
        }
    }
    return Pair(headerHeightPx, showToolbar)
}