package com.mchapagai.compose.about


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mchapagai.compose.utils.Constants

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit = {}
) {
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
        modifier = modifier
            .fillMaxSize()
//            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        CollapsingHeader(
            headerHeightPx = headerHeightPx,
            listState = listState
        )
        AboutScreenContent(
            listState = listState
        )
        CollapsingToolBar(
            showToolBar = true,
            canNavigateBack = true,
            onclick = onBackPress
        )
        CollapsingTitle(
            title = "About",
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx,
            listState = listState,
            showToolBar = showToolbar
        )
    }
}

@Composable
fun AboutScreenContent(
    modifier: Modifier = Modifier,
    listState: LazyListState,
) = LazyColumn(
    modifier = modifier.wrapContentSize(),
    state = listState,
    contentPadding = PaddingValues(
        bottom = Constants.navigationBarInsetDp,
        top = Constants.statusBarInsetDp / 2
    ),
    content = {
        item {
            Spacer(
                modifier = Modifier.height(height = Constants.headerHeight)
            )
        }
        item {
            AboutContent("\u00A9 Manorath Chapagai | All rights reserved.")
        }
        item {
            LinkifyText(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = "https://github.com/mchapagai/popular-movies",
            )
        }
        item {
            AboutContent("Application will allow user to discover movies and shows. The application uses Model-View-ViewModel architecture.")
        }
        item {
            AboutContent("Android is a trademark of Google Inc.")
        }
        item {
            HorizontalDivider(thickness = 2.dp/*, color = Color.Yellow*/)
        }
        item {
            LicensesScreen(content = AboutModel())
        }
    }
)

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AboutScreen(modifier: Modifier = Modifier) {
//    val scrollState = rememberScrollState()
//    Scaffold(
//        modifier =
//        modifier.fillMaxSize(),
//        topBar = {
//            Column(
//                modifier = modifier
//                    .height(256.dp)
//                    .fillMaxWidth()
//            ) {
//                // CollapsingToolbarLayout equivalent
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(256.dp)
//                        .verticalScroll(scrollState) // Enable scrolling
//                        .background(MaterialTheme.colorScheme.primary) // Set contentScrim color
//                ) {
//                    // Parallax effect with Box
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(256.dp)
//                    ) {
//                        // Background image
//                        Image(
//                            painter = painterResource(id = R.drawable.icon_background_drawable),
//                            contentDescription = null,
//                            modifier = modifier
//                                .fillMaxSize()
//                                .graphicsLayer {
//                                    // Parallax effect
//                                    translationY = -scrollState.value * 0.5f
//                                }
//                        )
//                        CircleImageView(
//                            modifier = Modifier
//                                .size(96.dp)
//                                .align(Alignment.Center)
//                        )
//                        Text(
//                            text = stringResource(id = R.string.app_name),
//                            modifier = Modifier
//                                .padding(top = 16.dp)
//                                .align(Alignment.BottomCenter),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//                // Toolbar
//                TopAppBar(
//                    title = { Text("About") },
//                )
//            }
//        }
//    ) { innerPadding ->
//        // use innerPadding to avoid overlap with the app bar
//        LazyColumn(
//            modifier = modifier
//                .padding(innerPadding)
//        ) {
//            // Your content here
//
//            item {
//                Text("About Screen Content")
//            }
//        }
//    }
//}
//
//@Composable
//fun CircleImageView(modifier: Modifier = Modifier) {
//    Image(
//        painter = painterResource(R.drawable.icon_robot),
//        contentDescription = "Circle Image",
//        contentScale = ContentScale.Crop,
//        modifier = modifier
//            .size(96.dp)
//            .clip(CircleShape) // clip to the circle shape
//            .border(5.dp, Color.Gray, CircleShape)//optional
//    )
//}