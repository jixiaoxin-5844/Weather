/*
package com.june.basicslibrary.utils.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.june.basicslibrary.utils.Common;
import com.jx885.library.BaseApp;
import com.jx885.library.BuildConfig;
import com.jx885.library.storage.BaseDefaultPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;

public class HttpUtils {

    static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()//
                .dns(OkHttpDns.getInstance())//
                .connectTimeout(10, TimeUnit.SECONDS)//
                .writeTimeout(10, TimeUnit.SECONDS)//
                .readTimeout(15, TimeUnit.SECONDS)//
                .hostnameVerifier((hostname, sslSession) -> true)// 不检测https的证书有效性
                .build();
    }

    static Headers getHeader(String appId) {
        int versionCode = Common.thisVersionCode();

        Headers.Builder headers = new Headers.Builder();
        if (!TextUtils.isEmpty(appId)) {
            headers.add("appId", appId);
        } else {
            headers.add("appId", Common.getPackageName());
        }
        headers.add("os", "Android");
        headers.add("version", "b" + versionCode);
        headers.add("versionCode", String.valueOf(versionCode));
        headers.add("model", Build.MODEL);
        headers.add("system", Build.VERSION.RELEASE);
        headers.add("pixels", BaseApp.getContext().getResources().getDisplayMetrics().widthPixels + "*" + BaseApp.getContext().getResources().getDisplayMetrics().heightPixels);
        headers.add("devId", getAndroidID());
        headers.add("deviceId", getAndroidID());

        String flavor = Common.getUmengChannel();
        if (TextUtils.isEmpty(flavor)) {
            headers.add("flavor", "");
        } else {
            headers.add("flavor", flavor);
        }

        String token = BaseDefaultPreferences.getToken();
        if (!TextUtils.isEmpty(token)) {
            headers.add("token", token);
        }

        int userId = BaseDefaultPreferences.getUserIdInt();
        if (userId > 0) {
            headers.add("userId", String.valueOf(userId));
        } else {
            String userIdString = BaseDefaultPreferences.getUserIdString();
            if (!TextUtils.isEmpty(userIdString)) {
                headers.add("userId", userIdString);
            }
        }

        if (BuildConfig.Dev) {
            Log.v("info", "|--------【headers】");
            Log.v("info", "|        appId=" + headers.get("appId"));
            Log.v("info", "|        os=" + headers.get("os"));
            Log.v("info", "|        version=" + headers.get("version"));
            Log.v("info", "|        versionCode=" + headers.get("versionCode"));
            Log.v("info", "|        model=" + headers.get("model"));
            Log.v("info", "|        system=" + headers.get("system"));
            Log.v("info", "|        pixels=" + headers.get("pixels"));
            Log.v("info", "|        devId=" + headers.get("devId"));
            Log.v("info", "|        deviceId=" + headers.get("devId"));
            Log.v("info", "|        flavor=" + headers.get("flavor"));

            String _token = headers.get("token");
            if (!TextUtils.isEmpty(_token)) {
                Log.v("info", "|        token=" + _token);
            }

            String _userId = headers.get("userId");
            if (!TextUtils.isEmpty(_userId)) {
                Log.v("info", "|        userId=" + _userId);
            }

            Log.v("info", "|        ");
        }

        return headers.build();
    }

    */
/**
     * 获取ANDROID_ID
     *
     * @return android id
     *//*

    @SuppressLint("HardwareIds")
    private static String getAndroidID() {
        return Settings.Secure.getString(BaseApp.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    */
/**
     * 软件版本
     *//*

    private static int getVersionCode() {
        try {
            return BaseApp.getContext().getPackageManager().getPackageInfo(BaseApp.getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return 0;
    }

    */
/**
     * 判断网络是否可用
     *
     * @param context        上下文
     * @param isCheckNetwork 是否检查网络，true表示检查，false表示不检查
     * @return boolean
     *//*

    static boolean isNetworkConnected(Context context, boolean isCheckNetwork) {
        if (isCheckNetwork && context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnectedOrConnecting();
        } else {
            return true;
        }
    }

    */
/**
     * 计算String的MD5值，返回小写
     *
     * @param sourceStr 需计算的字符
     * @return MD5值
     *//*

    public static String getStringMD5(String sourceStr) {
        String s = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 这两行代码的作用是：
            // 将bytes数组转换为BigInteger类型。1，表示 +，即正数。
            BigInteger bigInt = new BigInteger(1, md.digest(sourceStr.getBytes()));
            // 通过format方法，获取32位的十六进制的字符串。032,代表高位补0 32位，X代表十六进制的整形数据。
            // 为什么是32位？因为MD5算法返回的时一个128bit的整数，我们习惯于用16进制来表示，那就是32位。
            s = String.format("%032x", bigInt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    */
/**
     * 计算String的MD5值，返回大写
     *
     * @param sourceStr 需计算的字符
     * @return MD5值
     *//*

    public static String getStringMD5toUpperCase(String sourceStr) {
        return getStringMD5(sourceStr).toUpperCase();
    }

    */
/**
     * 计算文件的MD5，返回大写
     *
     * @param file 文件
     * @return MD5值
     *//*

    public static String getFileMD5(File file) {
        String s = "";

        if (!file.exists()) {
            return s;
        }

        FileInputStream in;
        byte[] buffer = new byte[1024];
        int len;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, len);
            }
            in.close();

            BigInteger bigInt = new BigInteger(1, md.digest());
            s = String.format("%032x", bigInt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s.toUpperCase();
    }

    public static String getDefErrTips(String realErr) {
        return (BuildConfig.Dev && !TextUtils.isEmpty(realErr)) ? realErr : "加载失败，点击重试";
    }

    public static String getDefErrNetwork() {
        return "请检查网络，点击刷新";
    }

    public static String getFailureDesc(int state, Object result) {
        // 当前手机没有网络
        if (state == AsyncTaskManager.HTTP_NULL_CODE) {
            return "当前网络不可用";
        }

        if (!BuildConfig.Dev) {
            return getDefErrTips("");
        }

        // 接口抛出HttpException异常
        if (state == AsyncTaskManager.HTTP_ERROR_CODE) {
            return ((Exception) result).getMessage();
        }

        // 其它抛出的异常
        if (state == AsyncTaskManager.REQUEST_ERROR_CODE) {
            return getDefErrTips(((Exception) result).getMessage());
        }

        return getDefErrTips("");
    }

    public static String URLEncoder(String url) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(url, "utf-8");
    }
}
*/
