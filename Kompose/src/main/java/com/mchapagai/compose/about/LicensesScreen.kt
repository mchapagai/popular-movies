package com.mchapagai.compose.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat

/**
 * Fetches the software and license lists from string resources using LocalContext.
 * Uses LazyColumn to efficiently display the list of licenses.
 * Displays the software list as a simple text item at the top.
 * Iterates through the lists using items and displays each license with a header and HTML content.
 */
@Composable
fun LicensesScreen(content: AboutModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = content.license,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(16.dp)
        )
        AboutContent(content.license)
        Text(
            // Display software list as simple text
            text = content.softwareList.joinToString("\n"),
            modifier = Modifier.padding(16.dp)
        )
        for (item in content.softwareList.indices) {
            val text = content.licenseList[item]
            HorizontalDivider(thickness = 2.dp)
            Text(
                text = content.softwareList[item],
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(16.dp)
            )
            HtmlText(
                text = text,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}


@Composable
fun AboutContent(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(bottom = 8.dp)
    )
}

/**
 * A composable that displays HTML text.
 * Takes the license text as input.
 * Uses [remember] to parse the HTML content only once using [androidx.core.text.HtmpCompat.fromHtml].
 * Utilizes AndroidView to display a TextView that can render the spanned HTML text.
 * Sets LinkMovementMethod on the TextView to make any links within the HTML clickable.
 */

@Composable
fun HtmlText(
    text: String,
    modifier: Modifier = Modifier,
    margin: Int = 0
) {
    val spannedText = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val annotatedString = buildAnnotatedString {
        append(spannedText.toString())
        // Add clickable spans for URLs and email addresses
        spannedText.getSpans(0, spannedText.length, Any::class.java)
            .filterIsInstance<android.text.style.URLSpan>()
            .forEach { urlSpan ->
                addStyle(
                    style = SpanStyle(textDecoration = TextDecoration.Underline),
                    start = spannedText.getSpanStart(urlSpan),
                    end = spannedText.getSpanEnd(urlSpan)
                )
                addStringAnnotation(
                    tag = "URL",
                    annotation = urlSpan.url,
                    start = spannedText.getSpanStart(urlSpan),
                    end = spannedText.getSpanEnd(urlSpan)
                )
            }
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            color = MaterialTheme.colorScheme.onSurface
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations("URL", start = offset, end = offset).firstOrNull()
                ?.let {
                    uriHandler.openUri(it.item)
                }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = margin.dp)
    )
}
//@Composable
//fun HtmlText(text: String, modifier: Modifier = Modifier) {
//
//    val spannedText = remember(text) {
//        // Parse the HTML text into Spanned
//        HtmlCompat.fromHtml(
//            text,
//            HtmlCompat.FROM_HTML_MODE_COMPACT
//        )
//    }
//    AndroidView(
//        modifier = modifier,
//        factory = { context ->
//            TextView(context).apply {
//                // Set the spanned HTML text to the TextView
//                this.text = spannedText
//                // Make links clickable
//                movementMethod = LinkMovementMethod.getInstance()
//                // Set the text color to WHITE
////                setTextColor(Color.BLACK)
//            }
//        },
//        update = { textView ->
//            textView.text = spannedText
//        }
//    )
//}