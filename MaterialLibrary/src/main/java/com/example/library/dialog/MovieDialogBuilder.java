package com.example.library.dialog;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.text.Html;

import java.io.Serializable;

public class MovieDialogBuilder implements Parcelable {

    private String title;
    private String message;
    private String tag;
    private String positiveButtonText;
    private String negativeButtonText;
    private String neutralButtonText;
    private boolean isCancelable;
    private boolean isCustomButton;
    private boolean isProgressDialog;
    private Serializable negativeButtonData;
    private Serializable neutralButtonData;
    private Serializable positiveButtonData;
    private Intent positiveButtonIntent;
    private Intent negativeButtonIntent;

    private
    @LayoutRes
    int layoutResId;
    private
    @DrawableRes
    int drawableResId;

    public MovieDialogBuilder() {}


    protected MovieDialogBuilder(Parcel in) {
        title = in.readString();
        message = in.readString();
        tag = in.readString();
        positiveButtonText = in.readString();
        negativeButtonText = in.readString();
        neutralButtonText = in.readString();
        isCancelable = in.readByte() != 0;
        isCustomButton = in.readByte() != 0;
        isProgressDialog = in.readByte() != 0;
        positiveButtonIntent = in.readParcelable(Intent.class.getClassLoader());
        negativeButtonIntent = in.readParcelable(Intent.class.getClassLoader());
        layoutResId = in.readInt();
        drawableResId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(tag);
        dest.writeString(positiveButtonText);
        dest.writeString(negativeButtonText);
        dest.writeString(neutralButtonText);
        dest.writeByte((byte) (isCancelable ? 1 : 0));
        dest.writeByte((byte) (isCustomButton ? 1 : 0));
        dest.writeByte((byte) (isProgressDialog ? 1 : 0));
        dest.writeParcelable(positiveButtonIntent, flags);
        dest.writeParcelable(negativeButtonIntent, flags);
        dest.writeInt(layoutResId);
        dest.writeInt(drawableResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieDialogBuilder> CREATOR = new Creator<MovieDialogBuilder>() {
        @Override
        public MovieDialogBuilder createFromParcel(Parcel in) {
            return new MovieDialogBuilder(in);
        }

        @Override
        public MovieDialogBuilder[] newArray(int size) {
            return new MovieDialogBuilder[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public MovieDialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MovieDialogBuilder setMessage(String message) {
        this.message = message == null ? null : Html.fromHtml(message).toString();
        return this;
    }

    public String getTag() {
        return tag;
    }

    public MovieDialogBuilder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public MovieDialogBuilder setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
        return this;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public MovieDialogBuilder setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
        return this;
    }

    public String getNeutralButtonText() {
        return neutralButtonText;
    }

    public MovieDialogBuilder setNeutralButtonText(String neutralButtonText) {
        this.neutralButtonText = neutralButtonText;
        return this;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public MovieDialogBuilder setCancelable(boolean cancelable) {
        isCancelable = cancelable;
        return this;
    }

    public boolean isCustomButton() {
        return isCustomButton;
    }

    public MovieDialogBuilder setCustomButton(boolean customButton) {
        isCustomButton = customButton;
        return this;
    }

    public boolean isProgressDialog() {
        return isProgressDialog;
    }

    public MovieDialogBuilder setProgressDialog(boolean progressDialog) {
        isProgressDialog = progressDialog;
        return this;
    }

    public Serializable getNegativeButtonData() {
        return negativeButtonData;
    }

    public MovieDialogBuilder setNegativeButtonData(Serializable negativeButtonData) {
        this.negativeButtonData = negativeButtonData;
        return this;
    }

    public Serializable getNeutralButtonData() {
        return neutralButtonData;
    }

    public MovieDialogBuilder setNeutralButtonData(Serializable neutralButtonData) {
        this.neutralButtonData = neutralButtonData;
        return this;
    }

    public Serializable getPositiveButtonData() {
        return positiveButtonData;
    }

    public MovieDialogBuilder setPositiveButtonData(Serializable positiveButtonData) {
        this.positiveButtonData = positiveButtonData;
        return this;
    }

    public Intent getPositiveButtonIntent() {
        return positiveButtonIntent;
    }

    public MovieDialogBuilder setPositiveButtonIntent(Intent positiveButtonIntent) {
        this.positiveButtonIntent = positiveButtonIntent;
        return this;
    }

    public Intent getNegativeButtonIntent() {
        return negativeButtonIntent;
    }

    public MovieDialogBuilder setNegativeButtonIntent(Intent negativeButtonIntent) {
        this.negativeButtonIntent = negativeButtonIntent;
        return this;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public MovieDialogBuilder setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
        return this;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    public MovieDialogBuilder setDrawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
        return this;
    }

    public static Creator<MovieDialogBuilder> getCREATOR() {
        return CREATOR;
    }
}
