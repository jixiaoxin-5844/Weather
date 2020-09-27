package com.june.basicslibrary.utils;

import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OSUtils {

    private static final String ROM_MIUI = "MIUI";
    private static final String ROM_FLYME = "FLYME";
    private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";

    private static String sName;

    public static boolean isMiui() {
        return check(ROM_MIUI);
    }

    public static boolean isFlyme() {
        return check(ROM_FLYME);
    }

    private static boolean check(String rom) {
        if (sName != null) {
            return sName.equals(rom);
        }
        String sVersion;
        if (!TextUtils.isEmpty(getProp(KEY_VERSION_MIUI))) {
            sName = ROM_MIUI;
        } else {
            sVersion = Build.DISPLAY;
            if (sVersion.toUpperCase().contains(ROM_FLYME)) {
                sName = ROM_FLYME;
            } else {
                sName = Build.MANUFACTURER.toUpperCase();
            }
        }
        return sName.equals(rom);
    }

    private static String getProp(String name) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + name);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

}
