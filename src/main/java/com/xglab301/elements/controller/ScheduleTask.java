package com.xglab301.elements.controller;

import com.xglab301.elements.Entities.ParamsData;
import com.xglab301.elements.Entities.UserData;
import com.xglab301.elements.db.HbaseUtils;
import com.xglab301.elements.mapper.ThreeElementsInsert;
import com.xglab301.elements.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);
     public static  CopyOnWriteArrayList<UserData> copyOnWriteArrayList=new CopyOnWriteArrayList();
    @Scheduled(cron = "*/5 * * * * ?")

    public synchronized void execute() throws Exception{
        ThreeElementsInsert threeElementsInsert=new ThreeElementsInsert();
        try {
            for(UserData userData:copyOnWriteArrayList){
                threeElementsInsert.DataInsert(userData.getIdType(),userData.getMdn(),userData.getEncrypt(),userData.getIdNO(),userData.getName());
                logger.info("数据成功存入Hbase");
                copyOnWriteArrayList.remove(userData);
            }
        }catch (NullPointerException e){
            logger.info(e.toString());
        }

        logger.info(String.valueOf(System.currentTimeMillis()));

    }
}
