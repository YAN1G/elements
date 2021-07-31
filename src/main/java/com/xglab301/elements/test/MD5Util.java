package com.xglab301.elements.test;
import java.io.IOException;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;


public class MD5Util {
    /**
     * MD5加密
     */
    public static String md5Encryption(String str)  {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * base64加密
     */
    public static String base64Encryption(String str)  {
        if (str == null) return null;
        String encodeStr = "";

        try {
            BASE64Encoder b64Encoder = new BASE64Encoder();
            encodeStr = b64Encoder.encode(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return encodeStr;
    }
    /**
     * base64解密
     */
    public static String base64Dcrypt(String str)  {
        if (str == null) return null;
        String decoderStr = "";

        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(str);
            decoderStr = new String(b);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return decoderStr;
    }

    public static void main(String[] args) {
        System.out.println(base64Dcrypt("17607128769"));
    }
}