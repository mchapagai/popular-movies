package com.mchapagai.library.utils;

import android.content.Context;

import com.mchapagai.library.R;
import com.mchapagai.library.dialog.MaterialDialogFragment;
import com.mchapagai.library.dialog.MovieDialogBuilder;

import javax.annotation.Nonnull;

import androidx.appcompat.app.AppCompatActivity;

public class MaterialDialogUtils {

    public static MaterialDialogFragment showDialog(@Nonnull Context context, int title, int message, int buttonMessage) {
        MovieDialogBuilder builder = new MovieDialogBuilder()
                .setTitle(context.getResources().getString(title))
                .setMessage(context.getResources().getString(message))
                .setCancelable(true)
                .setPositiveButtonText(context.getResources().getString(buttonMessage))
                .setLayoutResId(R.layout.confirmation_dialog)
                .setCustomButton(true);

        return MaterialDialogFragment.showDialog(builder, (AppCompatActivity) context);
    }

    public static MaterialDialogFragment showDialogWithoutTitle(@Nonnull Context context, int message, int buttonMessage) {
        MovieDialogBuilder builder = new MovieDialogBuilder()
                .setMessage(context.getResources().getString(message))
                .setCancelable(true)
                .setPositiveButtonText(context.getResources().getString(buttonMessage))
                .setLayoutResId(R.layout.confirmation_dialog)
                .setCustomButton(true);

        return MaterialDialogFragment.showDialog(builder, (AppCompatActivity) context);
    }

    public static MaterialDialogFragment showDialog(@Nonnull Context context, String title, String message, String buttonMessage) {
        MovieDialogBuilder builder = new MovieDialogBuilder()
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButtonText(buttonMessage)
                .setLayoutResId(R.layout.confirmation_dialog)
                .setCustomButton(true);

        return MaterialDialogFragment.showDialog(builder, (AppCompatActivity) context);
    }
}
