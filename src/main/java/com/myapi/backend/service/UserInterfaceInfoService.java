package com.myapi.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myapi.backend.model.entity.InterfaceInfo;
import com.myapi.backend.model.entity.UserInterfaceInfo;

/**
* @author 陈渤
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-07-23 16:28:44
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo UserInterfaceInfo, boolean add);

    /**
     * 统计调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(Long interfaceInfoId, long userId);
}
