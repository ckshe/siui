package com.linln.utill;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class DateUtil {

    public static String date2String (Date date, String format){
        if(format==null||"".equals(format)){
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    public static Date string2Date (String datestr, String format){
        if(format==null||"".equals(format)){
            format = "yyyy-MM-dd";
        }

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date =  sdf.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 根据日期获取当周的开始结束日期
     * @param date
     * @return
     */
    public static Map<String,String> getThisWeek(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        int firstDayOfWeek = cal.getFirstDayOfWeek();
        //获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date Sunday = cal.getTime();
        String weekBegin = sdf.format(Sunday);
        System.out.println("所在周星期天的日期：" + weekBegin);
        cal.add(Calendar.DATE, 6 );
        Date Monday = cal.getTime();
        String weekEnd = sdf.format(Monday);
        System.out.println("所在周星期六的日期：" + weekEnd);
        Map<String,String > map = new HashMap<>();
        map.put("weekBegin",weekBegin);
        map.put("weekEnd",weekEnd);

        return map;
    }

    public static Date dateAddNum(Date date,Integer num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,num);
        Date newDate = cal.getTime();
        return newDate;
    }


}
