package com.linln.utill;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @ClassName:UUIDUtils.java
 * @Description:生成随机字符串的工具类 
 * @author TangJM
 * @data 2018年12月11日
 */
public class UUIDUtils {
	
	/**
	 * 32位UUID
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * UUID生成十六位数唯一订单号
	 */
	public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
//         0 代表前面补充0     
//         4 代表长度为4     
//         d 代表参数为正数型
        return  machineId+ String.format("%015d", hashCodeV);
    }
	
	/**
	 * 时间+随机数
	 * @return
	 */
	public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
	}
	

}
