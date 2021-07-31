package com.xglab301.elements.mapper;

import com.xglab301.elements.db.HbaseUtils;
import com.xglab301.elements.utils.MD5;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class ThreeElementsMapper {
    @RequestMapping("/hbaseselect")
    public synchronized String HbaseSelect(@RequestParam String mdn,@RequestParam  String encrypt,@RequestParam  String idNo,@RequestParam  String name)throws Exception{
        String idNoExits;//数据库中idno
        String nameExits;//数据库中name
        String timeExits;
        System.out.println(mdn);
        System.out.println(MD5.getInstance().getMD5(mdn));
        Date date = new Date();//当前日期
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MONTH, -1);//月份减一
        System.out.println(calendar.getTime().getTime());//输出格式化的日期
        //if(Long.parseLong(timeExits)>=calendar.getTime().getTime()){
        if(mdn.length()>20){
            if(HbaseUtils.getInstance().getRow("THREEELEMENTS",mdn).isEmpty()) {
                System.out.println(1);
                return "error";
            }
            idNoExits=HbaseUtils.getInstance().getRowQualifier("THREEELEMENTS",mdn,"info","idNo");
            nameExits=HbaseUtils.getInstance().getRowQualifier("THREEELEMENTS",mdn,"info","name");
            timeExits=HbaseUtils.getInstance().getRowQualifier("THREEELEMENTS",mdn,"info","create_time");
            if(idNoExits.equals(idNo)&&nameExits.equals(name)&&(Long.parseLong(timeExits)>=calendar.getTime().getTime()))return "success";
            return "error";
        }
        if(HbaseUtils.getInstance().getRow("THREEELEMENTS",MD5.getInstance().getMD5(mdn)).isEmpty()){
            System.out.println("empty");
            return "error";
        }
        idNoExits=HbaseUtils.getInstance().getRowQualifier("THREEELEMENTS",MD5.getInstance().getMD5(mdn),"info","idNo");
        nameExits=HbaseUtils.getInstance().getRowQualifier("THREEELEMENTS",MD5.getInstance().getMD5(mdn),"info","name");
        timeExits=HbaseUtils.getInstance().getRowQualifier("THREEELEMENTS",MD5.getInstance().getMD5(mdn),"info","create_time");
        if(idNoExits.equals(MD5.getInstance().getMD5(idNo))&&nameExits.equals(MD5.getInstance().getMD5(name))&&(Long.parseLong(timeExits)>=calendar.getTime().getTime()))return "success";
        System.out.println("-------");
        return "error";
       // }
       // return "error";
    }

    public static void main(String[] args) throws Exception{
        System.out.println(new ThreeElementsMapper().HbaseSelect("17377940439","clear","532325200312260924","杨润婷"));
    }
}
