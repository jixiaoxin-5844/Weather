package com.june.basicslibrary.ui;

/**
 * author : Hyt
 * time : 2020/09/20
 * version : 1.0
 */
public class AutoSize {
    /**
     * 默认的设计尺寸
     * >0 设置宽度
     * <0 设置高度
     */
    public final float designSizeInDp;
    public final boolean isSupportSp;

    /**
     * @param designSizeInDp 设计宽度货高度
     * @param isSupportSp    是否支持sp
     */
    public AutoSize(float designSizeInDp, boolean isSupportSp) {
        if (designSizeInDp == 0) {
            throw new IllegalArgumentException("designSizeInDp==0");
        }
        this.designSizeInDp = designSizeInDp;
        this.isSupportSp = isSupportSp;
    }
}
