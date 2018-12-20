package com.mchapagai.library.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.mchapagai.library.R;
import com.mchapagai.library.views.MaterialButton;
import com.mchapagai.library.views.MaterialTextView;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class MaterialDialogFragment extends DialogFragment {

    public static final String DATA_MODEL = "dateModel";
    private OnDialogClickListener onDialogClickListener;

    public MaterialDialogFragment() {
    }

    private static MaterialDialogFragment buildAlertDialog(MovieDialogBuilder movieDialogBuilder) {
        MaterialDialogFragment fragment = new MaterialDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATA_MODEL, movieDialogBuilder);
        fragment.setArguments(args);
        return fragment;
    }

    public static MaterialDialogFragment showDialog(MovieDialogBuilder movieDialogBuilder, AppCompatActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        String tag = movieDialogBuilder.getTag();
        MaterialDialogFragment fragment = MaterialDialogFragment.buildAlertDialog(movieDialogBuilder);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.commitAllowingStateLoss();
        return fragment;
    }

    public static MaterialDialogFragment showDialog(MovieDialogBuilder builder, Fragment fragment) {
        FragmentManager manager = fragment.getFragmentManager();
        String tag = builder.getTag();
        MaterialDialogFragment dialogFragment = MaterialDialogFragment.buildAlertDialog(builder);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(dialogFragment, tag);
        transaction.commitAllowingStateLoss();
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MovieDialogBuilder dialogBuilder = getArguments().getParcelable(DATA_MODEL);
        return createDialog(dialogBuilder);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        dialog.dismiss();
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    private Dialog createDialog(final MovieDialogBuilder builder) {
        View view = null;
        Dialog dialog;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        if (builder.getLayoutResId() != 0) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            view = inflater.inflate(builder.getLayoutResId(), null);
            alertDialog.setView(view);
            if (!builder.isCustomButton()) {
                setButtons(builder, alertDialog);
            }
            if (view.findViewById(R.id.dialog_title) != null) {
                MaterialTextView dialogTitle = view.findViewById(R.id.dialog_title);

                if (StringUtils.isNotEmpty(builder.getTitle())) {
                    dialogTitle.setText(builder.getTitle());
                    dialogTitle.setVisibility(View.VISIBLE);
                } else {
                    dialogTitle.setVisibility(View.GONE);
                }
            }
        } else {
            if (builder.getMessage() != null) {
                alertDialog.setMessage(builder.getMessage());
            }
            setButtons(builder, alertDialog);
            if (builder.getTitle() != null) {
                alertDialog.setTitle(builder.getTitle());
            }
        }

        if (builder.getDrawableResId() != 0) {
            alertDialog.setIcon(builder.getDrawableResId());
        }

        setCancelable(builder.isCancelable());
        dialog = alertDialog.create();
        setCustomDialog(builder, dialog, view);

        return dialog;
    }

    private void setButtons(final MovieDialogBuilder builder, final AlertDialog.Builder dialogBuilder) {
        if (builder.getPositiveButtonText() != null) {
            dialogBuilder.setPositiveButton(builder.getPositiveButtonText(),
                    ((dialog, which) -> {
                        if (onDialogClickListener != null) {
                            onDialogClickListener.onPositiveButtonClicked(builder.getPositiveButtonData(), builder.getTag());
                        }
                    }));
            dialogBuilder.setNegativeButton(builder.getNegativeButtonText(),
                    ((dialog, which) -> {
                        if (onDialogClickListener != null) {
                            onDialogClickListener.onNegativeButtonClicked(builder.getTag());
                        }
                        dialog.dismiss();
                    }));
        }
    }

    private void setCustomDialog(final MovieDialogBuilder builder, final Dialog dialog, View containerView) {
        if (containerView == null) {
            return;
        }

        if (builder.getMessage() != null) {
            MaterialTextView message = containerView.findViewById(R.id.dialog_message);
            message.setVisibility(View.VISIBLE);
            message.setText(builder.getMessage());
        }

        if (builder.getPositiveButtonText() != null) {
            MaterialButton positiveButton = containerView.findViewById(R.id.positive_button);
            positiveButton.setText(builder.getPositiveButtonText().toUpperCase());
            positiveButton.setVisibility(View.VISIBLE);
            positiveButton.setOnClickListener(view -> {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onPositiveButtonClicked(builder.getPositiveButtonData(), builder.getTag());
                }
                dialog.dismiss();
            });
        }

        if (builder.getNegativeButtonText() != null) {
            MaterialButton negativeButton = containerView.findViewById(R.id.negative_button);
            negativeButton.setText(builder.getNegativeButtonText());
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setOnClickListener(v -> {
                if (onDialogClickListener != null) {
                    onDialogClickListener.onNegativeButtonClicked(builder.getTag());
                }
                dialog.dismiss();
            });
        }

    }


    public interface OnDialogClickListener {
        void onPositiveButtonClicked(Serializable data, String tag);

        void onNegativeButtonClicked(String tag);
    }
}
