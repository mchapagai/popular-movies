package com.mchapagai.compose.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mchapagai.compose.components.CircleImageView
import com.mchapagai.core.model.MovieCombinedCreditModel

@Composable
fun CreditsView(
    credits: List<MovieCombinedCreditModel>,
    onClickCreditItem: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(scrollState)) {
        if (credits.isNotEmpty()) {
            credits.forEach { credit ->
                CreditItem(credit = credit) { creditId ->
                    onClickCreditItem(creditId)
                }
            }
        }

    }
}

@Composable
fun CreditItem(
    credit: MovieCombinedCreditModel,
    onClickCreditItem: (Int) -> Unit
) {
    Column {
        if (credit.profileImagePath != null) {
            CircleImageView(
                image = "https://image.tmdb.org/t/p/w500/" + credit.profileImagePath,
                modifier = Modifier.clickable {
                    onClickCreditItem(credit.creditId)
                }
            )
        } else {
            CircleImageView(image = "https://image.tmdb.org/t/p/w500/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg")
        }
        CreditNameView(name = credit.name)
        CreditInfoView(name = credit.description)
    }
}

@Composable
fun CreditNameView(modifier: Modifier = Modifier, name: String) {
    Text(
        text = name,
        modifier.padding(top = 4.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 14.sp
    )
}

@Composable
fun CreditInfoView(modifier: Modifier = Modifier, name: String) {
    Text(
        text = name,
        modifier.padding(top = 4.dp),
        maxLines = 1,
        fontSize = 12.sp
    )
}

@Preview
@Composable
fun CreditsViewPreview() {

    val response = MovieCombinedCreditModel(
        creditId = 1,
        name = "Actor Name",
        description = "Description",
        profileImagePath = "https://image.tmdb.org/t/p/w500/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg"
    )
    CreditsView(credits = listOf(response), onClickCreditItem = {})
}