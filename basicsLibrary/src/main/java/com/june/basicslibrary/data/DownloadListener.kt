package com.june.basicslibrary.data

/**
 * author : Hyt
 * time : 2020/08/23
 * version : 1.0
 *
 */
interface DownloadListener {

    fun onStart() //下载开始


    fun onProgress(progress: Int) //下载进度


    fun onFinish(path: String?) //下载完成


    fun onFail(errorInfo: String?) //下载失败

}