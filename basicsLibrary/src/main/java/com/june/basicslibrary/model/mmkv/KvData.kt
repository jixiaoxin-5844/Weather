package com.june.basicslibrary.model.mmkv

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.june.basicslibrary.base.BaseApp


/**
 * author : Hyt
 * time : 2020/08/01
 * version : 1.0
 * mm kv 存 List
 */
class KvData {

    companion object {

        fun setLabelData(data: MutableList<String>) {
            BaseApp.kv.encode("labelData", Gson().toJson(data))
        }

        fun <T> getLabelData(): MutableList<T>? {
            var list = mutableListOf<T>()
            val data = BaseApp.kv.decodeString("labelData", "")
            if (data != "") {
                list = Gson().fromJson(data, object : TypeToken<List<T>>() {}.type)
                return list
            }
            return null
        }

    }

}