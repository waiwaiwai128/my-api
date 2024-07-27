package com.myapi.myapicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myapi.myapicommon.model.entity.UserInterfaceInfo;


/**
* @author 陈渤
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-07-23 16:28:44
*/
public interface InnerUserInterfaceInfoService {

    /**
     * 统计调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
