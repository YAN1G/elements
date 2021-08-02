package com.xglab301.elements.controller;

import com.xglab301.elements.Entities.UserData;
import com.xglab301.elements.db.HbaseUtils;
import com.xglab301.elements.db.JedisPoolUtil;
import com.xglab301.elements.mapper.DianXinController;
import com.xglab301.elements.mapper.ScheduleTask;
import com.xglab301.elements.mapper.ThreeElementsMapper;
import com.xglab301.elements.utils.AESUtil;
import com.xglab301.elements.utils.DataCatch;
import org.apache.log4j.Logger;
import com.xglab301.elements.utils.GetObject;
import com.xglab301.elements.utils.MD5;
import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sanys")
public class ThreeElementsController {
    private static Logger logger = Logger.getLogger(ThreeElementsController.class);

    @RequestMapping("/api/v1/invoke/main")
    public synchronized String ElementsSelect(@RequestParam String idType, @RequestParam String mdn, @RequestParam String encrypt, @RequestParam String idNo, @RequestParam String name) throws Exception {

        //logger.info("idNo:" + idNo + "  mdn:" + mdn + "  name:" + name);
        String strres = "{\"trace\":\"\",\"code\":200,\"data\":{\"value\":\"realNameCheck:0,idTypeCheck:0,idNoCheck:0,nameCheck:0\"},\"time\":\"2021-07-15 20:07:26\",\"message\":\"\",\"status\":\"SUCCEED\"}";
        long start1 = System.currentTimeMillis();
        JSONObject res = null;
        String equalData = "realNameCheck:0,idTypeCheck:0,idNoCheck:0,nameCheck:0";
        UserData userData = new UserData();
        UserData userData2 = new UserData();
        Jedis jedis = null;
        JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
        String key = MD5.getInstance().getMD5(mdn + idNo + name);
        String value = "{\"code\":221,\"message\":\"Error Value\"}";
        jedis = jedisPool.getResource();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Date afterDate = new Date(now.getTime());
        System.out.println("2:" + System.currentTimeMillis());
        try {
            if (jedis.exists(key)) {
                Thread.sleep(100);
                System.out.println("5");
                String str = jedis.get(key);
                if (str.length() < 100) {
                    return str;
                }
                long enddian = System.currentTimeMillis();
                long timedian = enddian - start1;
                //logger.info(enddian-start1);
                String spit = str.substring(104, 123);
                //logger.info("Redis查询成功,调用时间为：" + timedian);
                return str.replace(spit, sdf.format(afterDate));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            JedisPoolUtil.close(jedis);
        }
        //System.out.println("3:"+System.currentTimeMillis());
//        try {
        if (new ThreeElementsMapper().HbaseSelect(mdn, encrypt, idNo, name).equals("success")) {
            Thread.sleep(80);
            long end = System.currentTimeMillis();
            //logger.info("Hbase查询成功，时长" + (end - start1));
            String spit = strres.substring(104, 123);
            return strres.replace(spit, sdf.format(afterDate));
        } else if (new ThreeElementsMapper().HbaseSelect(mdn, encrypt, idNo, name).equals("error")) {
            //logger.info("a");
            long start = System.currentTimeMillis();
            String data = new DianXinController().ElementsSelect(idType, mdn, encrypt, idNo, name);
            System.out.println(data);
            res = JSONObject.fromObject(data);
            try {
                if (res.getString("code").equals("200") || res.getString("code").equals("221") || res.getString("code").equals("224"))
                    jedis.set(key, data);
                jedis.expire(key, 86400);
                //logger.info("A:数据成功存入Redis");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                JedisPoolUtil.close(jedis);
            }
            if (res.getString("code").equals("200")) {
                JSONObject resData = res.getJSONObject("data");
                String resValue = resData.getString("value");
                if (resValue.equals(equalData)) {
                    if (mdn.length() > 16) {
                        userData.setEncrypt(encrypt);
                        userData.setIdNO(idNo);
                        userData.setMdn(mdn);
                        userData.setName(name);
                        userData.setIdType(idType);
                        //     System.out.println("8:"+System.currentTimeMillis());
                        ScheduleTask.copyOnWriteArrayList.add(userData);
                        //  System.out.println("9:"+System.currentTimeMillis());
                    } else if ("sha256" == encrypt) {
                        System.out.println(mdn);
                    } else {
                        userData2.setEncrypt(encrypt);
                        userData2.setIdNO(MD5.getInstance().getMD5(idNo));
                        userData2.setMdn(MD5.getInstance().getMD5(mdn));
                        userData2.setName(MD5.getInstance().getMD5(name));
                        userData2.setIdType(idType);
                        //System.out.println("11:"+System.currentTimeMillis());
                        ScheduleTask.copyOnWriteArrayList.add(userData2);
                    }
                }

            }
            long end = System.currentTimeMillis();
            long res2 = end - start;
            //logger.info("A:电信接口调用时间"+res2);
            System.out.println(res2);
            System.out.println(data);

            long end2 = System.currentTimeMillis();
            long res3 = end2 - start1;
            //logger.info("A:电信接口总调用时长" + res3);
            //logger.info("电信接口查询成功，返回正确");
            return data;
        }
//       }catch (NullPointerException e){
//           // logger.info("b");
//            long start=System.currentTimeMillis();
//           String data=new DianXinController().ElementsSelect(idType,mdn,encrypt,idNo,name);
//           res= JSONObject.fromObject(data);
//            JSONObject resData=res.getJSONObject("data");
//            String resValue=resData.getString("value");
//            long end=System.currentTimeMillis();
//            long res2=end-start;
//            logger.info("B:电信接口调用时间"+res2);
//            System.out.println(res2);
//            System.out.println(data);
//            try {
//                if(res.getString("code").equals("200")||res.getString("code").equals("221")||res.getString("code").equals("224"))
//                    jedis.set(key,data);
//                jedis.expire(key,86400);
//                logger.info("数据成功存入Redis");
//            } catch (Exception ee) {
//                ee.printStackTrace();
//            }finally{
//                JedisPoolUtil.close(jedis);
//            }
//           if(res.getString("code").equals("200")&&resValue.equals(equalData)){
//               if(mdn.length()>16) {
//                   userData.setEncrypt(encrypt);
//                   userData.setIdNO(idNo);
//                   userData.setMdn(mdn);
//                   userData.setName(name);
//                   userData.setIdType(idType);
//              //     System.out.println("8:"+System.currentTimeMillis());
//                   ScheduleTask.copyOnWriteArrayList.add(userData);
//                 //  System.out.println("9:"+System.currentTimeMillis());
//               } else if("sha256"==encrypt){
//                   System.out.println(mdn);
//               }else{
//                   userData.setEncrypt(encrypt);
//                   userData.setIdNO(MD5.getInstance().getMD5(idNo));
//                   userData.setMdn(MD5.getInstance().getMD5(mdn));
//                   userData.setName(MD5.getInstance().getMD5(name));
//                   userData.setIdType(idType);
//                   //System.out.println("11:"+System.currentTimeMillis());
//                   ScheduleTask.copyOnWriteArrayList.add(userData);
//               }
//           }
//            long end2=System.currentTimeMillis();
//            long res3=end2-start1;
//            logger.info("电信接口总调用时长"+res3);
//            logger.info("电信接口查询成功，返回正确");
//           return data;
//       }
//
//            try {if(res.getString("code").equals("221")||res.getString("code").equals("224"))
//                jedis.set(key,res.toString());
//                jedis.expire(key,86400);
//                logger.info("数据成功存入Redis");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }finally{
//                JedisPoolUtil.close(jedis);
//            }
//
//
//        //System.out.println("14:"+System.currentTimeMillis());
//        logger.info("电信接口查询成功，返回错误");
        return "{\"code\":221,\"message\":\"Error Value\"}";
    }


    public static void main(String[] args) throws Exception {
        ThreeElementsController threeElementsController = new ThreeElementsController();
        long start = System.currentTimeMillis();
        String data = threeElementsController.ElementsSelect("1", "13534164065", "clear", "441624197612125815", "陈小雅");
        long end = System.currentTimeMillis();
        long res = end - start;
        System.out.println(res);
        System.out.println(data);
    }
}
