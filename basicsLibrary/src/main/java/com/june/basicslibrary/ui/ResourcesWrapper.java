/*
package com.june.basicslibrary.ui;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.Resource;


*/
/**
 * author : Hyt
 * time : 2020/09/20
 * version : 1.0
 *//*

public class ResourcesWrapper extends Resource {

    private final AutoSize autoSize;
    private float targetDensity;
    private float targetScaledDensity;
    private int targetDensityDpi;

    public ResourcesWrapper(Resources resources, AutoSize autoSize) {
      //  super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.autoSize = autoSize;
    }

//    @Override
//    public DisplayMetrics getDisplayMetrics() {
//       // DisplayMetrics displayMetrics = super.getDisplayMetrics();
//        initValue(displayMetrics);
//        autoSize(displayMetrics);
//        return displayMetrics;
//    }

    private void initValue(DisplayMetrics displayMetrics) {
        if (targetDensity == 0) {
            float nonCompatDensity = displayMetrics.density;
            float nonCompatScaledDensity = displayMetrics.scaledDensity;
            float designSizeInDp = autoSize.designSizeInDp;
            if (designSizeInDp > 0) {
                targetDensity = displayMetrics.widthPixels / designSizeInDp;
            } else {
                targetDensity = displayMetrics.heightPixels / -designSizeInDp;
            }
            targetScaledDensity = targetDensity * (nonCompatScaledDensity / nonCompatDensity);
            targetDensityDpi = (int) (160 * targetDensity);
        }
    }

    private void autoSize(DisplayMetrics displayMetrics) {
        displayMetrics.density = targetDensity;
        displayMetrics.densityDpi = targetDensityDpi;
        if (autoSize.isSupportSp) {
            displayMetrics.scaledDensity = targetScaledDensity;
        }
    }

    @NonNull
    @Override
    public Class getResourceClass() {
        return null;
    }

    @NonNull
    @Override
    public Object get() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void recycle() {

    }
}
*/
