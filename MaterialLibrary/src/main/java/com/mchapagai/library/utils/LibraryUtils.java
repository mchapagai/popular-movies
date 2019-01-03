package com.mchapagai.library.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import com.mchapagai.library.R;
import com.mchapagai.library.common.LibraryConstants;
import com.mchapagai.library.views.MaterialTextView;
import javax.annotation.Nonnull;

public class LibraryUtils {

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }

    public static Snackbar showSnackBar(@Nonnull Context context, @Nonnull View view, @Nonnull String message) {
        Snackbar snackbar = Snackbar.make(view, LibraryConstants.EMPTY_STRING, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        View snackView = LayoutInflater.from(context).inflate(R.layout.material_snackbar, null);
        snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.actionColor));
        layout.addView(snackView, 0);
        MaterialTextView snackMessage = snackView.findViewById(R.id.snackbar_message);
        snackMessage.setText(message);
        snackbar.show();
        snackbar.getView().requestFocus();
        snackbar.getView().sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED);
        return snackbar;
    }
}
