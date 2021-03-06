package com.xglab301.elements.db;

import org.slf4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
public class JedisPoolUtil
{
    private static volatile JedisPool jedisPool = null;

    private static Logger log=null;
    private JedisPoolUtil(){}

    public static JedisPool getJedisPoolInstance()
    {
        if(null == jedisPool)
        {
            synchronized (JedisPoolUtil.class)
            {
                if(null == jedisPool)
                {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    //poolConfig.setMaxActiv(1000);
                    poolConfig.setMaxIdle(32);
                    poolConfig.setMaxTotal(10000);
                    poolConfig.setMaxWaitMillis(100*1000);
                    poolConfig.setTestOnBorrow(true);

                    jedisPool = new JedisPool(poolConfig,"127.0.0.1",6379);
                }
            }
        }
        return jedisPool;
    }

    public static void close(Jedis jedis){
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            log.error("return redis resource exception", e);
        }
    }

}

