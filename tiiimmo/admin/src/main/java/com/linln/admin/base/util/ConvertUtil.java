package com.linln.admin.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.valueOf;

/**
 * @author zhaobc
 * @description
 * @date 2019-12-28
 */
public class ConvertUtil {

    /**
     * 对象转Int
     * @param obj
     * @return
     */
    public static Integer ConvertToInt(Object obj) {
        Integer def = 0;
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return def;
        try{
            if(String.valueOf(obj).contains("."))
            {
                return Double.valueOf(valueOf(obj)).intValue();
            }
            else {
                return Integer.parseInt(String.valueOf(obj));
            }
        }catch (Exception e)
        {
            return def;
        }
    }

    /**
     * 对象转Int
     * @param obj
     * @param def 默认值
     * @return
     */
    public static Integer ConvertToInt(Object obj,Integer def) {
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return def;
        try{
            if(String.valueOf(obj).contains("."))
            {
                return Double.valueOf(valueOf(obj)).intValue();
            }
            else {
                return Integer.parseInt(String.valueOf(obj));
            }
        }catch (Exception e)
        {
            return def;
        }
    }

    /**
     * 对象转换double
     * @param obj
     * @return
     */
    public static Double ConvertDouble(Object obj)
    {
        Double def = 0.0;
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return def;
        try {
            if(String.valueOf(obj).contains("."))
            {
                return Double.parseDouble(String.valueOf(obj));
            }
            else {
                return Integer.valueOf(valueOf(obj)).doubleValue();
            }
        }catch (Exception e)
        {
            return def;
        }
    }

    /**
     * 对象转换double
     * @param obj
     * @param def
     * @return
     */
    public static Double ConvertDouble(Object obj,Double def)
    {
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return def;
        try {
            if(String.valueOf(obj).contains("."))
            {
                return Double.parseDouble(String.valueOf(obj));
            }
            else {
                return Integer.valueOf(valueOf(obj)).doubleValue();
            }
        }catch (Exception e)
        {
            return def;
        }
    }

    /**
     * 字符串转换date
     * @param obj
     * @return
     */
    public static Date ConvertDate(Object obj )
    {
        SimpleDateFormat  dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return null;
        try {
            String newStr = String.valueOf(obj).replaceAll("/", "-");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            Date date = dateFormat.parse(newStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析带T的日期
     * @param obj
     * @return
     */
    public static Date ConvertDate2T(Object obj)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        try {
            Date date = df.parse(valueOf(obj));
            Date date1 = null;
            date1 = df1.parse(date.toString());
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据传入对象跟格式方式转换时间
     * @param obj
     * @param format
     * @return
     */
    public static Date ConvertDate(Object obj,String format )
    {
        if(format=="")
        {
            format ="yyyy-MM-dd hh:mm:ss";
        }
        SimpleDateFormat  dateFormat= new SimpleDateFormat(format);
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return null;
        try {
            String newStr = String.valueOf(obj).replaceAll("/", "-");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            Date date = dateFormat.parse(newStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 对象转字符串
     * @param obj
     * @return
     */
    public static String ConvertToString(Object obj)
    {
        if(obj == null)
            return "";
        try{
            return String.valueOf(obj);
        }catch (Exception e)
        {
            return "";
        }
    }
    /**
     * 获取转换后的doblue绝对值
     * @param obj
     * @return
     */
    public static Double ConvertDoubleAbs(Object obj)
    {
        Double def = 0.0;
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return def;
        try {
            return Math.abs(Double.parseDouble(String.valueOf(obj)));
        }catch (Exception e)
        {
            return def;
        }
    }

    /**
     * 获取转换后的int绝对值
     * @param obj
     * @return
     */
    public static Integer ConvertIntegerAbs(Object obj) {
        Integer def = 0;
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return def;
        try{
            if(String.valueOf(obj).contains("."))
            {
                return Math.abs(Double.valueOf(valueOf(obj)).intValue());
            }
            else {
                return Math.abs(Integer.parseInt(String.valueOf(obj)));
            }
        }catch (Exception e)
        {
            return def;
        }
    }

    /**
     * 日期格式转换成timestamp
     * @param obj
     * @return
     */
    public static Timestamp ConvertDate2Timestamp(Object obj){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);//设定格式
        dateFormat.setLenient(false);
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return new Timestamp(0);
        try{
            Date date =new Date(Date.parse(String.valueOf(obj)));
            //Date date = dateFormat.parse(String.valueOf(obj));
            return new Timestamp(date.getTime());
        }catch (Exception e)
        {
            return new Timestamp(0);
        }
    }
    public static Timestamp ConvertDate2Timestamp(Object obj,Timestamp def){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);//设定格式
        dateFormat.setLenient(false);
        if(obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
            return def;
        try{
            Date date =new Date(Date.parse(String.valueOf(obj)));
            //Date date = dateFormat.parse(String.valueOf(obj));
            return new Timestamp(date.getTime());
        }catch (Exception e)
        {
            return def;
        }
    }
    /**
     * 将数字转换成时间
     * @param obj 数字字符串
     * @return
     */
    public static Date ConvertNumber2Date(Object obj)
    {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("1900-01-01");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(startDate);
            if (obj == null || valueOf(obj) == "null" || valueOf(obj).length()== 0)
                return null;
            Integer day = ConvertToInt(obj);
            calendar.add(Calendar.DATE, day-2);
            return calendar.getTime();
        }catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 根据提供的字符串去重
     * @param jsonArray json串
     * @param unique 去重条件 例如 aa|bb|cc|dd
     * @return
     */
    public static Map<String, JSONObject> ConvertUniqueJsonArray(JSONArray jsonArray, String unique){

        Map<String, JSONObject> maps = new HashMap<String, JSONObject>();
        String[] uniqueArr = unique.split("\\|");
        if(uniqueArr.length>0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                StringBuffer keyName = new StringBuffer();
                for (int s = 0; s < uniqueArr.length; s++) {
                    keyName.append(obj.get(uniqueArr[s]));
                    if(!(s+1 == uniqueArr.length))
                        keyName.append("|");
                }
                String key = String.valueOf(keyName);
                maps.put(key, obj);
            }
        }
        return maps;
    }
    /**
     * 获取某个月份天数
     *
     * @param date
     * @return
     */
    public static int ConvertGetDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间前一天
     * @param date
     * @return
     */
    public static String ConvertGetCurrentDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        int year = calendar.get(Calendar.YEAR);//年份
        int month = calendar.get(Calendar.MONTH) + 1;//月份
        int day = calendar.get(Calendar.DATE);//月份
        //简单处理1-9的时间
        String dayStr = valueOf(day);
        String monthStr = valueOf(month);
        if(day<10)dayStr = "0"+day;
        if(month<10)monthStr = "0"+valueOf(month);
        String aDate = String.valueOf(year)+"-"+monthStr+"-"+dayStr;
        return aDate;
    }
    /**
     * 可传入一些自己组装好的list
     * 获取当前月每天的日期
     * @return 返回集合
     */
    public static List<Object> ConvertGetDayListOfMonth(Date date) {
        List<Object> list = new ArrayList<Object>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);//年份
        int month = calendar.get(Calendar.MONTH) + 1;//月份
        int day = calendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            //简单处理1-9的时间
            String dayStr = valueOf(i);
            String monthStr = valueOf(month);
            if(i<10)dayStr = "0"+i;
            if(month<10)monthStr = "0"+valueOf(month);
            String aDate = String.valueOf(year)+"-"+monthStr+"-"+dayStr;
            list.add(aDate);
        }
        return list;
    }
}
