package com.june.basicslibrary.model

import android.content.Context
import android.os.Looper
import com.june.basicslibrary.base.BaseApp
import com.june.basicslibrary.utils.NetCheckUtils
import com.june.basicslibrary.utils.UtilToast
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

/**
 * author : Hyt
 * time : 2020/08/08
 * version : 1.0
 * 等待使用测试,多次调用... 各种环境下测试
 */
class CallAdapter{

    companion object{

        fun <T> execute(call: Call<T> , context: Context = BaseApp.CONTEXT): Response<T>? {
            if(NetCheckUtils.isConnected(context)){  //检查是否有网络
                return try {
                    call.execute()
                } catch (e:IOException){
                    showToast("网络超时,请检查网络")
                    null
                } catch (e:RuntimeException){
                    showToast("意外错误")
                    null
                } catch (e: Exception) {
                    showToast("网络异常")
                    null
                }
            }else{
                showToast("好像没有网络...")
                return null
            }
        }

        private fun showToast(message: String){
            Looper.prepare()
            UtilToast.showErr(message)
            Looper.loop()
        }

    }

}