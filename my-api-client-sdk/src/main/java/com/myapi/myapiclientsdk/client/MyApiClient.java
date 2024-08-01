package com.myapi.myapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.myapi.myapiclientsdk.model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.myapi.myapiclientsdk.utils.SignUtils.genSign;


/**
 * 调用第三方接口的客户端
 *
 * @author cb
 */
public class MyApiClient {

    private static final String
            GATEWAY_HOST = "http://localhost:8090";

    private String accessKey;

    private String secretKey;



    public MyApiClient(String accessKey, String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    public String getNamebyGet(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result3= HttpUtil.get(GATEWAY_HOST + "/api/get", paramMap);
        System.out.println(result3);
        return result3;
    }


    public String getNamebyPost(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result3= HttpUtil.post(GATEWAY_HOST + "/api/post", paramMap);
        System.out.println(result3);
        return result3;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        //编码解决中文乱码
//        String encode ="";
//        try {
//            encode = URLEncoder.encode(body, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        hashMap.put("accessKey", accessKey);
        //不要传secretKey
        //hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        //hashMap.put("body", encode);
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }

    public String getUserNamebyPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
