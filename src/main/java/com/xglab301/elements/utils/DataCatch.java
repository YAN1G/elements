package com.xglab301.elements.utils;


import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class DataCatch {
    public String testPost(String urlStr, String name) {
        String res=null;
        JSONObject object=null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            HttpURLConnection con = (HttpURLConnection) conn;
            con.setDoOutput(true);
            //con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Charsert", "UTF-8");
            //con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
           // JSONObject json = JSONObject.fromObject(paramMap);
            //String xmlInfo = json.toString();
            System.out.println("urlStr=" + urlStr);
            //System.out.println("xmlInfo=" + xmlInfo);
            out.write(name);
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
               res=line;
                //System.out.println(line);
            }
            //System.out.println(line);
            object=JSONObject.fromObject(res);
            //System.out.println(object);
            return object.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public static void main(String[] args) {
        String strURL = "http://sanys.86cate.com:8247/sanys/api/v1/invoke/main";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("api_userid", "SWyjsUpSySb");
        paramMap.put("api_data","17E7F4E2F50875D3F3CD2FE00BF53FDEBF429A891D768E220B4D4DAC63A28D2DF0CAB2E2C32E91ED6E1EC4AF619AF2F54C6B01AD04E2A07E3B48DEA6C78C2457AAF97B135527554232D951A53B8D435CEDC9B4AAD8760EA93576F4E72E291FBB2B2A5180D70115968CFA2347A42B74EF");
        JSONObject json = JSONObject.fromObject(paramMap);
        DataCatch dataCatch=new DataCatch();
       //dataCatch.testPost(strURL,json.toString());
        System.out.println(dataCatch.testPost(strURL,json.toString()));
    }
}
