package com.june.basicslibrary.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class UtilTime {

    static String[] MONTH = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    public static SimpleDateFormat getFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    public static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
    }

    public static String format(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(time);
    }

    public static String format2(Date date) {
        return new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault()).format(date);
    }

    public static String format3(Date date) {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date);
    }

    public static String format4(Calendar date) {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date);
    }

    public static String today() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis());
    }

    /**
     * 服务端给的时间，经常会以.0结尾，所以去除之
     *
     * @param datetime
     * @return
     */
    public static String RemoveLastZero(String datetime) {
        if (TextUtils.isEmpty(datetime))
            return "";

        if (datetime.length() > 19)
            return datetime.substring(0, 19);
        else
            return datetime;
    }

    //-------------------------------------------------------------------------------------------------

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间，与当前时间相比，时间差转换为口头上的术语，如几天几小时几分几秒
     *
     * @return
     */
    public static String convert_between(String datetime) {
        try {
            long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(datetime).getTime();
            return convert_between((int) ((time - System.currentTimeMillis()) / 1000));
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间，两个时间相比，时间差转换为口头上的术语，如几天几小时几分几秒
     *
     * @return
     */
    public static String convert_between(String starttime, String endtime) {
        try {
            long ttime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(starttime).getTime();
            long etime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(endtime).getTime();
            return convert_between((int) ((etime - ttime) / 1000));
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
    }

    /**
     * 将时长秒，转换为口头上的术语，如几天几小时几分几秒 1天：86400s 1时：3600s 1分：60s
     *
     * @param sec 相差的间隔，单位为秒
     * @return
     */
    public static String convert_between(long sec) {
        if (sec < 0)
            return "时间超了";
        StringBuilder buf = new StringBuilder();
        if (sec >= 86400) {
            int day = (int) (sec / 86400);
            int hour = (int) ((sec % 86400) / 3600);
            int min = (int) ((sec % 86400 % 3600) / 60);
            int second = (int) (sec % 86400 % 3600 % 60);
            buf.append(day).append("天").append(hour).append("小时").append(min).append("分").append(second).append("秒");
        } else if (sec > 3600) {
            int hour = (int) (sec / 3600);
            int min = (int) ((sec % 3600) / 60);
            int second = (int) (sec % 3600 % 60);
            buf.append(hour).append("小时").append(min).append("分").append(second).append("秒");
        } else if (sec > 60) {
            int min = (int) (sec / 60);
            int second = (int) (sec % 60);
            buf.append(min).append("分").append(second).append("秒");
        } else {
            buf.append(sec).append("秒");
        }

        return buf.toString();
    }

    /**
     * 将时长秒，转换为几分几秒，适用于通话时长之类的，如2'30''
     *
     * @param sec
     * @return
     */
    public static String convert_between_len(long sec) {
        if (sec < 0)
            return String.valueOf(sec);

        StringBuffer buf = new StringBuffer();
        if (sec > 60) {
            int min = (int) (sec / 60);
            int second = (int) (sec % 60);
            buf.append(min).append("'").append(second).append("''");
        } else {
            buf.append(sec).append("''");
        }

        return buf.toString();
    }

    /**
     * 将时长秒，转换为几分几秒，适用于通话时长之类的，如02:30
     *
     * @param sec
     * @return
     */
    public static String convert_calltime(long sec) {
        if (sec < 0)
            return String.valueOf(sec);

        StringBuffer buf = new StringBuffer();
        if (sec > 60) {
            int min = (int) (sec / 60);
            int second = (int) (sec % 60);
            if (min < 10)
                buf.append("0");
            buf.append(min).append(":");

            if (second < 10)
                buf.append("0");
            buf.append(second);
        } else {
            buf.append("00:");
            if (sec < 10)
                buf.append("0");
            buf.append(sec);
        }

        return buf.toString();
    }

    //-------------------------------------------------------------------------------------------------

    /**
     * 将EEE MMM dd HH:mm:ss zzz yyyy格式的时间，同当前时间相比，格式化为：xx分钟前，xx小时前和日期
     *
     * @param datetime
     * @return
     */
    public static String convert_before_timezone(String datetime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.getDefault());
        dateFormat.setLenient(false);
        Date created = null;
        try {
            created = dateFormat.parse(datetime);
        } catch (Exception e) {
            return "";
        }

        return convert_before(created.getTime());
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间，同当前时间比对，格式化为：xx分钟前，xx小时前和日期
     *
     * @param datetime 需比对的时间
     * @return
     */
    public static String convert_before(String datetime) {
        if (TextUtils.isEmpty(datetime)) {
            return "";
        }

        try {
            long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(datetime).getTime();
            return convert_before(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将对比后的时间，格式化为：xx分钟前，xx小时前和日期
     *
     * @param time 需比对的时间
     * @return
     */
    public static String convert_before(long time) {
        int difftime = (int) ((System.currentTimeMillis() - time) / 1000);
        if (difftime < 86400 && difftime > 0) {
            if (difftime < 3600) {
                int min = difftime / 60;
                if (min == 0)
                    return "刚刚";
                else
                    return difftime / 60 + "分钟前";
            } else {
                return difftime / 3600 + "小时前";
            }
        } else {
            Calendar now = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH) && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(c.getTime());
            }
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH) && c.get(Calendar.DATE) == now.get(Calendar.DATE) - 1) {
                return new SimpleDateFormat("昨天 HH:mm", Locale.getDefault()).format(c.getTime());
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH) && c.get(Calendar.DATE) == now.get(Calendar.DATE) - 2) {
                return new SimpleDateFormat("前天 HH:mm", Locale.getDefault()).format(c.getTime());
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                return new SimpleDateFormat("M月d日 HH:mm", Locale.getDefault()).format(c.getTime());
            } else {
                return new SimpleDateFormat("yy年M月d日", Locale.getDefault()).format(c.getTime());
            }
        }
    }

    /**
     * 指定的时间，在时间条件范围内的，返回true，不在该时间范围内，返回false
     *
     * @param sDate     开始日期，yyyy-MM-dd hh:mm:ss
     * @param eDate     结束时间，yyyy-MM-dd hh:mm:ss
     * @param checkTime 检查时间，yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static boolean timeCompare(String sDate, String eDate, String checkTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long sTime = sdf.parse(sDate).getTime();
            long eTime = sdf.parse(eDate).getTime();
            long sec = sdf.parse(checkTime).getTime();
            return sec > sTime && sec < eTime;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 当前时间，在时间条件范围内的，返回true，不在该时间范围内，返回false
     *
     * @param sDate 开始日期，hh:mm
     * @param eDate 结束时间，hh:mm
     * @return
     */
    public static boolean timeCompa(String sDate, String eDate) {
        try {
            long sec = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            long sTime = sdf.parse(df.format(sec) + " " + sDate).getTime();
            long eTime = sdf.parse(df.format(sec) + " " + eDate).getTime();
            return sec > sTime && sec < eTime;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断两个时间的大小
     *
     * @param sDateTime 开始时间，yyyy-MM-dd hh:mm:ss
     * @param eDateTime 结束时间，yyyy-MM-dd hh:mm:ss
     * @return true 前者比后者大
     */
    public static boolean timeCompare(String sDateTime, String eDateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long sTime = sdf.parse(sDateTime).getTime();
            long eTime = sdf.parse(eDateTime).getTime();
            return sTime > eTime;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断两个日期的大小
     *
     * @param sDate 开始日期，yyyy-MM-dd
     * @param eDate 结束时间，yyyy-MM-dd
     * @return true 前者比后者大
     */
    public static boolean dateCompare(String sDate, String eDate) {
        return timeCompare(sDate + " 00:00:00", eDate + " 00:00:00");
    }

    /**
     * 跟当前日期比较两个日期的大小
     *
     * @param sDate 日期，yyyy-MM-dd
     * @return true 前者比后者大
     */
    public static boolean dateCompareNow(String sDate) {
        return timeCompare(sDate + " 00:00:00", new SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.getDefault()).format(System.currentTimeMillis()));
    }

    /**
     * 将传入时间添加秒钟数
     *
     * @param date 时间
     * @param sec  秒数，正数为添加秒，负数是减少秒
     * @return
     */
    public static String addSec(String date, int sec) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long reminTime = sdf.parse(date).getTime() + 1000 * sec;
            return sdf.format(reminTime);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 当前时间，添加天数
     *
     * @param day 天数
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String addDays(int day) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return format(now.getTime());
    }

    /**
     * 当前时间，添加年
     *
     * @param year 年
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String addYear(int year) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
        return format(now.getTime());
    }

    /**
     * 格式化取当前时间
     *
     * @return
     */
    public static String getThisDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());
    }

    /**
     * 将 /Date(1401441512917)/ 格式的时间，转化为标准易读的时间
     *
     * @param datetime
     * @return
     */
    public static String FormatDatetime(String datetime) {
        if (TextUtils.isEmpty(datetime))
            return "";
        String time = datetime.replace("/Date(", "").replace(")/", "");

        try {
            String ftime = UtilTime.convert_before(Long.valueOf(time));
            return ftime;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将 /Date(1401441512917)/ 格式的时间，转化为yyyy-MM-dd HH:mm:ss
     *
     * @param datetime
     * @return
     */
    public static String FormatDatetime2(String datetime) {
        if (TextUtils.isEmpty(datetime))
            return "";
        String time = datetime.replace("/Date(", "").replace(")/", "");

        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Long.valueOf(time));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将 /Date(1401441512917)/ 格式的时间，转化为yyyy-MM-dd
     *
     * @param datetime
     * @return
     */
    public static String FormatDatetime3(String datetime) {
        if (TextUtils.isEmpty(datetime))
            return "";
        String time = datetime.replace("/Date(", "").replace(")/", "");

        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Long.valueOf(time));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将 /Date(1401441512917)/ 格式的时间，取出毫秒
     *
     * @param datetime
     * @return
     */
    public static long FormatDatetime4(String datetime) {
        if (TextUtils.isEmpty(datetime))
            return 0l;
        String time = datetime.replace("/Date(", "").replace(")/", "");

        try {
            return Long.valueOf(time);
        } catch (Exception e) {
            return 0l;
        }
    }

    /**
     * 格式化取当前时间
     *
     * @return
     */
    public static String FormatDatetime5(long time) {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(time);
    }

    /**
     * 格式化取当前时间
     *
     * @return
     */
    public static String FormatDatetime6(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(time);
    }

    /**
     * 获取年龄
     *
     * @param birthDate 格式：yyyy-mm-dd
     * @return
     */
    public static int[] getBirthDay(String birthDate) {
        if (birthDate.length() != 10)
            return new int[]{0, 0, 0};
        String birthDate_year = birthDate.substring(0, 4);
        String birthDate_month = birthDate.substring(5, 7);
        String birthDate_day = birthDate.substring(8);

        Calendar birthday = new GregorianCalendar(Integer.valueOf(birthDate_year), Integer.valueOf(birthDate_month) - 1, Integer.valueOf(birthDate_day));//2010年10月12日，month从0开始
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (day < 0) {
            month -= 1;
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }

        L.i("info", "年龄：" + year + "年" + month + "月" + day + "天");
        return new int[]{year, month, day};
    }

    /**
     * 计算年份
     *
     * @param day 格式：yyyy-mm-dd
     * @return
     */
    public static int getBirthDayToYear(String day) {
        return getBirthDay(day)[0];
    }

    /**
     * 获取MM月dd日格式的日期
     *
     * @param time
     * @return
     */
    public static String getMonthDayBySec(long time) {
        return new SimpleDateFormat("MM月dd日", Locale.getDefault()).format(time);
    }

    /**
     * 根据HH:mm的时间，获取当天这个时间的毫秒数
     *
     * @param time
     * @return
     */
    public static long getDodaySec(String time) {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis());
        long sec = 0;
        try {
            sec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(today + " " + time + ":00").getTime();
        } catch (Exception e) {
        }
        return sec;
    }

    public static String yyyyMMdd() {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis());
    }

    public static String yyyyMMddHHmmss() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(System.currentTimeMillis());
    }

    /**
     * 取月份
     *
     * @param time 格式：yyyy-MM-dd HH:mm:ss
     * @return 当年显示 xx月，往年显示xxxx年xx月
     */
    public static String getMonth(String time) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        if (TextUtils.equals("" + year, time.substring(0, 4))) {
            try {
                int m = Integer.valueOf(time.substring(5, 7));
                if (m >= 1 && m <= 12) {
                    return MONTH[m - 1];
                }
            } catch (Exception ignored) {
            }
        } else {
            return time.substring(0, 4) + "年" + time.substring(5, 7) + "月";
        }

        return "";
    }

    /**
     * 该日期是否为当月
     *
     * @param month 格式：yyyyMM
     * @return
     */
    public static boolean isMonth(String month) {
        String today = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(System.currentTimeMillis());
        return (TextUtils.equals(today, month));
    }

    /**
     * 该日期是否为今天
     *
     * @param day 格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean isDoday(String day) {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis());
        return (TextUtils.equals(today, day.substring(0, 10)));
    }

    /**
     * 该日期是否为今天
     *
     * @param day 格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean isYesterday(String day) {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        return (TextUtils.equals(today, day.substring(0, 10)));
    }

    /**
     * 格式化时间
     *
     * @param sec 秒数
     * @return 返回hh:mm格式的时间
     */
    public static String formatSecToHHmm(int sec) {
        if (sec <= 0)
            return "00:00";

        StringBuilder buf = new StringBuilder();
        if (sec > 60) {
            int min = sec / 60;
            int second = sec % 60;
            if (min < 10)
                buf.append("0");
            buf.append(min).append(":");

            if (second < 10)
                buf.append("0");
            buf.append(second);
        } else {
            buf.append("00:");
            if (sec < 10)
                buf.append("0");
            buf.append(sec);
        }

        return buf.toString();
    }

    /**
     * 当前日期 是否超过month个月
     *
     * @param date  日期 格式：yyyy-MM-dd
     * @param month 月数
     */
    public static boolean isExceedMonth(String date, int month) {
        boolean isOverTime = false;
        try {
            long val = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date).getTime();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(val);
            cal.add(Calendar.MONTH, month);
            isOverTime = (System.currentTimeMillis() > cal.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isOverTime;
    }

    /**
     * 该日期是否为永久的，超过2099就算永久了
     *
     * @param date 日期，格式：yyyy-MM-dd
     * @return true是，false不是
     */
    public static boolean isTimeForever(String date) {
        if (TextUtils.isEmpty(date) || date.length() != 10) {
            return false;
        }
        String year = date.substring(0, 4);
        try {
            return Integer.valueOf(year) > 2099;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 取前x天
     *
     * @param beforeDay
     * @return
     */
    public static Date getBeforeDay(int beforeDay) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) - beforeDay);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    /**
     * 取前x天
     *
     * @param beforeDay 前几天
     * @return yyyyMMdd
     */
    public static String getBeforeDay_yyyyMMdd(int beforeDay) {
        Date day = getBeforeDay(beforeDay);
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(day);
    }

    /**
     * 取前一周
     *
     * @return yyyy-MM-dd
     */
    public static String getBeforeWeek() {
        Date day = getBeforeDay(7);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(day);
    }

    /**
     * 当前日期，添加月份
     *
     * @param day   日期，固定格式：yyyy-MM-dd HH:mm:ss
     * @param month 月份
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String addMonth(String day, int month) {
        if (TextUtils.isEmpty(day) || day.length() != 19) {
            return day;
        }
        try {
            Date date = getFormat().parse(day);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, month);
            return getFormat().format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return day;
        }
    }
}
