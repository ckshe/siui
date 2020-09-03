package com.linln.utill;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linln.RespAndReqs.ScheduleJobReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ApiUtil {

    private static final Logger logger = LoggerFactory.getLogger(ApiUtil.class);

    public static JSONArray postToScheduleJobApi(String scheduleJobUrl, ScheduleJobReq scheduleJobReq){
        scheduleJobReq.setPageSize(10000);
        scheduleJobReq.setIsReRun(1);
        scheduleJobReq.setPageIndex(1);

        RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ZERO).setReadTimeout(Duration.ZERO).build();
        JSONObject s = (JSONObject) JSON.toJSON(scheduleJobReq);
        System.out.println(s.toString());
        JSONArray result = new JSONArray();
        //logger.info("-----json:"+s.toJSONString());
        try {
            logger.info("Begin get data by first page ...");
            JSONObject dd = restTemplate.postForEntity(scheduleJobUrl,s, JSONObject.class).getBody();
            System.out.println(dd.toString());
            logger.info("Finish get first page data");
            Integer pageIndex = dd.getInteger("pageIndex");
            Integer total = dd.getInteger("total");
            Integer size = dd.getInteger("pageSize");
            result = dd.getJSONArray("data");
            if(result == null){
                return new JSONArray();
            }
            //logger.info("-----result:"+result.toJSONString());
            Integer tempSum = size;
            boolean flag = (total>0 && total>size);
            while (flag) {
                scheduleJobReq.setIsReRun(0);
                pageIndex ++ ;
                tempSum = tempSum + size;
                scheduleJobReq.setPageIndex(pageIndex);
                s = (JSONObject) JSON.toJSON(scheduleJobReq);
                System.out.println(s.toString());
                System.out.println("---------url:"+scheduleJobUrl);
                logger.info("Begin get data by page " + pageIndex.toString() + "...");
                JSONObject dd2 = restTemplate.postForEntity(scheduleJobUrl,s, JSONObject.class).getBody();
                logger.info("Finish get data by page " + pageIndex.toString() + ".");
                JSONArray ree = dd2.getJSONArray("data");
                result.addAll(ree);
                flag = tempSum <total;
            }
            if(result == null) {
                result =  new JSONArray();
            }
            return result;
        } catch (Exception e){
            logger.error(e.getMessage());
            return result;
        }
    }
}
