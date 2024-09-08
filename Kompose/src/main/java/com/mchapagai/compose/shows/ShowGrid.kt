package com.mchapagai.compose.shows

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mchapagai.compose.components.ShowGridItem
import com.mchapagai.core.viewModel.ShowViewModel

@Composable
fun ShowGrid(viewModel: ShowViewModel = viewModel()) {
    val listState = rememberLazyGridState()
    val shows by remember { derivedStateOf { viewModel.shows } }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        state = listState
    ) {
        items(shows) { show ->
            ShowGridItem(show.getFullPosterPath())
        }
    }

    // Infinite scroll logic
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= shows.size - 5) {
                    viewModel.fetchShows()
                }
            }
    }
}