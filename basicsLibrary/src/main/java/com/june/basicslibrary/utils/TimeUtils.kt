package com.june.basicslibrary.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.roundToInt

class TimeUtils {
    companion object {


        //fun1 判断传入的字符串是否是邮箱
        fun isEmail(email: String): Boolean {
            //在@之前为电子邮箱的用户名，在@之后为电子邮箱的服务器域名地址(与邮箱注册网站的地址一致)
            val pattern =
                Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        //获取当前时间，精确到时分秒
        fun getExactTime(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.CHINA)
            val date = Date(System.currentTimeMillis())
            return simpleDateFormat.format(date)
        }

        //获取当前时间，精确到时分秒
        fun getExactTime2(): String {
            val simpleDateFormat = SimpleDateFormat("M月d日 HH:mm", Locale.CHINA)
            val date = Date(System.currentTimeMillis())
            return simpleDateFormat.format(date)
        }

        //传入日期 格式 2020-5-20   ，转换成对应日期毫秒数 (1970年1月1日起的毫秒数)
        fun transform(date: String): Long? {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            val mDate: Date? = simpleDateFormat.parse(date)  //转换成时间格式
            return mDate!!.time
        }

        //获取今日日期
        fun getNowDate(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-M-dd", Locale.CHINA)
            val date = Date(System.currentTimeMillis())
            return simpleDateFormat.format(date)
        }

        //获取下个月的日期
        fun getNextMonthDate(): String {
            var date = Date()
            val calendar = GregorianCalendar()
            calendar.time = date
            calendar.add(Calendar.MONTH, 1)
            date = calendar.time
            val simpleDateFormat = SimpleDateFormat("yyyy-M-dd", Locale.CHINA)
            return simpleDateFormat.format(date)
        }

        //获取明年的今天的日期
        fun getNextYearDate(): String {
            var date = Date()
            val calendar = GregorianCalendar()
            calendar.time = date
            calendar.add(Calendar.YEAR, 1)
            date = calendar.time
            val simpleDateFormat = SimpleDateFormat("yyyy-M-dd", Locale.CHINA)
            return simpleDateFormat.format(date)
        }

        //两个时间值 计算相差天数 返回 多少 天  格式: yyyy-MM-dd
        fun countDifferDay(endTime: String, startTime: String): Long {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            val oneDay = 1000 * 60 * 60 * 24

            val startDate: Date? = simpleDateFormat.parse(startTime)  //转换成时间格式
            val endDate: Date? = simpleDateFormat.parse(endTime)

            val differ: Long = endDate!!.time - startDate!!.time //相差毫秒数

            return differ / oneDay
        }

        //判断一个时间是否在一个时间区域里
        fun inDate(endTime: String, startTime: String, date: String): Boolean {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            val oneDay = 1000 * 60 * 60 * 24

            val startDate: Date? = simpleDateFormat.parse(startTime)  //转换成时间格式
            val endDate: Date? = simpleDateFormat.parse(endTime)
            val dateN: Date? = simpleDateFormat.parse(date)

            if (dateN!!.time >= startDate!!.time && dateN.time <= endDate!!.time) {
                return true
            }
            return false
        }

        //计算一个年份是不是闰年
        fun isLeapYear(year: Int): Boolean {
            return if (year / 4 == 0 && year / 100 != 0) {
                true
            } else year / 400 == 0
        }

        //得到某月多少天
        fun getDayOfMonth(isLeapYear: Boolean, month: Int): Int {
            var day = 0
            when (month) {
                1, 3, 5, 7, 8, 10, 12 -> day = 31
                2 -> {
                    day = if (isLeapYear) 29 else {
                        28
                    }
                }
                4, 6, 9, 11 -> day = 30
            }
            return day
        }

        /* 判断今天星期几 日期字符串参数
    * 1 星期日
    * 2 星期一
    * 3 星期二
    * 4 星期三
    * 5 星期四
    * 6 星期五
    * 7 星期六 */
        fun dayOfTheWeek(date: String): Int {
            val simpleDateFormat = SimpleDateFormat("yyyy-M-dd", Locale.CHINA)
            val toDayDate: Date = simpleDateFormat.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = toDayDate
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        /**
         *根据毫秒数判断指定天数时星期几
         * */
        fun getWeekDay(time: Long): Int {
            val date = Date(time)
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.get(Calendar.DAY_OF_WEEK)
        }


        /**
         * 输入毫秒数，转换成时间单位
         * 转换为小时单位
         * */
        fun millisecondConvertToTime(millisecond: Long):String{
            val toDouble = millisecond.toDouble()
            val minute = toDouble / 1000 / 60
            val format = DecimalFormat("#.00").format(minute) //保留两位小数，不四舍五入
            return "$format 分钟"
        }



    }
}