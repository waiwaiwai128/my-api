package com.myapi.myapigateway;

import cn.hutool.jwt.Claims;
import com.myapi.myapiclientsdk.utils.SignUtils;
import com.sun.deploy.util.StringUtils;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 用户发送请求到API网关
        // 2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + request.getPath().value());
        log.info("请求方法：" + request.getMethod());
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());
        //拿到响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 3. （黑白名单）
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            return handleNoAuth(response);
        }
        // 4. 用户鉴权（ak，sk）
        // 从请求头中获取参数
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");

        // todo 实际情况应该是去数据库中查是否已分配给用户
        if (!"ak".equals(accessKey)){
            return handleNoAuth(response);
        }
        // 直接校验如果随机数大于1万，则抛出异常，并提示"无权限"
        if (Long.parseLong(nonce) > 10000) {
            return handleNoAuth(response);
        }

        // 时间和当前时间不能超过5分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        Long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES)  {
            return handleNoAuth(response);
        }

        // todo 实际情况中是从数据库中查出 secretKey
        String serverSign = SignUtils.genSign(body, "sk");
        // 如果生成的签名不一致，则抛出异常，并提示"无权限"
        if (!sign.equals(serverSign)) {
            return handleNoAuth(response);
        }
        // 5. 用户请求的模拟接口是否存在
        // todo 从数据库查询接口
        // 6. 请求转发，调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);
        // 7. 响应日志
        return handleResponse(exchange, chain);
//        log.info("响应：" + response.getStatusCode());
        // todo 8. 调用成功，接口调用次数+1 invokeCount
//        if(response.getStatusCode() != HttpStatus.OK) {
//            // todo invokeCount
//        }
//        // 9. 调用失败，返回错误码
//        else {
//            return handleInvokeError(response);
//        }
//        return filter;
//        return chain.filter(exchange);
    }

    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain){
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if(statusCode == HttpStatus.OK){
                // 装饰器
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值写数据
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // todo 8. 调用成功，接口调用次数+1 invokeCount

                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                sb2.append("<--- {} {} \n");
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                // 打印日志
                                log.info("响应结果："+data);
                                log.info(sb2.toString(), rspArgs.toArray());//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                //设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("网关处理响应异常：" + e);
            return chain.filter(exchange);
        }

    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
