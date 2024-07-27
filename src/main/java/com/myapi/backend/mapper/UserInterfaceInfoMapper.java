package com.myapi.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myapi.myapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 陈渤
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-07-23 16:28:44
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




