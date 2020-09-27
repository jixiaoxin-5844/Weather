/*
package com.june.basicslibrary.utils.network;


import androidx.annotation.NonNull;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.jx885.library.BaseApp;
import com.jx885.library.storage.BaseDefaultPreferences;
import com.jx885.library.util.NLog;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public class OkHttpDns implements Dns {

    private HttpDnsService httpDns;
    private static OkHttpDns instance = null;

    private OkHttpDns() {
        // 驾培创业教练：173697 5df3caa87f5a66c452874dc4b6717e20
        if (BaseApp.getContext().getPackageName().equals("com.jx885.reward")) {
            this.httpDns = HttpDns.getService(BaseApp.getContext(), "173697", "5df3caa87f5a66c452874dc4b6717e20");
        } else {
            this.httpDns = null;
        }
    }

    public static OkHttpDns getInstance() {
        if (instance == null) {
            synchronized (OkHttpDns.class) {
                if (instance == null) {
                    instance = new OkHttpDns();
                }
            }
        }
        return instance;
    }

    @NonNull
    @Override
    public List<InetAddress> lookup(@NonNull String hostname) throws UnknownHostException {

        if (null == httpDns) {
            return Dns.SYSTEM.lookup(hostname);
        }

        // 如果是测试服务器，不管
        int type = BaseDefaultPreferences.getServerType();
        if (type == 1 || type == 2) {
            return Dns.SYSTEM.lookup(hostname);
        }

        // 通过异步解析接口获取ip
        String ip = httpDns.getIpByHostAsync(hostname);
        if (ip != null) {
            //如果ip不为null，直接使用该ip进行网络请求
            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
            NLog.i("OkHttpDns", "hostname=" + hostname + ",inetAddresses:" + inetAddresses);
            return inetAddresses;
        }
        //如果返回null，走系统DNS服务解析域名
        return Dns.SYSTEM.lookup(hostname);
    }

}
*/
