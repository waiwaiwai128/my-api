package com.myapi.myapicommon.service;
;
import com.myapi.myapicommon.model.entity.User;


/**
 * 用户服务
 *
 * @author yupi
 */
public interface InnerUserService {

    /**
     * 获取调用的用户信息
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
