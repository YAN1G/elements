package com.xglab301.elements.mapper;

import com.xglab301.elements.db.HbaseUtils;
import com.xglab301.elements.utils.MD5;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
//@RestController
public class ThreeElementsInsert {
    @RequestMapping("/insert")
    public void DataInsert(@RequestParam String idType, @RequestParam String mdn, @RequestParam String encrypt, @RequestParam String idNo, @RequestParam String name){
        HbaseUtils.getInstance().put("THREEELEMENTS", mdn, "info", "create_time",String.valueOf(new Date().getTime()));
        HbaseUtils.getInstance().put("THREEELEMENTS", mdn, "info", "idType", idType);
        HbaseUtils.getInstance().put("THREEELEMENTS", mdn, "info", "idNo", idNo);
        HbaseUtils.getInstance().put("THREEELEMENTS", mdn, "info", "encrypt", encrypt);
        HbaseUtils.getInstance().put("THREEELEMENTS", mdn, "info", "name", name);
    }
    @RequestMapping("/insertmd5")
    public synchronized void MD5DataInsert(@RequestParam String idType, @RequestParam String mdn, @RequestParam String encrypt, @RequestParam String idNo, @RequestParam String name){
        HbaseUtils.getInstance().put("THREEELEMENTS", MD5.getInstance().getMD5(mdn), "info", "create_time",String.valueOf(new Date().getTime()));
        HbaseUtils.getInstance().put("THREEELEMENTS", MD5.getInstance().getMD5(mdn), "info", "idType", idType);
        HbaseUtils.getInstance().put("THREEELEMENTS", MD5.getInstance().getMD5(mdn), "info", "idNo", MD5.getInstance().getMD5(idNo));
        HbaseUtils.getInstance().put("THREEELEMENTS", MD5.getInstance().getMD5(mdn), "info", "encrypt", encrypt);
        HbaseUtils.getInstance().put("THREEELEMENTS", MD5.getInstance().getMD5(mdn), "info", "name", MD5.getInstance().getMD5(name));
    }

    public static void main(String[] args) {
new ThreeElementsInsert().DataInsert("1",MD5.getInstance().getMD5("15365016879"),"clear",MD5.getInstance().getMD5("320121199006012137"),MD5.getInstance().getMD5("陈正庆"));
    }
}
