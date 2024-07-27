package com.myapi.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myapi.myapicommon.model.entity.InterfaceInfo;

/**
* @author 陈渤
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-07-22 16:03:59
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
