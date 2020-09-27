package com.june.basicslibrary.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


/**
 * author : Hyt
 * time : 2020/08/23
 * version : 1.0
 *
 */
interface DownloadService {

    @Streaming
    @GET
    fun download(@Url url: String?): Call<ResponseBody?>?


}