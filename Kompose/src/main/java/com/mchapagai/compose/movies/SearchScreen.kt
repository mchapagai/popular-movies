package com.mchapagai.compose.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mchapagai.core.response.search.SearchResultResponse
import com.mchapagai.core.viewModel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onPressBack: () -> Unit
) {
    val searchState by viewModel.searchState.collectAsState()
    var query by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopAppBar(
            title = {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.background,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                    ),
                )
            },
            navigationIcon = {
                IconButton(onClick = onPressBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = {
                    viewModel.executeSearch(query)
                    focusManager.clearFocus()
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        )
        when (searchState) {
            is SearchViewModel.SearchState.Idle -> {
                // Show initial state or placeholder
            }

            is SearchViewModel.SearchState.Loading -> {
                // Show loading indicator
            }

            is SearchViewModel.SearchState.Success -> {
                // Display search results
                val results = (searchState as SearchViewModel.SearchState.Success).results
                SearchResultsList(results = results)
            }

            is SearchViewModel.SearchState.Error -> {
                // Show error message
                val error = (searchState as SearchViewModel.SearchState.Error).throwable
                Text("Search Error: ${error.message}")
            }
        }
    }
}

@Composable
fun SearchResultsList(results: List<SearchResultResponse>) {
    LazyColumn {
        items(results) { result ->
            SearchResultItem(result)
        }
    }
}

@Composable
fun SearchResultItem(result: SearchResultResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = result.title ?: "", fontWeight = FontWeight.Bold)
                Text(text = result.mediaType ?: "")
                Text(text = result.overview ?: "")
            }
            Spacer(modifier = Modifier.width(16.dp))
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/".plus(result.posterPath),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}