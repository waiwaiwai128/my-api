package com.myapi.myapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名根据
 * @author cb
 */
public class SignUtils {
    /**
     * 生成签名
     * @param body
     * @param secretKey
     * @return
     *
     */
    public static String genSign(String body, String secretKey){
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        String digestHex = md5.digestHex(content);
        return digestHex;
    }

}
