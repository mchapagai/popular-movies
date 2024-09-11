package com.mchapagai.compose.about

import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Patterns
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.util.LinkifyCompat


@Composable
fun LinkifyText(modifier: Modifier, text: String?) {
    val context = LocalContext.current
    val textView = remember { TextView(context) }
    AndroidView(modifier = modifier, factory = { textView }) { view ->
        view.text = text ?: ""
        LinkifyCompat.addLinks(view, Linkify.WEB_URLS)
        Linkify.addLinks(
            view, Patterns.PHONE, "tel:",
            Linkify.sPhoneNumberMatchFilter, Linkify.sPhoneNumberTransformFilter
        )
        view.movementMethod = LinkMovementMethod.getInstance()
    }
}