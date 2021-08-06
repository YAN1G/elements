package com.xglab301.elements.mapper;

import com.xglab301.elements.config.Config;
import com.xglab301.elements.db.HbaseUtils;
import com.xglab301.elements.utils.MD5;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class ThreeElementsMapper {


    private static Logger logger = Logger.getLogger(ThreeElementsMapper.class);
    @RequestMapping("/hbaseselect")
    public synchronized String HbaseSelect(@RequestParam String mdn,@RequestParam  String encrypt,@RequestParam  String idNo,@RequestParam  String name)throws Exception{
        logger.info("11111");

        String idNoExits;//数据库中idno
        String nameExits;//数据库中name
        String timeExits;
        Date date = new Date();//当前日期
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MONTH, -1);//月份减一
        if(mdn.length()>20){
            if(HbaseUtils.getRow(Config.TALBE,mdn).isEmpty()) {
                return Config.ERROR;
            }
            idNoExits=HbaseUtils.getInstance().getRowQualifier(Config.TALBE,mdn,"info","idNo");
            nameExits=HbaseUtils.getInstance().getRowQualifier(Config.TALBE,mdn,"info","name");
            timeExits=HbaseUtils.getInstance().getRowQualifier(Config.TALBE,mdn,"info","create_time");
            if(idNoExits.equals(idNo)&&nameExits.equals(name)&&(Long.parseLong(timeExits)>=calendar.getTime().getTime())){
                return "success";
            }
            return Config.ERROR;
        }
        if(HbaseUtils.getRow(Config.TALBE,MD5.getInstance().getMD5(mdn)).isEmpty()){
            return Config.ERROR;
        }
        idNoExits=HbaseUtils.getInstance().getRowQualifier(Config.TALBE,MD5.getInstance().getMD5(mdn),"info","idNo");
        nameExits=HbaseUtils.getInstance().getRowQualifier(Config.TALBE,MD5.getInstance().getMD5(mdn),"info","name");
        timeExits=HbaseUtils.getInstance().getRowQualifier(Config.TALBE,MD5.getInstance().getMD5(mdn),"info","create_time");
        if(idNoExits.equals(MD5.getInstance().getMD5(idNo))&&nameExits.equals(MD5.getInstance().getMD5(name))&&(Long.parseLong(timeExits)>=calendar.getTime().getTime())){
            return "success";
        }
        return "error";
    }

    public static void main(String[] args) throws Exception{
        //System.out.println(new ThreeElementsMapper().HbaseSelect("17377940439","clear","532325200312260924","杨润婷"));
    }
}
