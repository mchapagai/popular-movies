package com.example.library.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.library.R;
import com.example.library.dialog.DialogBuilder;
import com.example.library.dialog.MaterialDialogFragment;

import javax.annotation.Nonnull;

public class MaterialDialogUtils {

    public static MaterialDialogFragment showDialogWithSingleButton(Context context, int title, int message, int positiveButton) {
        return showDialog(context, title, message, positiveButton);
    }

    public static MaterialDialogFragment showDialog(@Nonnull Context context, int title, int message, int positiveButtonMsg) {
        DialogBuilder builder = new DialogBuilder()
                .setTitle(context.getResources().getString(title))
                .setMessage(context.getResources().getString(message))
                .setCancelable(true)
                .setPositiveButtonText(context.getResources().getString(positiveButtonMsg))
                .setLayoutResId(R.layout.custom_confirmation_dialog)
                .setCustomButton(true);

        return MaterialDialogFragment.showDialog(builder, (AppCompatActivity) context);
    }

    public static MaterialDialogFragment showDialog(@Nonnull Context context, String title, String message, String positiveButtonMsg) {
        DialogBuilder builder = new DialogBuilder()
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButtonText(positiveButtonMsg)
                .setLayoutResId(R.layout.custom_confirmation_dialog)
                .setCustomButton(true);

        return MaterialDialogFragment.showDialog(builder, (AppCompatActivity) context);
    }
}
