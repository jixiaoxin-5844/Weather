package com.june.basicslibrary.utils;

/**
 * author : Hyt
 * time : 2020/09/01
 * version : 1.0
 */
public class ColorEvaluate {

    public static int evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB)))) ;


//        // convert from sRGB to Linear
//        startR = (float) Math.pow(startR, 2.2);
//        startG = (float) Math.pow(startG, 2.2);
//        startB = (float) Math.pow(startB, 2.2);
//
//        endR = (float) Math.pow(endR, 2.2);
//        endG = (float) Math.pow(endG, 2.2);
//        endB = (float) Math.pow(endB, 2.2);
//
//        //compute the interpolated color in linear space
//        float a = startA + fraction * (endA - startA);
//        float r = startR + fraction * (endR - startR);
//        float g = startG + fraction * (endG - startG);
//        float b = startB + fraction * (endB - startB);
//
//        //convert back to sRGB in the [0..255] range
//        a = a * 255.0f;
//        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
//        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
//        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        //return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);

    }

}
