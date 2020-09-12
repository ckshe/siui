package com.linln.utill;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DateUtil {



    //判断时间是否在该时间段内
    public static  boolean isTimeRange(String start,String end){

        Integer ssecondSum =  caculateSecond(start);
        Integer esecondSum = caculateSecond(end);
        if(ssecondSum>esecondSum){
            esecondSum = esecondSum + 86400;
        }
        Date date = new Date();
        String today = date2String(date,"HH:mm:ss");
        Integer datee = caculateSecond(today);
        if(datee>=ssecondSum&&datee<esecondSum){
            return true;
        }else {
            return false;
        }
    }

    public static Integer caculateSecond(String time){
        String [] d = time.split(":");
        Integer hour = Integer.parseInt(d[0]);
        Integer minute = Integer.parseInt(d[1]);
        Integer second = Integer.parseInt(d[2]);
        Integer secondSum = hour*60*60+minute*60+second;
        if(secondSum==0){
            secondSum = 86400;
        }
        return secondSum;
    }



    //时间往前推n个小时
    public static Date dateAddHours(Date date,int hour){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        Date xdate = cal.getTime();
        String xday = date2String(xdate,"yyyy-MM-dd HH:mm:ss");
        return xdate;
    }

    //date转string
    public static String date2String (Date date, String format){
        if(format==null||"".equals(format)){
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
    }

    //string 转date
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
        //设置一个星期的第一天
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        int firstDayOfWeek = cal.getFirstDayOfWeek();
        //获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
//        if(dayWeek==1){
//            dayWeek = -7;
//        }
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

    //日期加减
    public static Date dateAddNum(Date date,Integer num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,num);
        Date newDate = cal.getTime();
        return newDate;
    }
    //返回两个时间差的分钟数
    public static BigDecimal differTwoDate(Date startTime ,Date endTime){
        long differ = endTime.getTime() - startTime.getTime();
        BigDecimal result = new BigDecimal(differ/(1000*60)).setScale(0,BigDecimal.ROUND_UP);

        return result;
    }

    //返回两天之间的日期集合
    public static List<String> dayBetweenTwoDate(Date start,Date end){
        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(Calendar.DATE,1);
        while (tempStart.before(tempEnd)) {
            days.add(dateFormat.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }

        return days;

    }

    //返回截至当天所在周的日期集合
    public static List<String> getThisWeekDayListUtillToday(Date date){
        Map<String, String> thisWeek = getThisWeek(date);
        String startTime = thisWeek.get("weekBegin")+" 00:00:00";
        String endTime = thisWeek.get("weekEnd")+" 23:59:59";
        List<String> daylist = dayBetweenTwoDate(string2Date(startTime, ""), string2Date(endTime, ""));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        List<String> daylistresult = daylist.subList(1,dayWeek);
        return daylistresult;

    }

    //获取当天日期 201010格式
    public static String getTodayStringForProcessTaskCode(){
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        String result = strNow[0].substring(2,strNow[0].length())+strNow[1]+strNow[2];

        return result;
    }

    //日期比较前后
    public static int compareDate(Date one,Date two){
        String onestr = date2String(one,"");
        String twostr = date2String(two,"");
        one = string2Date(onestr,"");
        two = string2Date(twostr,"");


        long onelong = one.getTime();
        long twolong = two.getTime();

        return (onelong<twolong ? -1 : (onelong==twolong ? 0 : 1));



    }

    public static void main(String[] args) {
        //String day = dateAddHours("2020-06-30 00:00:00",-1);
        //System.out.println(day);
    }
}
