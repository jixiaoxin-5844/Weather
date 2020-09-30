package com.shijing.weather.data.storage

import com.june.basicslibrary.base.BaseApp
import com.june.basicslibrary.utils.TimeUtils
import com.shijing.weather.data.api.BaseAction

/**
 * author : Hyt
 * time : 2020/08/27
 * version : 1.0
 * App 信息
 */
class AppDataKv {
    companion object{

        //显示所有当前页信息
        fun getAllData(): String{
            val list =  mutableListOf(
                Pair("APP_firstOpen",getFirstOpenApp().toString()),
                Pair("APP_toDayFirstOpen",isToDayFirstOpen().toString()),
                Pair("APP_DebugVersion", getDebugVersion()),
                Pair("APP_AppAllUseTime", getAppAllUseTime().toString()),
                Pair("APP_AppToDayStatistics", getAppToDayStatistics().toString()),

                )
            return list.toString()
        }

        /** 1
         *  首次启动App ->
         *  添加引导页啥的
         *   请在版本更新后酌情是否设置为 false 后续可使用 后端进行动态控制
         * */
        fun getFirstOpenApp(): Boolean {
            return BaseApp.kv.decodeBool("APP_firstOpen", true)
        }

        fun setFirstOpenApp(firstOpen: Boolean) {
            BaseApp.kv.encode("APP_firstOpen", firstOpen)
        }

        /** 2
        *  当天第一次打开
        */
        fun isToDayFirstOpen(): Boolean {
            val nowDate = TimeUtils.getNowDate()
            val toDayOpen = BaseApp.kv.decodeString("APP_toDayFirstOpen")
            return if (nowDate != toDayOpen) {
                BaseApp.kv.encode("APP_toDayFirstOpen", nowDate)
                true
            } else {
                false
            }
        }

        /** 3
         *   debugVersion
         *  json 格式，使用字符串存储，拿出来再转换
         * */
        fun setDebugVersion(version : String){
            BaseApp.kv.encode("APP_DebugVersion", version)
        }

        fun getDebugVersion(): String {
            return BaseApp.kv.decodeString("APP_DebugVersion", "")
        }



        /** 5
         *  统计App 总共使用时长
         * */
        fun setAppAllUseTime(time : Long){
            BaseApp.kv.encode("APP_AppAllUseTime", time)
        }

        fun getAppAllUseTime():Long{
            return BaseApp.kv.decodeLong("APP_AppAllUseTime", 0L)
        }

        /** 6
         *  统计App 总共使用时长
         * */
        fun setAppToDayStatistics(time : Long){
            BaseApp.kv.encode("APP_AppToDayStatistics", time)
        }

        fun getAppToDayStatistics():Long{
            return BaseApp.kv.decodeLong("APP_AppToDayStatistics", 0L)
        }


    }
}