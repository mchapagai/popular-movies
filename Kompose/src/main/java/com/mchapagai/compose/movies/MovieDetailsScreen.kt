package com.mchapagai.compose.movies

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.mchapagai.compose.R
import com.mchapagai.core.model.MovieCombinedCreditModel
import com.mchapagai.core.response.common.ReviewResponse
import com.mchapagai.core.response.common.VideoResponse
import com.mchapagai.core.response.movies.MovieDetailsResponse
import com.mchapagai.core.viewModel.MovieViewModel
import java.util.Locale

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieViewModel = viewModel(),
    onPressBack: () -> Unit
) {

    val movies by remember { derivedStateOf { viewModel.movieDetails } }
    val creditDetails by remember { derivedStateOf { viewModel.combinedCredits } }
    val videos by remember { derivedStateOf { viewModel.videoList } }
    val reviews by remember { derivedStateOf { viewModel.reviewList } }

    LaunchedEffect(key1 = movieId) {
        viewModel.fetchMovieDetails(movieId = movieId)
        viewModel.fetchMovieCreditDetailsByCreditId(movieId = movieId)
        viewModel.fetchMovieVideos(movieId = movieId)
        viewModel.fetchMovieReviews(movieId = movieId)
    }

    val scrollState = rememberScrollState()
    var detailScroller by remember {
        mutableStateOf(DetailsScroller(scrollState, Float.MIN_VALUE))
    }

    val transitionState =
        remember(detailScroller) { detailScroller.toolbarTransitionState }
    val toolbarState = detailScroller.getToolbarState(LocalDensity.current)

    // Transition that fades in/out the header with the image and the Toolbar
    val transition = rememberTransition(transitionState, label = "")
    val contentAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }, label = ""
    ) { toolbarTransitionState ->
        if (toolbarTransitionState == ToolbarState.HIDDEN) 1f else 0f
    }

    Box(Modifier.fillMaxSize()) {
        // TODO make movies.value NOT optional
        movies.value?.let {
            DetailsContent(
                scrollState = scrollState,
                onNamePosition = { newNamePosition ->
                    // Comparing to Float.MIN_VALUE as we are just interested on the original
                    // position of name on the screen
                    if (detailScroller.namePosition == Float.MIN_VALUE) {
                        detailScroller =
                            detailScroller.copy(namePosition = newNamePosition)
                    }
                },
                movie = it,
                creditDetails,
                videos = videos?.videoList ?: emptyList(),
                reviews = reviews?.reviewList ?: emptyList(),
                contentAlpha = { contentAlpha.value }
            )
        }
        DetailsToolbar(
            toolbarState, movies.value?.title ?: "",
            pressOnBack = onPressBack,
            contentAlpha = { contentAlpha.value }
        )
    }
}

@Composable
private fun DetailsToolbar(
    toolbarState: ToolbarState,
    name: String,
    pressOnBack: () -> Unit,
    contentAlpha: () -> Float
) {
    if (toolbarState.isShown) {
        DetailsToolbar(
            name = name,
            onBackClick = pressOnBack,
        )
    } else {
        HeaderActions(
            onBackClick = pressOnBack,
            modifier = Modifier.alpha(contentAlpha())
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsToolbar(
    name: String,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        },
        title = {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.CenterStart)
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun HeaderActions(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val iconModifier = Modifier
            .sizeIn(
                maxWidth = 32.dp,
                maxHeight = 32.dp
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(start = 12.dp)
                .then(iconModifier)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
private fun DetailsContent(
    scrollState: ScrollState,
    onNamePosition: (Float) -> Unit,
    movie: MovieDetailsResponse,
    creditModel: List<MovieCombinedCreditModel>,
    videos: List<VideoResponse>,
    reviews: List<ReviewResponse>,
    contentAlpha: () -> Float,
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier.height(240.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.getFullBackdropPath()),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.medium)
                    .alpha(contentAlpha()),
                contentScale = ContentScale.FillWidth,
            )
        }
        Spacer(
            Modifier
                .onGloballyPositioned { onNamePosition(it.positionInWindow().y) })

        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.tagline,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(icon = Icons.Filled.DateRange, text = movie.getFormattedReleaseDate())
            InfoItem(
                icon = Icons.Filled.Star, text = stringResource(
                    id = R.string.details_rating_votes_count,
                    String.format(Locale.US, "%.2f", movie.voteAverage),
                    movie.voteCount ?: Any()
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium
        )
        GenreView(movie.genres)
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(16.dp))
        CreditsView(credits = creditModel, onClick = {})
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(16.dp))
        TrailerView(videos = videos)
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(16.dp))
        ReviewScreen(review = reviews)
    }
}

@Composable
fun InfoItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}