package com.myapi.myapiinterface.controller;

import com.myapi.myapiclientsdk.utils.SignUtils;
import com.myapi.myapiinterface.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 查询名称api
 *
 * @author cb
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNamebyGet(String name, HttpServletRequest request){
        System.out.println(request.getHeader("yupi"));

        return "GET 你的名字是"+name;
    }

    @PostMapping("/post")
    public String getNamebyPost(@RequestParam String name){
        return "POST 你的名字是"+name;
    }

    @PostMapping("/user")
    public String getUserNamebyPost(@RequestBody User user, HttpServletRequest request){
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        // 解码解决中文乱码
//        try {
//            body = URLDecoder.decode(body,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }


        //todo 应该去数据库查
        if(!"ak".equals(accessKey)){
            throw new RuntimeException("无权限");
        }
        //todo 应该在后端保存 redis 或者 map
        if(Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        //todo 时间戳 和当前时间接近
//        if(timestamp){
//
//        }
        //todo 应该从数据库查secretKey
        String serverSign = SignUtils.genSign(body, "sk");
        if(!sign.equals(serverSign)){
            throw new RuntimeException("无权限");
        }

        String result = "POST 用户名字是" + user.getUsername();
        
        return result;
    }
}
