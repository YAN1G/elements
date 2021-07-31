package com.xglab301.elements.test;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String data="{\"trace\":\"\",\"code\":200,\"data\":{\"value\":\"realNameCheck:0,idTypeCheck:0,idNoCheck:0,nameCheck:0\"},\"time\":\"2021-07-15 20:07:26\",\"message\":\"\",\"status\":\"SUCCEED\"}";
        JSONObject res=JSONObject.fromObject(data);
        JSONObject resData=res.getJSONObject("data");
        String value=resData.getString("value");
        System.out.println(value);
        System.out.println(new Date().getTime());
        Date now = new Date();
        Date afterDate = new Date(now .getTime()+1000*60*60*24*30);
        System.out.println(afterDate.getTime());
        Date date = new Date();//当前日期
        //SimpleDateFormat() sdf = new SimpleDateFormat("yyyy/MM/dd");//格式化对象
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MONTH, -1);//月份减一
        System.out.println(calendar.getTime().getTime());//输出格式化的日期

    }
}
