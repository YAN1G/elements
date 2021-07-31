package com.xglab301.elements.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test2 {
    void  test() throws Exception {
        //String url = "http://202.114.196.192:8080/RestfulServices/api/analyze/checkInData/2";
        StringBuilder result = new StringBuilder();//访问返回结果
        BufferedReader read = null;//读取访问结果


        try {
            //创建url
            long start=System.currentTimeMillis();

            URL realurl = new URL("http://345ea19162.wicp.vip:31216/elements-0.0.1-SNAPSHOT/sanys/api/v1/invoke/main?idType=1&mdn=13012707105&encrypt=clear&idNo=372323198407090611&name=%E7%8E%8B%E6%88%90%E8%8D%A3");
            //打开连接
            URLConnection connection = realurl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立连接
            connection.connect();
            long end=System.currentTimeMillis();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段，获取到cookies等
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            read = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            //long end=System.currentTimeMillis();
            long res=end-start;
            System.out.println(res);
            String line;//循环读取
            while ((line = read.readLine()) != null) {
                result.append(line);
            }

            //result2=result.toString();
            //result2=result2.replaceAll( "\\\\","");
            //object =new JSONObject(result.toString());//源数据数组
            // data=new  Data[annotations.length()];


            //System.out.println(data);

        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            if (read != null) {//关闭流
                try {
                    read.close();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }
    }




    public static void main(String[] args) throws Exception{
//        Map<String,Object> params=new HashMap<String,Object>();
//        params.put("api_userid","SWyjsUpSySb");
//        params.put("api_data","17E7F4E2F50875D3F3CD2FE00BF53FDEAA422E5380F4503D9D149F3BF6AB99A0806C288F0CBC3E09DDA116659CC88A8C115C4E45FF4AD588D651EA1CDAADFE546D9AD5E12CF782164F2608D8ED59317A6593666636B4ABDD81E1D345FFDA2015B96E8A580817EC35320FD0152214E611ABFD81FAFFE70D717C371CEBE9FF4893");
//        JSONObject json = JSONObject.fromObject(params);
//        DataCatch dataCatch=new DataCatch();
//        System.out.println(json.toString());
//        System.out.println(dataCatch.posturl("http://sanys.86cate.com:8247/sanys/api/v1/invoke/main",json.toString()));
        String time="{\"trace\":\"\",\"code\":200,\"data\":{\"value\":\"realNameCheck:0,idTypeCheck:0,idNoCheck:0,nameCheck:0\"},\"time\":\"2021-07-10 17:24:44\"\",\"message\":\"\",\"status\":\"SUCCEED\"}";
        String time2=time.substring(104,123);
        System.out.println(time2);
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(date));

    }
}
