package com.june.basicslibrary.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

import com.june.basicslibrary.R;
import com.june.basicslibrary.base.BaseApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  time 2020/8/25
 *  version 1.0
 *  from -> qnm
 *  当前最低minSdkVersion == 23
 *  targetSdkVersion == 29
 * */
public class Common {

    public static final String[] PHOTO_SELECT = {"拍照", "从相册中选取"};

    /**
     * 当前版本号是否大于指定版本号
     *
     * @param apiLevel 需判断的版本号
     * @return true 大于
     */
    public static boolean isCompatible(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    /**
     * 是否拥有某个权限
     *
     * @param permission 权限
     * @return true 有，false没有
     */
    public static boolean checkPermission(String permission) {
        PackageManager pm = BaseApp.getCONTEXT().getPackageManager();
        return (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permission, getPackageName()));
    }

    /**
     * 是否拥有READ_PHONE_STATE权限
     *
     * @return true 有， false 没有
     */
    public static boolean checkPermission_HaveREAD_PHONE_STATE() {
        return checkPermission(Manifest.permission.READ_PHONE_STATE);
    }

    public static boolean checkPermission_STORAGE() {
        return checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 判断网络连接是否打开
     */
    public static boolean CheckNetwork() {
        ConnectivityManager cm = (ConnectivityManager) BaseApp.getCONTEXT().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 判断网络连接是否打开
     */
    public static boolean CheckNetwork2() {
        boolean flag = false;
        ConnectivityManager netManager = (ConnectivityManager) BaseApp.getCONTEXT().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManager.getActiveNetworkInfo() != null) {
            flag = netManager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    /**
     * 获取ANDROID_ID
     *
     * @return android id
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        return Settings.Secure.getString(BaseApp.getCONTEXT().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取iccid
     *
     * @return iccid值
     */
    public static String getICCID() {
        if (!checkPermission_HaveREAD_PHONE_STATE()) {
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) BaseApp.getCONTEXT().getSystemService(Context.TELEPHONY_SERVICE);
        if (null == telephonyManager) {
            return "";
        }
        @SuppressLint({"MissingPermission", "HardwareIds"}) String iccid = telephonyManager.getSimSerialNumber();
        return doNullStr(iccid);
    }

    /**
     * 获取iccid
     *
     * @return iccid值
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMEI() {
        if (!checkPermission_HaveREAD_PHONE_STATE()) {
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) BaseApp.getCONTEXT().getSystemService(Context.TELEPHONY_SERVICE);
        if (null == telephonyManager) {
            return "";
        }
        String imei = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            imei = telephonyManager.getDeviceId();
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            imei = telephonyManager.getImei();
        }
        return doNullStr(imei);
    }

    //-------------------------------------------------------------------------------------------------

    /**
     * 判断是否为手机号码
     * 只要符合 13*、14*、15*、17*、18*、19*即可
     */
    public static boolean isMobileNO(String number) {
        if (number.startsWith("+86")) {
            number = number.substring(3);
        }

        if (number.startsWith("+") || number.startsWith("0")) {
            number = number.substring(1);
        }

        number = number.replace(" ", "").replace("-", "");
        return isMobileNOSimple(number);
    }

    public static boolean isMobileNOSimple(String number) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        return p.matcher(number).matches();
    }

    /**
     * 隐藏手机号码
     *
     * @param number 手机号码
     * @return 18650801233 隐藏中间四位：186****1234
     */
    public static String hideMobile(String number) {
        if (TextUtils.isEmpty(number)) {
            return "";
        }

        if (number.length() >= 11) {
            return number.substring(0, 3) + "****" + number.substring(number.length() - 4);
        } else if (number.length() >= 5) {
            return number.substring(0, 3) + "****" + number.substring(number.length() - 2);
        } else if (number.length() >= 3) {
            return number.substring(0, 3) + "****";
        } else {
            return number;
        }
    }


    /**
     * 软件版本
     */
    public static String thisVersion() {
        final String unknown = "Unknown";
        try {
            String ret = BaseApp.getCONTEXT().getPackageManager().getPackageInfo(BaseApp.getCONTEXT().getPackageName(), 0).versionName;
            if (ret.contains(" + "))
                ret = ret.substring(0, ret.indexOf(" + ")) + "b";
            return ret;
        } catch (NameNotFoundException ignored) {
        }
        return unknown;
    }

    /**
     * 软件版本
     */
    public static int thisVersionCode() {
        try {
            return BaseApp.getCONTEXT().getPackageManager().getPackageInfo(BaseApp.getCONTEXT().getPackageName(), 0).versionCode;
        } catch (NameNotFoundException ignored) {
        }
        return 0;
    }

    /**
     * dip转px
     *
     * @param dipValue dip的值
     * @return px的值
     */
    public static int getPixels(int dipValue) {
        Resources r = BaseApp.getCONTEXT().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(int spValue) {
        DisplayMetrics dm = BaseApp.getCONTEXT().getResources().getDisplayMetrics();
        return (int) (spValue * dm.scaledDensity + 0.5f);
    }

    /**
     * dip转px
     *
     * @param dpValue dip
     * @return px
     */
    public static int dip2px(float dpValue) {
        final float scale = BaseApp.getCONTEXT().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getPixels(DisplayMetrics displayMetrics, int dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, displayMetrics);
    }

    /**
     * 判断是否装有SD卡、是否可读写、是否有空间
     *
     * @param size 需存入的文件大小，SD剩余空间必须大于该值，单位MB
     * @return true可用，false不可用
     */
    public static boolean checkSDStatus(long size) {
        try {
            /* 读取SD卡大小 */
            File storage = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(storage.getPath());

            long blocks = 0L;
            long blockSize = 0L;
            if (isCompatible(18)) {
                blocks = stat.getAvailableBlocksLong();
                blockSize = stat.getBlockSizeLong();
            } else {
                blocks = stat.getAvailableBlocks();
                blockSize = stat.getBlockSize();
            }

            /* 判断 */
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && (blocks * blockSize) > size * 1024 * 1024;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将字符串转换为double
     *
     * @param values 字符串
     * @return double值
     */
    public static double string2double(String values) {
        if (TextUtils.isEmpty(values))
            return 0.0;
        double x = 0.0;
        try {
            x = Double.parseDouble(values);
        } catch (Exception ignored) {
        }

        return x;
    }

    /**
     * 将double转换为string
     *
     * @param values double值
     * @return 字符串
     */
    public static String double2string(double values) {
        if (values == 0) {
            return "0";
        }
        if (values == (int) values) {
            return String.valueOf((int) values);
        }
        return String.valueOf(values);
    }

    /**
     * 保留两位小数
     *
     * @param values double值
     * @return 字符串
     */
    public static String double2Money(double values) {
        double x = new BigDecimal(values).setScale(2, RoundingMode.HALF_UP).doubleValue();
        if (x == (int) x) {
            return (int) x + "";
        } else {
            return x + "";
        }
    }

    /**
     * 禁止Edittext弹出软件盘，光标依然正常显示。
     */
    public static void disableShowSoftInput(EditText et) {
        setEditTextShowSoftInput(et, true);
    }

    public static void setEditTextShowSoftInput(EditText et, boolean isDisable) {
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(isDisable);
            method.invoke(et, !isDisable);
        } catch (Exception ignored) {
        }

        try {
            method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
            method.setAccessible(isDisable);
            method.invoke(et, !isDisable);
        } catch (Exception ignored) {
        }
    }

    /**
     * 移动文件至文件
     *
     * @param srcFileName 源文件完整路径
     * @param destDirName 目的目录完整路径，包含文件名
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFileToFile(String srcFileName, String destDirName) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile())
            return false;

        String destDir = destDirName.substring(0, destDirName.lastIndexOf("/") + 1);
        File dest = new File(destDir);
        if (!dest.exists())
            dest.mkdirs();

        return srcFile.renameTo(new File(destDirName));
    }

    public static boolean moveFileToFile(File srcFile, File destFile) {
        if (!srcFile.exists() || !srcFile.isFile())
            return false;

        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        return srcFile.renameTo(destFile);
    }

    /**
     * 移动文件至某个文件夹
     *
     * @param srcFileName 源文件完整路径
     * @param destDir     目的目录完整路径，不含文件名
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFileToDirectory(String srcFileName, String destDir) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile())
            return false;

        File dest = new File(destDir);
        if (!dest.exists())
            dest.mkdirs();

        return srcFile.renameTo(new File(destDir + File.separator + srcFile.getName()));
    }

    /**
     * 移动目录
     *
     * @param srcDirName  源目录完整路径
     * @param destDirName 目的目录完整路径
     * @return 目录移动成功返回true，否则返回false
     */
    public static boolean moveDirectory(String srcDirName, String destDirName) {

        File srcDir = new File(srcDirName);
        if (!srcDir.exists() || !srcDir.isDirectory())
            return false;

        File destDir = new File(destDirName);
        if (!destDir.exists())
            destDir.mkdirs();

        /*
          如果是文件则移动，否则递归移动文件夹。删除最终的空源文件夹 注意移动文件夹时保持文件夹的树状结构
         */
        File[] sourceFiles = srcDir.listFiles();
        for (File sourceFile : sourceFiles) {
            if (sourceFile.isFile())
                moveFileToDirectory(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());
            else if (sourceFile.isDirectory())
                moveDirectory(sourceFile.getAbsolutePath(), destDir.getAbsolutePath() + File.separator + sourceFile.getName());
        }
        return srcDir.delete();
    }

    /**
     * 对文件名重命名
     *
     * @param oldFilename 旧的文件名，必须全路径
     * @param newFilename 新的文件名，必须全路径
     * @return 是否重命名成功
     */
    public static boolean renameFile(String oldFilename, String newFilename) {
        try {
            File file = new File(oldFilename);
            file.renameTo(new File(newFilename));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制文件至某个文件夹
     *
     * @param srcFileName 源文件完整路径
     * @param destDirName 目的目录完整路径，包含文件名
     * @return 是否操作成功
     */
    public static boolean copyFileToFile(String srcFileName, String destDirName) {
        try {
            int byteRead;
            File oldFile = new File(srcFileName);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(oldFile);
                FileOutputStream fs = new FileOutputStream(destDirName);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 取缓存的路径，存储卡的话，至少得10M空间
     *
     * @return 缓存的路径
     */
    public static File getCachePath(String uniqueName) {
        if (checkSDStatus(10)) {
            // 有存储卡的情况下，存入应用本身的缓存目录
            // 路径为：/storage/emulated/0/Android/data/com.example.roamtest/cache
            if (null != BaseApp.getCONTEXT().getExternalCacheDir()) {
                return new File(BaseApp.getCONTEXT().getExternalCacheDir().getPath(), uniqueName);
            } else {
                return new File(BaseApp.getCONTEXT().getCacheDir().getPath(), uniqueName);
            }
        } else {
            // 无存储卡的情况下，存入内置空间
            // 路径为：/data/data/com.example.roamtest/cache
            return new File(BaseApp.getCONTEXT().getCacheDir().getPath(), uniqueName);
        }
    }

    /**
     * 判断字符串是否有空
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    public static String doNullStr(String str) {
        return null == str ? "" : str;
    }

    /**
     * 将大小转换为易读的大小，如XX MB，KB等
     *
     * @param size 大小
     * @return 格式化之后的大小
     */
    public static String convertSize(double size) {
        if (size > 1048576) {
            return new DecimalFormat("#.##").format((size / 1048576)) + " MB";
        } else if (size > 1024) {
            return new DecimalFormat("#.##").format((size / 1024)) + " KB";
        } else if (size < 1024) {
            return size + " 字节";
        } else {
            return size + "";
        }
    }

    /**
     * 获取随机数
     *
     * @param iRdLength 随机数的长度
     * @return 随机数
     */
    public static String getRandom(int iRdLength) {
        Random rd = new Random();
        int iRd = rd.nextInt();
        if (iRd < 0) { // 负数时转换为正数
            iRd *= -1;
        }
        String sRd = String.valueOf(iRd);
        int iLgth = sRd.length();
        if (iRdLength > iLgth) { // 获取数长度超过随机数长度
            // 将整数转化为一个n位的字符串
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < iRdLength - String.valueOf(iRd).length(); i++) {
                result.append("0");
            }
            result.append(String.valueOf(iRd));
            return result.toString();
        } else {
            return sRd.substring(iLgth - iRdLength, iLgth);
        }
    }

    /**
     * 在指定的范围内取随机数
     *
     * @param min 小值
     * @param max 大值
     * @return 在min和max的范围内，取一个随机数
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取包名
     *
     * @return 包名
     */
    public static String getPackageName() {
        try {
            PackageInfo info = BaseApp.getCONTEXT().getPackageManager().getPackageInfo(BaseApp.getCONTEXT().getPackageName(), 0);
            return info.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断当前应用程序是否处于后台，通过getRunningTasks的方式
     *
     * @return true 在后台; false 在前台
     */
    public static boolean isApplicationBroughtToBackgroundByTask(String packageName) {
        ActivityManager activityManager = (ActivityManager) BaseApp.getCONTEXT().getSystemService(Context.ACTIVITY_SERVICE);
        if (null == activityManager) {
            return false;
        }
        List<RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            return !topActivity.getPackageName().equals(packageName);
        }
        return false;
    }

    /**
     * 判断服务是否在运行状态
     *
     * @param serviceName 服务名
     * @return true 在运行，false 没运行
     */
    public static boolean isServiceRunning(String serviceName) {
        ActivityManager manager = (ActivityManager) BaseApp.getCONTEXT().getSystemService(Context.ACTIVITY_SERVICE);
        if (null == manager) {
            return false;
        }
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取meta-data的值
     *
     * @param metaKey 键值
     * @return String值
     */
    public static String getMetaValue(String metaKey) {

        if (TextUtils.isEmpty(metaKey)) {
            return "";
        }

        try {
            ApplicationInfo ai = BaseApp.getCONTEXT().getPackageManager().getApplicationInfo(BaseApp.getCONTEXT().getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = ai.metaData;
            if (null == metaData) {
                return "";
            }

            return metaData.getString(metaKey);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取meta-data的值
     *
     * @param metaKey 键值
     * @return int值
     */
    public static int getMetaValueInteger(String metaKey) {
        if (metaKey == null) {
            return 0;
        }
        try {
            ApplicationInfo ai = BaseApp.getCONTEXT().getPackageManager().getApplicationInfo(BaseApp.getCONTEXT().getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = null;
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                return metaData.getInt(metaKey);
            }
        } catch (NameNotFoundException e) {
            return 0;
        }

        return 0;
    }

    public static String getWeixinAppId() {
        return Common.getMetaValue("weixin_app_id");
    }

    public static String getUmengChannel() {
        return Common.getMetaValue("UMENG_CHANNEL");
    }

    public static String getQnmAppId() {
        return Common.getMetaValue("QNM_APP_ID");
    }

    /**
     * 摆动view
     *
     * @param v view
     */
    public static void shakeView(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(BaseApp.getCONTEXT(), R.anim.shake_x));
    }

    /**
     * 获取状态栏的高度
     *
     * @return 高度
     */
    @SuppressLint("PrivateApi")
    public static int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = BaseApp.getCONTEXT().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void setBackgroundDrawable(View v, Drawable mDrawable) {
        if (isCompatible(16)) {
            v.setBackground(mDrawable);
        } else {
            v.setBackgroundDrawable(mDrawable);
        }
    }

    /**
     * 从 listview 中获取到指定行的 view
     *
     * @param pos
     * @param listView
     * @return
     */
    public static View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    /**
     * 弹出输入框键盘
     *
     * @param view view
     */
    public static void showSoftInputFromWindow(View view) {
        try {
            InputMethodManager m = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != m) {
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 关闭输入法
     */
    public static void hideSoftInputFromWindow(Activity ctx) {
        try {
            View view = ctx.getWindow().peekDecorView();
            if (view != null) {
                InputMethodManager inputmanger = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputmanger != null) {
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 跳转至应用商城
     *
     * @param ctx 上下文
     */
    public static void openApplicationMarket(Context ctx) {
        String packageName = getPackageName();
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(localIntent);
        } catch (Exception e) {
            // 跳转至应用宝
            String url = "http://a.app.qq.com/o/simple.jsp?pkgname=" + packageName;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
    }

    /**
     * 获取文字所占的宽度
     *
     * @param textSize 字体大小，单位sp、dp
     * @param text     文字
     * @return 宽度
     */
    public static float getTextWidth(float textSize, String text) {
        Paint mPaint = new TextPaint() {
        };
        // mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        return mPaint.measureText(text);
    }

    public static float getTextHeight(TextView tv) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);//-----①
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);//-----②
        tv.measure(widthMeasureSpec, heightMeasureSpec); //-------③
        return tv.getMeasuredHeight();
    }

    /**
     * 会员是否到期 TODO 需移到业务层去，跟Common没关系
     *
     * @param viddate 有效期
     * @return 是否过期
     */
    public static boolean isMemberOverdue(String viddate) {
        boolean isOverdue = true;
        try {
            long limit = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(viddate).getTime();
            isOverdue = System.currentTimeMillis() > limit;
        } catch (Exception ignored) {
        }

        return isOverdue;
    }

    /**
     * 设置状态栏颜色
     */
    @SuppressLint("NewApi")
    public static void setStatusBarColor(Activity ctx, @ColorRes int color) {
        setStatusBarColorInt(ctx, ctx.getResources().getColor(color));
    }

    public static void setStatusBarColorInt(Activity ctx, @ColorInt int color) {
        if (Common.isCompatible(21)) {
            Window window = ctx.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        } else if (Common.isCompatible(19)) {
            ctx.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ImageView bar = new ImageView(ctx);
            ViewGroup.LayoutParams lParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Common.getStatusBarHeight());
            bar.setBackgroundColor(color);
            bar.setLayoutParams(lParams);
            ViewGroup view = (ViewGroup) ctx.getWindow().getDecorView();
            view.addView(bar);
        }
    }

    /**
     * 设置状态栏的颜色
     *
     * @param ctx 上下文
     */
    public static void setStatusBarColor(Activity ctx) {
        if (Common.isCompatible(21)) {
            setStatusBarColor(ctx, R.color.colorPrimaryDark);
        } else if (Common.isCompatible(19)) {
            setStatusBarColor(ctx, R.color.colorPrimary);
        }
    }

    /**
     * 全屏
     *
     * @param ctx 上下文
     */
    public static void setFullScreen(Activity ctx) {
        if (Common.isCompatible(21)) {
            Window window = ctx.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Common.isCompatible(19)) {
            ctx.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            ctx.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 透明状态栏
     */
    @SuppressLint("NewApi")
    public static void setTransStatusBar(Activity ctx) {
        setTransStatusBar(ctx, null);
    }

    /**
     * 透明状态栏，适用于全屏图片
     *
     * @param ctx           Activity
     * @param statusBarView 替换状态栏的透明图片，设置成跟状态栏一样的高度
     */
    @SuppressLint("NewApi")
    public static void setTransStatusBar(Activity ctx, View statusBarView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ctx.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ctx.getResources().getColor(android.R.color.transparent));

            if (null != statusBarView)
                statusBarView.getLayoutParams().height = Common.getStatusBarHeight();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ctx.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (null != statusBarView)
                statusBarView.getLayoutParams().height = Common.getStatusBarHeight();
        }
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName 资源名称，比如icon
     * @param c            资源类型，比如R.drawable.class
     * @return int
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Uri resourceIdToUri(int resourceId) {
        return Uri.parse("android.resource://" + BaseApp.getCONTEXT().getPackageName() + "/" + resourceId);
    }

    /**
     * 是否安装了某个应用
     *
     * @param packageName 包名
     * @return true安装了，false未安装
     */
    private static boolean isAppInstalled(String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = BaseApp.getCONTEXT().getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    /**
     * 是否安装了微信
     *
     * @return true 安装了，false没安装
     */
    public static boolean isWeixinInstalled() {
        return isAppInstalled("com.tencent.mm");
    }

    /**
     * 是否安装了驾培创业教练
     *
     * @return true 安装了，false没安装
     */
    public static boolean isRewardInstalled() {
        return isAppInstalled("com.jx885.reward");
    }

    /**
     * 是否安装了四轮教练
     *
     * @return true 安装了，false没安装
     */
    public static boolean isReward2Installed() {
        return isAppInstalled("com.jx885.reward2");
    }

    /**
     * 获取是否存在NavigationBar
     *
     * @return true 有虚拟导航栏，false没有
     */
    public static boolean checkDeviceHasNavigationBar(WindowManager windowManager) {
        if (isCompatible(17)) {
            Display d = windowManager.getDefaultDisplay();
            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);
            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);
            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;
            L.d("info", "realWidth*realHeight=" + realWidth + "x" + realHeight + ",displayWidth*displayHeight=" + displayWidth + "x" + displayHeight);
            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        } else {
            boolean hasNavigationBar = false;
            Resources rs = BaseApp.getCONTEXT().getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            try {
                @SuppressLint("PrivateApi") Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
                Method m = systemPropertiesClass.getMethod("get", String.class);
                String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
                if ("1".equals(navBarOverride)) {
                    hasNavigationBar = false;
                } else if ("0".equals(navBarOverride)) {
                    hasNavigationBar = true;
                }
            } catch (Exception ignored) {
                //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
                boolean hasMenuKey = ViewConfiguration.get(BaseApp.getCONTEXT()).hasPermanentMenuKey();
                boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
                return !hasMenuKey && !hasBackKey;
            }
            return hasNavigationBar;
        }
    }

    /**
     * 获取导航栏的高度
     *
     * @return 高度
     */
    public static int getNavigationBarHeight() {
        Resources resources = BaseApp.getCONTEXT().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 由SparseArray.toString生成的字符串，反解析生成SparseArray
     * 解析这样的格式：{102=B, 210=B, 336=B, 420=A, 452=A, 663=B, 758=B, 789=B, 832=A, 1052=A, 1061=A}
     * 如果为空时，值可能为{}
     *
     * @param code 类似这样的格式：{102=B, 210=B}
     * @return SparseArray
     */
    public static SparseArray<Integer> readStringToSparseArray(String code) {
        SparseArray<Integer> data = new SparseArray<>();
        if (TextUtils.isEmpty(code)) {
            return data;
        }
        if (TextUtils.equals(code, "{}")) {
            return data;
        }
        if (code.length() < 5) {
            return data;
        }
        try {
            String[] buf = code.substring(1, code.length() - 1).split(",[ ]+");
            for (String x : buf) {
                String[] values = x.split("=");
                data.put(Integer.valueOf(values[0]), Integer.valueOf(values[1]));
            }
        } catch (Exception ignored) {
        }
        return data;
    }

    /**
     * 由SparseArray.toString生成的字符串，取出key，生成以逗号分隔的字符
     *
     * @param code 类似这样的格式：{102=B, 210=B}
     * @return 以逗号分隔的字符
     */
    public static String readSparseArrayKeys(String code) {
        if (TextUtils.isEmpty(code)) {
            return null;
        }
        if (TextUtils.equals(code, "{}")) {
            return null;
        }
        if (code.length() < 5) {
            return null;
        }

        StringBuilder ids = new StringBuilder();
        try {
            String[] buf = code.substring(1, code.length() - 1).split(",[ ]+");
            for (String x : buf) {
                String[] values = x.split("=");
                ids.append(values[0]).append(",");
            }
        } catch (Exception ignored) {
        }
        if (ids.length() > 1) {
            return ids.substring(0, ids.length() - 1);
        } else {
            return null;
        }
    }

    /**
     * 显示关键字
     *
     * @param content  内容
     * @param keywords 关键字，多个以逗号，隔开
     * @param color    颜色
     * @param isBold   是否需要粗体
     * @return Spanned
     */
    public static SpannableStringBuilder getKeywordsSpanned(String content, String keywords, int color, boolean isBold) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder(content);
        if (TextUtils.isEmpty(keywords)) {
            return spannableString;
        }

        String[] keys = keywords.split("，");
        for (String key : keys) {
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            if (!content.contains(key)) {
                continue;
            }

            Pattern pattern = Pattern.compile(key);
            Matcher matcher = pattern.matcher(spannableString);

            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                if (isBold) {
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
        }

        return spannableString;
    }

    public static SpannableStringBuilder getKeywordsSpanned(String content, String keywords) {
        return getKeywordsSpanned(content, keywords, Color.parseColor("#ff2200"), false);
    }

    public static void setStatusBarMode(Window window) {
        setStatusBarMode(window, null);
    }

    /**
     * 设置状态栏的样式
     * 状态栏浅色背景，深色字体
     * 支持：Android 4.4之后的miui、flyme，以及正常的Android 6.0及以后的系统
     *
     * @param window        window
     * @param statusBarView statusBarView
     */
    public static void setStatusBarMode(Window window, View statusBarView) {
        if (window == null) {
            return;
        }

        if (OSUtils.isMiui()) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                @SuppressLint("PrivateApi") Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体

                // 开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(BaseApp.getCONTEXT().getResources().getColor(android.R.color.transparent));

                    if (null != statusBarView)
                        statusBarView.getLayoutParams().height = Common.getStatusBarHeight();
                } else {
                    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    if (null != statusBarView)
                        statusBarView.getLayoutParams().height = Common.getStatusBarHeight();
                }

            } catch (Exception ignored) {
            }

        } else if (OSUtils.isFlyme()) {

            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                value |= bit;
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception ignored) {
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(BaseApp.getCONTEXT().getResources().getColor(android.R.color.transparent));

            if (null != statusBarView)
                statusBarView.getLayoutParams().height = Common.getStatusBarHeight();
        }
    }

    /**
     * 复制到粘贴板
     *
     * @param content 文字
     */
    public static void copyToClipboard(String content) {
        final ClipboardManager clip = (ClipboardManager) BaseApp.getCONTEXT().getSystemService(Context.CLIPBOARD_SERVICE);
        if (null != clip) {
            clip.setPrimaryClip(ClipData.newPlainText(null, content));
        }
    }

    public static boolean isActivityAlive(Context context, String simpleName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        for (RunningTaskInfo info : list) {
            Log.e("common", "topactivity的名字：" + info.topActivity.getClassName());
            Log.e("common", "需要的activity的名字：" + simpleName);
            if (info.topActivity.getClassName().equals(simpleName) || info.baseActivity.getClassName().equals(simpleName)) {
                return true;
            }
        }
        return false;

    }

    // 将mac地址处理了一下，全部大写并且去掉了冒号
    public static String getMacString(Context context) {
        String mac = getMac(context);
        if (TextUtils.isEmpty(mac)) {
            return null;
        }
        mac = mac.toUpperCase(Locale.ENGLISH);
        if (mac.contains(":")) {
            mac = mac.replace(":", "");
        }
        return mac;
    }

    public static String getMac(Context context) {
        String mac = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = null;
            try {
                info = wifi.getConnectionInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (info == null) {
                return null;
            }
            mac = info.getMacAddress();
            if (!TextUtils.isEmpty(mac)) {
                mac = mac.toUpperCase(Locale.ENGLISH);
            }
            return mac;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            String WifiAddress = "";
            try {
                WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return WifiAddress;

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "02:00:00:00:00:00";
        }
        return "";
    }
}
