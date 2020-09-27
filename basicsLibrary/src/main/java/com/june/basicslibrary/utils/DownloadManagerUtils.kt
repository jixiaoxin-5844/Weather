package com.june.basicslibrary.utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import com.june.basicslibrary.R
import java.io.File

/**
 * author : Hyt
 * time : 2020/09/12
 * version : 1.0
 *
 */
object DownloadManagerUtils {

    private val TAG = "downloadManager"
    var referenceId = 0L //分配的下载Id

    val localFileDir = "update"
    val localFileName = "com.hyt.cupcake0.apk" //具体安装包
    //apk下载绝对路径
    val apkPathUrl = "/storage/emulated/0/Android/data/com.hyt.cupcake/files/update/com.hyt.cupcake0.apk"


    /**
     *  @param url 下载地址
     *  @param localFileDir  文件夹名
     *  @param localFileName  安装包名称
     *  @param broadcastReceiver 下载完成的时候，接收广播
     *
     * */
    fun createDownloadService(
        context: Context, url: String,
        localFileDir: String = this.localFileDir, localFileName: String = this.localFileName, broadcastReceiver: BroadcastReceiver? = null
    ) {
        val downloadManager =
            context.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
        val parse = Uri.parse(url)
        val request = DownloadManager.Request(parse)
        with(request) {
            setTitle(context.getString(R.string.app_name))
            setDescription("下载中...")
            //只能在WiFi下进行下载
            // setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)

            /*
            * VISIBILITY_HIDDEN 下载UI不会显示，也不会显示在通知中，如果设置该值，需要声明android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
            * VISIBILITY_VISIBLE 当处于下载中状态时，可以在通知栏中显示；当下载完成后，通知栏中不显示
            * VISIBILITY_VISIBLE_NOTIFY_COMPLETED 当处于下载中状态和下载完成时状态，均在通知栏中显示
            * VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION 只在下载完成时显示在通知栏中
            * */
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

            //  setMimeType("application/vnd.android.package-archive")

            ///设置下载的路径
            request.setDestinationInExternalFilesDir(
                context,
                localFileDir,
                localFileName
            );
        }

        referenceId = downloadManager.enqueue(request)

        if (broadcastReceiver != null) {
            context.registerReceiver(
                broadcastReceiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
        }
    }

    /**
     * 安装
     *
     * @param mContext 上下文
     * @param localFileDir  文件夹名
     * @param localFileName  安装包名称
     */
    fun installAPK(mContext: Context?, localFileDir: String = this.localFileDir,
                   localFileName: String = this.localFileName) {
        if (null == mContext) {
            return
        }
        val type = "application/vnd.android.package-archive"
        val apkFile = File(mContext.getExternalFilesDir(localFileDir), localFileName)
        L.d(TAG, "安装包绝对路径:${apkFile.absolutePath}")

        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val apkUri =
                FileProvider.getUriForFile(mContext, "${mContext.packageName}.provider", apkFile)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, type)
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), type)
        }
        mContext.startActivity(intent)
    }


}