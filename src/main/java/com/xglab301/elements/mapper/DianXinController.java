package com.xglab301.elements.mapper;

import com.xglab301.elements.utils.AESUtil;
import com.xglab301.elements.utils.DataCatch;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DianXinController {
    @RequestMapping("/dianxinselect")
    public synchronized String ElementsSelect(@RequestParam String idType, @RequestParam String mdn, @RequestParam String encrypt, @RequestParam String idNo, @RequestParam String name)throws Exception {
        DataCatch dataCatch=new DataCatch();
        String url="http://sanys.86cate.com:8247/sanys/api/v1/invoke/main";
        Map<String,Object> paramMap=new HashMap<String,Object>();
        Map<String,Object> miParamMap=new HashMap<String,Object>();
        paramMap.put("api_userid","SWyjsUpSySb");
        paramMap.put("idType",idType);
        paramMap.put("encrypt",encrypt);
        paramMap.put("mdn",mdn);
        paramMap.put("name",name);
        paramMap.put("idNo",idNo);
        JSONObject paramsJson = JSONObject.fromObject(paramMap);
        String params=paramsJson.toString();
        String miParams = AESUtil.encrypt(params, "6369BE91F580F990015D709D4D69FA41");
        miParamMap.put("api_userid","SWyjsUpSySb");
        miParamMap.put("api_data",miParams);
        JSONObject json = JSONObject.fromObject(miParamMap);
        String data=dataCatch.testPost(url,json.toString());
        return data;
    }
    }
