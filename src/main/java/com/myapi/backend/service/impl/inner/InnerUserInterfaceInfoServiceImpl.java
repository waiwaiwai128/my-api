package com.myapi.backend.service.impl.inner;

import com.myapi.backend.service.UserInterfaceInfoService;
import com.myapi.myapicommon.model.entity.UserInterfaceInfo;
import com.myapi.myapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Collection;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

}
