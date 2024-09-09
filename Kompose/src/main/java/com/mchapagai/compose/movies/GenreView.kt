package com.mchapagai.compose.movies

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mchapagai.compose.components.GenreItem
import com.mchapagai.core.response.common.GenresResponse

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreView(tags: List<GenresResponse>) {
    FlowRow(
        modifier = Modifier.padding(8.dp)
    ) {
        tags.forEach { tag ->
            GenreItem(text = tag.genreName)
        }
    }
}

@Preview
@Composable
fun GenrePreview() {
    val tags = ArrayList<GenresResponse>()
    tags.add(GenresResponse("Actions", 1))
    tags.add(GenresResponse("Adventures", 2))
    GenreView(tags)
}
