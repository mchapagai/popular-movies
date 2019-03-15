package com.mchapagai.movies.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.mchapagai.movies.R;
import com.mchapagai.movies.dialog.MaterialDialogFragment;
import com.mchapagai.movies.dialog.MovieDialogBuilder;

public class MaterialDialogUtils {

    public static MaterialDialogFragment showDialog(Context context, int title, int message,
            int buttonMessage) {
        MovieDialogBuilder builder = new MovieDialogBuilder()
                .setTitle(context.getResources().getString(title))
                .setMessage(context.getResources().getString(message))
                .setCancelable(true)
                .setPositiveButtonText(context.getResources().getString(buttonMessage))
                .setLayoutResId(R.layout.confirmation_dialog)
                .setCustomButton(true);

        return MaterialDialogFragment.showDialog(builder, (AppCompatActivity) context);
    }

    public static MaterialDialogFragment showDialogWithoutTitle(Context context, int message,
            int buttonMessage) {
        MovieDialogBuilder builder = new MovieDialogBuilder()
                .setMessage(context.getResources().getString(message))
                .setCancelable(true)
                .setPositiveButtonText(context.getResources().getString(buttonMessage))
                .setLayoutResId(R.layout.confirmation_dialog)
                .setCustomButton(true);

        return MaterialDialogFragment.showDialog(builder, (AppCompatActivity) context);
    }

    public static MaterialDialogFragment showDialog(Context context, String title, String message,
            String buttonMessage) {
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
