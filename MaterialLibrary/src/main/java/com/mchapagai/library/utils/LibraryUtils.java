package com.mchapagai.library.utils;

public class LibraryUtils {

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }
}
