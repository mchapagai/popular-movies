package com.mchapagai.library.utils;

import android.content.Context;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;
import com.mchapagai.library.common.LibraryConstants;
import javax.annotation.Nonnull;

public class LibraryUtils {

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }

    public static Snackbar showSnackBar(@Nonnull Context context, @Nonnull View view, @Nonnull String message) {
        Snackbar snackbar = Snackbar.make(view, LibraryConstants.EMPTY_STRING, Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        return snackbar;
    }
}
