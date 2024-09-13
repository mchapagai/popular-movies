package com.mchapagai.compose.people.views

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mchapagai.core.model.PeopleCombinedCreditModel

@Composable
fun PersonCreditsView(castItems: List<PeopleCombinedCreditModel>?) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (castItems != null) {
            if (castItems.isNotEmpty()) {
                castItems.forEach { cast ->
                    PersonDetailsItemScreen(
                        casts = cast
                    )
                }
            }
        }
    }
}