package com.june.basicslibrary.model

import com.june.basicslibrary.utils.L
import okhttp3.Interceptor
import okhttp3.Response

/**
 * author : Hyt
 * time : 2020/08/08
 * version : 1.0
 * @param TAG = 日志TAG , 默认 = retrofit
 *
 * Code
 *  1xx : 临时性消息。 100(继续发送) 101(正在切换协议)
 *  2xx : 200(OK) 201(创建成功)
 *  3xx : 重定向 301(永久移动) 302(暂时移动)
 *  4xx : 客户端错误 400(客户端请求错误) 401(被禁止) 403(被禁止) 404(找不到内容)
 *  5xx : 服务器错误 500(服务器内部错误)
 *
 */
class MyInterceptor(private val TAG: String = "retrofit") : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val builder = request.newBuilder()
            .url(url)
            .addHeader("to", "hh")
        val response = chain.proceed(builder.build())

        when (response.code) {
            in 200..299 -> {
                L.d(TAG, "200..299")
            }
            in 400..499 -> {
                L.d(TAG, "400..499")
            }
            else -> {
                L.d(TAG, "else Code")
            }
        }
        L.d(
            TAG, """ ->
            -----------------------------------------
            response:$response
            isHttps:${url.isHttps} 
            method:${request.method}
            
            encodedPath:${url.encodedPath}
            encodedPassword:${url.encodedPassword}
            host:${url.host}
            encodedQuery:${url.encodedQuery}

            headers:${response.headers}
            code:${response.code}
            -----------------------------------------
        """.trimIndent()
        )

        return response
    }
}
