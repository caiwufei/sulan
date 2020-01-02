package com.yilanjiaju.log.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	/**
     * 把日期字符串格式化成日期类型
     * @param dateStr
     * @param format
     * @return
     */
    public static Date convert2Date(String dateStr, String format) {  
        SimpleDateFormat simple = new SimpleDateFormat(format); 
        try {  
//            simple.setLenient(true);
            return simple.parse(dateStr);  
        } catch (Exception e) {  
            return  null;  
        }  
     }  
    
    
    /**
     * 把日期类型格式化成长字符串
     * 年月日 时分秒
     * @param date
     * @return
     */
    public static String convertDate2LongString(Date date) {
       return convertDate2String(date,"yyyy-MM-dd HH:mm:ss");
    }
       
    public static String convertDate2LongString2(Date date) {
        return convertDate2String(date,"yyyy/MM/dd HH:mm:ss");
     }
    
    public static String convertDate2LongString3(Date date) {
        return convertDate2String(date,"yyyy-MM-dd HH-mm-ss");
     }
    
    /**
     * 月日 时分
     * @param date
     * @return
     */
    public static String convertDate2MDHM(Date date) {
       return convertDate2String(date,"MM-dd HH:mm");
    }
    
    /**
     * 把日期类型格式化成断字符串
     * 年月日
     * @param date
     * @return
     */
    public static String convertDate2ShortString(Date date) {
    	return convertDate2String(date,"yyyy-MM-dd");
    }
    
    public static String convertDate2ShortString2(Date date) {
    	return convertDate2String(date,"yyyy/MM/dd");
    }
    
    /**
     * 月日
     * @param date
     * @return
     */
    public static String convertDate2MD(Date date) {
    	return convertDate2String(date,"MM-dd");
    }
    
    /**
     * 把日期类型格式化成字符串
     * @param date
     * @param format
     * @return
     */
    public static String convertDate2String(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            return formater.format(date);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 转sql的time格式
     * @param date
     * @return
     */
    public static Timestamp convertSqlTime(Date date){
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
    
    /**
     * 转sql的日期格式
     * @param date
     * @return
     */
    public static java.sql.Date convertSqlDate(Date date){
        java.sql.Date Datetamp = new java.sql.Date(date.getTime());
        return Datetamp;
    }
    
    
    /**
     * 获取当前日期
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }
    

    /**
     * timestamp转长String
     * 年月日 时分秒
     * @param ts
     * @return
     */
    public static String convertTimestamp2LongString(Timestamp ts) {
       return convertTimestamp2String(ts,"yyyy-MM-dd HH:mm:ss");
    }
    

    /**
     * timestamp转短String
     * 年月日
     * @param ts
     * @return
     */
    public static String convertTimestamp2ShortString(Timestamp ts) {
    	return convertTimestamp2String(ts,"yyyy-MM-dd");
    }
     
    /**
     * @param ts
     * @param format
     * @return
     */
    public static String convertTimestamp2String(Timestamp ts,String format){
    	 SimpleDateFormat formater = new SimpleDateFormat(format);
         try {
             return formater.format(ts);
         } catch (Exception e) {
             return null;
         }
    }
    
    
    
    /**
     * 获取时间戳
     * @return
     */
    public static long getTimestamp()
    {
        return System.currentTimeMillis();
    }
    
    /**
     * 获取月份的天数
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取日期的年
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 获取日期的月
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 获取日期的日
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    
    /**
     * 获取日期的时
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }
    
    /**
     * 获取日期的分种
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * 获取日期的秒
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }
    
    /**
     * 获取星期几
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek-1;
    }

    /**
     * 获取星期几的字母表示
     * @param date
     * @return
     */
    public static String getStrWeekDay(Date date) {
        int dTmp = getWeekDay(date);
        String strWeek = "";
        switch (dTmp) {
            case 1:
                strWeek = "星期一";
                break;
            case 2:
                strWeek = "星期二";
                break;
            case 3:
                strWeek = "星期三";
                break;
            case 4:
                strWeek = "星期四";
                break;
            case 5:
                strWeek = "星期五";
                break;
            case 6:
                strWeek = "星期六";
                break;
            case 7:
                strWeek = "星期日";
                break;

        }
        return strWeek;
    }


    
    /**
     * 获取哪一年共有多少周
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekNumOfYear(c.getTime());
    }
    
    /**
     * 取得某天是一年中的多少周
     * @param date
     * @return
     */
    public static int getWeekNumOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 取得某天所在周的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }
    
    /**
     * 取得某天所在周的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }
    
    /**
     * 取得某年某周的第一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周,2009-01-05为2009年第一周的第一天
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar calFirst = Calendar.getInstance();
        calFirst.set(year, 0, 7);
        Date firstDate = getFirstDayOfWeek(calFirst.getTime());

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.setTime(firstDate);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);
        firstDate = getFirstDayOfWeek(cal.getTime());

        return firstDate;
    }
    
    /**
     * 取得某年某周的最后一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周, 2009-01-04为
     * 2008年最后一周的最后一天
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar calLast = Calendar.getInstance();
        calLast.set(year, 0, 7);
        Date firstDate = getLastDayOfWeek(calLast.getTime());

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.setTime(firstDate);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);
        Date lastDate = getLastDayOfWeek(cal.getTime());

        return lastDate;
    }
    
    
    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }
    
    /*
     * 1则代表的是对年份操作， 2是对月份操作， 3是对星期操作， 5是对日期操作， 11是对小时操作， 12是对分钟操作， 13是对秒操作，
     * 14是对毫秒操作
     */

    /**
     * 增加年
     * @param date
     * @param amount
     * @return
     */
    public static Date addYears(Date date, int amount) {
        return add(date, 1, amount);
    }

    /**
     * 增加月
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, 2, amount);
    }

    /**
     * 增加周
     * @param date
     * @param amount
     * @return
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, 3, amount);
    }

    /**
     * 增加天
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }

    /**
     * 增加时
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }

    /**
     * 增加分
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }

    /**
     * 增加秒
     * @param date
     * @param amount
     * @return
     */
    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    /**
     * 增加毫秒
     * @param date
     * @param amount
     * @return
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }
    
    
    
   /**
    * time差
    * @param before
    * @param after
    * @return
    */
   public static long diffTimes(Date before, Date after){
       return after.getTime() - before.getTime();
   }
   
   /**
    * 秒差
    * @param before
    * @param after
    * @return
    */
   public static long diffSecond(Date before, Date after){
       return (after.getTime() - before.getTime())/1000;
   }
   
   /**
    * 分种差
    * @param before
    * @param after
    * @return
    */
   public static int diffMinute(Date before, Date after){
       return (int)(after.getTime() - before.getTime())/1000/60;
   }
   
   /**
    * 时差
    * @param before
    * @param after
    * @return
    */
   public static int diffHour(Date before, Date after){
       return (int)(after.getTime() - before.getTime())/1000/60/60;
   }
    
    /**
     * 天数差
     * @param before
     * @param after
     * @return
     */
    public static int diffDay(Date before, Date after) {
        return Integer.parseInt(String.valueOf(((after.getTime() - before.getTime()) / 86400000)));
    }
    
    /**
     * 月差
     * @param before
     * @param after
     * @return
     */
    public static int diffMonth(Date before, Date after){
        int monthAll=0;
        int yearsX = diffYear(before,after);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(before);
        c2.setTime(after);
        int monthsX = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        monthAll=yearsX*12+monthsX;
        int daysX =c2.get(Calendar.DATE) - c1.get(Calendar.DATE);
        if(daysX>0){
            monthAll=monthAll+1;
        }
        return monthAll;
    }
    
    /**
     * 年差
     * @param before
     * @param after
     * @return
     */
    public static int diffYear(Date before, Date after) {
        return getYear(after) - getYear(before);
    }
    
    /**
     * 设置23:59:59
     * @param date
     * @return
     */
    public static Date setEndDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.set(Calendar.HOUR_OF_DAY, 23);  
        calendar.set(Calendar.MINUTE, 59);  
        calendar.set(Calendar.SECOND, 59);  
        return calendar.getTime();  
    } 
    
    /**
     * 设置00:00:00
     * @param date
     * @return
     */
    public static Date setStartDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.set(Calendar.HOUR_OF_DAY, 00);  
        calendar.set(Calendar.MINUTE, 00);  
        calendar.set(Calendar.SECOND, 00);  
        return calendar.getTime();  
    }  
    
    /**
     * 获取当前时间的前一天或后一天日期
     * @param day
     * @return
     */
     public static String getBeforeOrAfterDate(int day){
         Date date=new Date();//取时间
         Calendar calendar = new GregorianCalendar();
         calendar.setTime(date);
         calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动
         date=calendar.getTime(); //这个时间就是日期往后推一天的结果
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         String dateString = formatter.format(date);

         return dateString;
     }


    public static void main(String[] args) {
        long end = Long.valueOf("1556640000000");
        Date dd = new Date(end);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(dd));
    }
}
