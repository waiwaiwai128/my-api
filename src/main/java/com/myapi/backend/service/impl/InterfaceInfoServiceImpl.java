package com.myapi.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myapi.backend.common.ErrorCode;
import com.myapi.backend.exception.BusinessException;
import com.myapi.backend.service.InterfaceInfoService;
import com.myapi.backend.model.entity.InterfaceInfo;
import com.myapi.backend.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 陈渤
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-07-22 16:03:59
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        Long id = interfaceInfo.getId();
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        Integer status = interfaceInfo.getStatus();
        String method = interfaceInfo.getMethod();
        Long userId = interfaceInfo.getUserId();
        Date createTime = interfaceInfo.getCreateTime();
        Date updateTime = interfaceInfo.getUpdateTime();
        Integer isDelete = interfaceInfo.getIsDelete();

        // 校验名称字段
//        if (add) {
//            // 在添加时，名称不能为空
//            if (StringUtils.isBlank(name)) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称不能为空!");
//            }
//        }
//        // 名称长度不能超过50
//        if (StringUtils.isNotBlank(name) && name.length() > 50) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长!");
//        }
//
//        // 校验URL字段
//        if (StringUtils.isNotBlank(url)) {
//            // URL的格式校验（简单的正则表达式示例，具体情况可以根据需要调整）
//            String urlPattern = "^(http|https)://.*";
//            if (!url.matches(urlPattern)) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "URL格式不正确!");
//            }
//        }
//
//        // 校验请求头字段
//        if (StringUtils.isNotBlank(requestHeader) && requestHeader.length() > 500) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求头过长!");
//        }
//
//        // 校验响应头字段
//        if (StringUtils.isNotBlank(responseHeader) && responseHeader.length() > 500) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "响应头过长!");
//        }
//
//        // 校验状态字段
//        if (status == null || !(status == 0 || status == 1)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "状态无效!");
//        }
//
//        // 校验方法字段
//        if (StringUtils.isNotBlank(method) && !("GET".equalsIgnoreCase(method) || "POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "HTTP方法无效!");
//        }
//
//        // 校验用户ID
//        if (userId == null || userId <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户ID无效!");
//        }
//
//        // 校验创建时间和更新时间字段
//        if (createTime != null && updateTime != null && createTime.after(updateTime)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建时间不能晚于更新时间!");
//        }
//
//        // 校验是否删除字段
//        if (isDelete == null || !(isDelete == 0 || isDelete == 1)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除标志无效!");
//        }
    }
}




