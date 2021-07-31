//package com.xglab301.elements.utils;
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import javax.servlet.http.HttpServletRequest;
//import net.sf.json.JSONObject;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.ParseException;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//public class HttpClientRequest {
//    public static String post(String url, Map<String, String> params) {
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        String body = null;
//
//        HttpPost post = postForm(url, params);
//
//        body = invoke(httpclient, post);
//
//        httpclient.getConnectionManager().shutdown();
//
//        return body;
//    }
//
//    public static String get(String url) {
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        String body = null;
//
//        HttpGet get = new HttpGet(url);
//        body = invoke(httpclient, get);
//
//        httpclient.getConnectionManager().shutdown();
//
//        return body;
//    }
//
//    private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) {
//
//        HttpResponse response = sendRequest(httpclient, httpost);
//        String body = paseResponse(response);
//
//        return body;
//    }
//
//    private static String paseResponse(HttpResponse response) {
//
//        HttpEntity entity = response.getEntity();
//
//        String charset = EntityUtils.getContentCharSet(entity);
//
//        String body = null;
//        try {
//            body = EntityUtils.toString(entity);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return body;
//    }
//
//    private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {
//
//        HttpResponse response = null;
//
//        try {
//            response = httpclient.execute(httpost);
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    private static HttpPost postForm(String url, org.json.JSONObject params) {
//        GetObject getObject=new GetObject();
//        String
//        HttpPost httpost = new HttpPost(url);
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//
//        Set<String> keySet = params.keySet();
////        for (String key : keySet) {
////            nvps.add(new BasicNameValuePair(key, params.get(key)));
////        }
//
//        try {
//
//            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return httpost;
//
//    }
//
//    public static JSONObject httpPostJson(String url, String json) {
//        HttpPost httpPost = new HttpPost(url);
//        try {
//            httpPost.setEntity(new StringEntity(json));
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        HttpClient client = new DefaultHttpClient();
//        try {
//            HttpResponse res = client.execute(httpPost);
//            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                HttpEntity entity = res.getEntity();
//                String result = EntityUtils.toString(entity,"utf-8");// 返回json格式：
//                JSONObject response = JSONObject.fromObject(result);
//                return response;
//            }
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static String getRequestBodyStr(HttpServletRequest request) {
//        String bodyStr = "";
//        System.out.println("开始解析request body");
//        try {
//            BufferedInputStream buffer = new BufferedInputStream(
//                    request.getInputStream());
//
//            int cLen = 0, bufLen = 0;
//            byte[] result = new byte[4096];
//            byte[] buf = new byte[4096];
//
//            while ((bufLen = buffer.read(buf)) > 0) {
//                if (bufLen > result.length - cLen) {
//                    byte[] tmp = new byte[result.length + 4096];
//                    System.arraycopy(result, 0, tmp, 0, cLen);
//                    result = tmp;
//                }
//                System.arraycopy(buf, 0, result, cLen, bufLen);
//                cLen += bufLen;
//            }
//            StringBuilder body = new StringBuilder(new String(Arrays.copyOf(
//                    result, cLen), "UTF-8"));
//
//            bodyStr = body.toString();
//            if (bodyStr != null && !bodyStr.equals("")) {
//                System.out.println("接口调用" + "：请求内容体:" + bodyStr);
//            } else {
//                System.out.println("接口调用" + "：请求体文本流为空");
//                return "";
//            }
//        } catch (Exception e) {
//            System.out.println("接口调用" + "：请求内容体解析异常:" + e.getMessage());
//            e.printStackTrace();
//        }
//        return bodyStr;
//    }
//}
