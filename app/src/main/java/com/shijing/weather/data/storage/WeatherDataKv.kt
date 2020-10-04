package com.shijing.weather.data.storage

import com.june.basicslibrary.base.BaseApp

/**
 * author : Hyt
 * time : 2020/10/03
 * version : 1.0
 * 缓存天气相关数据
 */
class WeatherDataKv {
    companion object{

        //******************** 暂时停用  使用城市id查询更好
        /**
         * 存储经纬度  -> 以逗号分隔的经度/纬度坐标 (和风参数规定)
         * @param longitude 经度
         * @param latitude 纬度
        */
        fun setLongitudeAndLatitude(longitude: String,latitude: String){
            BaseApp.kv.encode("Weather_LongitudeAndLatitude", "${longitude},${latitude}")
        }

        fun getLongitudeAndLatitude():String{
            //默认值是北京的定位 LocationID
            return BaseApp.kv.decodeString("Weather_LongitudeAndLatitude", "101010100")
        }

        /**
         * 存储当前使用的城市名
         * @param cityName 城市名
         */
        fun setCityName(cityName: String){
            BaseApp.kv.encode("Weather_CityName", cityName)
        }

        fun getCityName():String{
            //默认值是北京的定位 LocationID
            return BaseApp.kv.decodeString("Weather_CityName", "北京")
        }

        /**
         * 存储当前使用的城市的LocationId
         * @param locationId 城市Id
         * */
        fun setLocationId(locationId: String){
            BaseApp.kv.encode("Weather_LocationId", locationId)
        }

        fun getLocationId():String{
            //默认值是北京的定位 LocationID
            return BaseApp.kv.decodeString("Weather_LocationId", "101010100")
        }


        /**
         *  最后一次更新天气的时间
         * */
        fun setLastTime(time: String){
            BaseApp.kv.encode("Weather_LastTime", time)
        }

        fun getLastTime():String{
            return BaseApp.kv.decodeString("Weather_LastTime", "")
        }


    }
}