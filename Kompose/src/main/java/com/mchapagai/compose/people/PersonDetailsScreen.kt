package com.mchapagai.compose.people

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.mchapagai.core.viewModel.PeopleViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mchapagai.compose.components.collapsingtoolbar.CollapsingHeader
import com.mchapagai.compose.components.collapsingtoolbar.CollapsingTitle
import com.mchapagai.compose.components.collapsingtoolbar.CollapsingToolBar
import com.mchapagai.compose.utils.Constants

@Composable
fun PersonDetailsScreen(
    personId: Int,
    viewModel: PeopleViewModel = viewModel(),
    onClickToolbar: () -> Unit,
) {

    val personDetails by remember { derivedStateOf { viewModel.personDetails } }
    val castItems by remember { derivedStateOf { viewModel.combinedPersonDetails } }

    LaunchedEffect(personId) {
        viewModel.fetchPersonDetails(personId)
        viewModel.fetchPersonCombinedDetails(personId)
    }

    val listState: LazyListState = rememberLazyListState()

    val headerHeightPx = with(receiver = LocalDensity.current) {
        Constants.headerHeight.toPx()
    }
    val toolbarHeightPx = with(receiver = LocalDensity.current) {
        Constants.toolbarHeight.toPx()
    }

    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0) {
                listState.firstVisibleItemScrollOffset >= toolbarBottom
            } else true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        CollapsingHeader(
            headerHeightPx = headerHeightPx,
            listState = listState,
            imagePath = "https://image.tmdb.org/t/p/w500/".plus(personDetails.value?.profilePath)
        )
        PersonDetailsContent(
            listState = listState,
            response = personDetails.value,
            castItems = castItems?.cast,
//            onClickItem = onItemClick
        )
        CollapsingToolBar(
//            showToolBar = showToolbar,
            canNavigateBack = true,
            onClickToolbar = onClickToolbar
        )
        CollapsingTitle(
            title = personDetails.value?.name ?: "",
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx,
            listState = listState,
            showToolBar = showToolbar
        )
    }
}
