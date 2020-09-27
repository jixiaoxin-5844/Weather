package com.june.basicslibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;


import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.june.basicslibrary.BuildConfig;
import com.june.basicslibrary.data.DownloadListener;
import com.june.basicslibrary.data.DownloadService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * author : Hyt
 * time : 2020/08/23
 * version : 1.0
 */
public class DownloadUtils {
    private static int sBufferSize = 8192;

    public static void download(String url, final String path, final DownloadListener downloadListener) {

        HttpLoggingInterceptor logging =new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        //**************
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.xxx.com")

                //通过线程池获取一个线程，指定callback在子线程中运行。
                .callbackExecutor(Executors.newSingleThreadExecutor())
              //  .client(okHttpClient0)
                .build();


        DownloadService service = retrofit.create(DownloadService.class);

        Call<ResponseBody> call = service.download(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                //将Response写入到从磁盘中，详见下面分析
                //注意，这个方法是运行在子线程中的
                writeResponseToDisk(path, response, downloadListener);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                downloadListener.onFail("网络错误～");
            }
        });
    }
    private static void writeResponseToDisk(String path, Response<ResponseBody> response, DownloadListener downloadListener) {
        //从response获取输入流以及总大小
        writeFileFromIS(new File(path), response.body().byteStream(), response.body().contentLength(), downloadListener);
    }


    //将输入流写入文件
    private static void writeFileFromIS(File file, InputStream is, long totalLength, DownloadListener downloadListener) {
        //开始下载
        downloadListener.onStart();

        //创建文件
        if (!file.exists()) {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                downloadListener.onFail("createNewFile IOException");
            }
        }

        OutputStream os = null;
        long currentLength = 0;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
                currentLength += len;
                //计算当前下载进度
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
            }
            //下载完成，并返回保存的文件路径
            downloadListener.onFinish(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            downloadListener.onFail("IOException");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 安装
     *
     * @param mContext 上下文
     * @param apkName  安装包名称
     */
    public static void installAPK(Context mContext, String apkName) {
        installAPK(mContext, "apk", apkName);
    }

    /**
     * 安装
     *
     * @param mContext 上下文
     * @param fileDir  文件夹名
     * @param apkName  安装包名称
     */
    public static void installAPK(Context mContext, String fileDir, String apkName) {
        if (null == mContext) {
            return;
        }

        String type = "application/vnd.android.package-archive";
        File apkFile = new File(mContext.getExternalFilesDir(fileDir), apkName);
        L.d("download", "文件路径：" + apkFile.getPath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(mContext, "/storage/emulated/0/Android/data/com.june.dyy/files/apk/", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, type);
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), type);
        }
        mContext.startActivity(intent);
    }
}
