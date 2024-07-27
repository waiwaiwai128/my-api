package com.myapi.myapicommon.service;

import com.myapi.myapicommon.model.entity.InterfaceInfo;

/**
* @author 陈渤
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-07-22 16:03:59
*/
public interface InnerInterfaceInfoService {

    /**
     * 获取接口信息
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
