package com.mchapagai.compose.people

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mchapagai.compose.components.collapsingtoolbar.CollapsingHeader
import com.mchapagai.compose.components.collapsingtoolbar.CollapsingTitle
import com.mchapagai.compose.components.collapsingtoolbar.CollapsingToolBar
import com.mchapagai.compose.components.collapsingtoolbar.rememberToolbarState
import com.mchapagai.compose.utils.Constants
import com.mchapagai.core.viewModel.PeopleViewModel

@Composable
fun PersonDetailsScreen(
    personId: Int,
    viewModel: PeopleViewModel = viewModel(),
    onClickToolbar: () -> Unit,
) {

     // [derivedStateOf] is a function that returns a [State] when the underlying state changes
    val personDetails by remember { derivedStateOf { viewModel.personDetails } }
    val castItems by remember { derivedStateOf { viewModel.combinedPersonDetails } }
    val isLoading by viewModel.isLoading

    LaunchedEffect(personId) {
        viewModel.fetchPersonDetails(personId)
        viewModel.fetchPersonCombinedDetails(personId)
    }

    val listState: LazyListState = rememberLazyListState()
    val (headerHeightPx, showToolbar) = rememberToolbarState(
        listState = listState,
        headerHeight = Constants.headerHeight,
        toolbarHeight = Constants.toolbarHeight
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        CollapsingHeader(
            headerHeightPx = headerHeightPx,
            listState = listState,
            imagePath = Constants.SECURE_IMAGE_ENDPOINT.plus(personDetails.value?.profilePath)
        )
        PersonDetailsContent(
            listState = listState,
            response = personDetails.value,
            castItems = castItems.value,
            isLoading = isLoading
        )
        CollapsingToolBar(
            canNavigateBack = true,
            onClickToolbar = onClickToolbar
        )
        CollapsingTitle(
            title = personDetails.value?.name ?: "",
            headerHeightPx = headerHeightPx,
            toolbarHeight = Constants.toolbarHeight,
            listState = listState,
            showToolBar = showToolbar
        )
    }
}
