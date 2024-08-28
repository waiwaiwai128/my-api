package com.myapi.myapiinterface.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.myapi.myapiinterface.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 查询名称api
 *
 * @author cb
 */
@RestController
@RequestMapping("/")
public class ServiceController {

    @PostMapping("/getChineseRecipes")
    public String getChineseRecipes(@RequestBody HashMap<String, String> params, HttpServletRequest request) {
        String query = params.get("query");

        String API_KEY = "00a8159bbac54f368b8162dce732c6c7";

        // 调用 Spoonacular API 查询中国菜谱
        String url = "https://api.spoonacular.com/recipes/complexSearch";

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("query", query);
        paramMap.put("cuisine", "Chinese");
        paramMap.put("number", 5); // 返回 5 个菜谱
        paramMap.put("apiKey", API_KEY);

        // 使用 HttpUtil 发送 GET 请求并获取响应
        String response = HttpUtil.get(url, paramMap);

        // 解析 JSON 并返回结果
        JSONObject jsonObject = JSONUtil.parseObj(response);
        return jsonObject.toString();
    }

    @PostMapping("/convertCurrency")
    public String convertCurrency(@RequestBody HashMap<String, String> params, HttpServletRequest request) {
        String fromCurrency = params.get("fromCurrency");
        String toCurrency = params.get("toCurrency");
        String amount = params.get("amount");

        // 调用第三方汇率API
        String apiKey = "52ea84066f1441efdc50e968";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + fromCurrency + "/" + toCurrency + "/" + amount;

        // 使用HttpUtil发送GET请求并获取响应
        String response = HttpUtil.get(url);

        // 解析JSON并提取汇率转换结果
        JSONObject jsonObject = JSONUtil.parseObj(response);
        double conversionResult = jsonObject.getDouble("conversion_result");

        // 返回汇率转换信息
        return String.format("转换结果: %s %s = %.2f %s", amount, fromCurrency, conversionResult, toCurrency);
    }

    @PostMapping("/weather")
    public String getWeatherInfo(@RequestBody HashMap<String, String> params, HttpServletRequest request) {
        String city = params.get("city");

        // 调用第三方天气API
        String apiKey = "8b571d38a4404fa4bba174622242708";
        String url = "http://api.weatherapi.com/v1/current.json";

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", apiKey);
        paramMap.put("q", city);

        // 使用HttpUtil发送GET请求并获取响应
        String response = HttpUtil.get(url, paramMap);

        // 解析JSON并提取当前天气信息
        JSONObject jsonObject = JSONUtil.parseObj(response);
        JSONObject currentWeather = jsonObject.getJSONObject("current");

        // 提取所需的字段
        String condition = currentWeather.getJSONObject("condition").getStr("text");
        double tempC = currentWeather.getDouble("temp_c");
        double windKph = currentWeather.getDouble("wind_kph");
        int humidity = currentWeather.getInt("humidity");

        // 返回天气信息
        return String.format("当前天气: %s, 温度: %.1f°C, 风速: %.1f kph, 湿度: %d%%", condition, tempC, windKph, humidity);
    }



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
//        if(!"ak".equals(accessKey)){
//            throw new RuntimeException("无权限");
//        }
//        //todo 应该在后端保存 redis 或者 map
//        if(Long.parseLong(nonce) > 10000) {
//            throw new RuntimeException("无权限");
//        }
        //todo 时间戳 和当前时间接近
//        if(timestamp){
//
//        }
        //todo 应该从数据库查secretKey
//        String serverSign = SignUtils.genSign(body, "sk");
//        if(!sign.equals(serverSign)){
//            throw new RuntimeException("无权限");
//        }

        String result = "POST 用户名字是" + user.getUsername();
        
        return result;
    }

}
