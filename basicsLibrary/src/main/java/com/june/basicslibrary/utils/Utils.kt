package com.june.basicslibrary.utils

/**
 * author : Hyt
 * time : 2020/10/02
 * version : 1.0
 *
 */
class Utils {
    companion object {

        /**
         * 截取字符串str中指定字符 strStart、strEnd之间的字符串
         * @param str 要操作的字符串
         * @param strStart
         * @param strEnd
         * @return
         */
        fun subString(str: String?, strStart: String, strEnd: String): String? {
            if(str != null){
                /* 找出指定的2个字符在 该字符串里面的 位置 */
                val strStartIndex = str.indexOf(strStart)
                val strEndIndex = str.indexOf(strEnd)
                /* index 为负数 即表示该字符串中 没有该字符 */
                return if (strStartIndex < 0 && strEndIndex < 0) {
                    "错误"
                } else str.substring(strStartIndex, strEndIndex).substring(strStart.length)
            }
            return "空字符串"
        }

    }
}