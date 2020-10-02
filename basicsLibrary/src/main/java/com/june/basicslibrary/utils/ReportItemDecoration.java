package com.june.basicslibrary.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportItemDecoration extends RecyclerView.ItemDecoration {
    private int top;
    private int left;
    private int right;
    private int bottom;

    public ReportItemDecoration(Context context) {
        top = DensityUtils.dp2px(context, 6);
//        left = DensityUtils.dp2px(context, 18);
//        right = DensityUtils.dp2px(context, 18);
        left = DensityUtils.dp2px(context, 6);
        right = DensityUtils.dp2px(context, 6);
        bottom = DensityUtils.dp2px(context, 6);
    }

    public ReportItemDecoration(Context context, int margin){
        top = DensityUtils.dp2px(context, margin);

        left = DensityUtils.dp2px(context, margin);
        right = DensityUtils.dp2px(context, margin);
        bottom = DensityUtils.dp2px(context, margin);
    }

    public ReportItemDecoration(Context context, int startMargin, int endMargin){
        top = DensityUtils.dp2px(context, 0);
        left = DensityUtils.dp2px(context, startMargin);
        right = DensityUtils.dp2px(context, endMargin);
        bottom = DensityUtils.dp2px(context, 0);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
       /* if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = top;

        }*/
        outRect.top = top;
        outRect.bottom = bottom;
        outRect.left = left;
        outRect.right = right;
    }
}
