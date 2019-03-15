package com.mchapagai.movies.dialog;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import java.io.Serializable;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;

public class MovieDialogBuilder implements Parcelable {

    private String title;
    private String message;
    private String tag;
    private String positiveButtonText;
    private String negativeButtonText;
    private boolean isCancelable;
    private boolean isCustomButton;
    private Serializable negativeButtonData;
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


    public MovieDialogBuilder(Parcel in) {
        title = in.readString();
        message = in.readString();
        tag = in.readString();
        positiveButtonText = in.readString();
        negativeButtonText = in.readString();
        isCancelable = in.readByte() != 0;
        isCustomButton = in.readByte() != 0;
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
        dest.writeByte((byte) (isCancelable ? 1 : 0));
        dest.writeByte((byte) (isCustomButton ? 1 : 0));
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

    public Serializable getNegativeButtonData() {
        return negativeButtonData;
    }

    public MovieDialogBuilder setNegativeButtonData(Serializable negativeButtonData) {
        this.negativeButtonData = negativeButtonData;
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
}
