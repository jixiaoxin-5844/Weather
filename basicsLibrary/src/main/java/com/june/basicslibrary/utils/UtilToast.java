package com.june.basicslibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.june.basicslibrary.R;
import com.june.basicslibrary.base.BaseApp;

/**
 * 这是一个通用的Toast提示消息框
 * <p>
 * 有样式的，建议不超过14个字
 *
 * @author pengl
 */
public class UtilToast {

    public static void show(String message) {

        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (message.length() > 12)
            Toast.makeText(BaseApp.Companion.getCONTEXT(), message, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(BaseApp.Companion.getCONTEXT(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(BaseApp.Companion.getCONTEXT(), message, Toast.LENGTH_LONG).show();
    }

    public static void showSucc(String message) {
        showStyle(1, message);
    }

    public static void showInfo(String message) {
        showStyle(2, message);
    }

    public static void showAlert(String message) {
        showStyle(3, message);
    }

    public static void showErr(String message) {
        showStyle(4, message);
    }

    private static void showStyle(int type, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        Toast toast = new Toast(BaseApp.Companion.getCONTEXT());
        LayoutInflater inflate = (LayoutInflater) BaseApp.Companion.getCONTEXT().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.view_toast, null);
        TextView tv = v.findViewById(R.id.toast_tv);
        tv.setText(message);
        tv.setTextColor(BaseApp.Companion.getCONTEXT().getResources().getColor(R.color.white));
        ImageView img = v.findViewById(R.id.toast_img);

        if (type == 1) {
            img.setImageResource(R.mipmap.snackbar_right);
        } else if (type == 2) {
            img.setImageResource(R.mipmap.snackbar_info);
        } else if (type == 3) {
            img.setImageResource(R.mipmap.snackbar_alert);
        } else if (type == 4) {
            img.setImageResource(R.mipmap.snackbar_err);
        }

        toast.setView(v);
        toast.setGravity(Gravity.CENTER, 0, 0);

        if (message.length() > 12)
            toast.setDuration(Toast.LENGTH_LONG);
        else
            toast.setDuration(Toast.LENGTH_SHORT);

        toast.show();
    }
}
