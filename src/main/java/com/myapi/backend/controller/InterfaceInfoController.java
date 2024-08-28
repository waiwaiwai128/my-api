package com.myapi.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.myapi.backend.annotation.AuthCheck;
import com.myapi.backend.common.*;
import com.myapi.backend.constant.CommonConstant;
import com.myapi.backend.constant.UserConstant;
import com.myapi.backend.exception.BusinessException;
import com.myapi.backend.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.myapi.backend.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.myapi.backend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.myapi.backend.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.myapi.backend.model.enums.InterfaceInfoStatusEnum;
import com.myapi.backend.model.enums.InterfaceMethodEnum;
import com.myapi.backend.service.InterfaceInfoService;
import com.myapi.backend.service.UserService;
import com.myapi.myapiclientsdk.client.MyApiClient;
import com.myapi.myapicommon.model.entity.InterfaceInfo;
import com.myapi.myapicommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 接口管理
 *
 * @author cdd
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Autowired
    private MyApiClient myApiClient;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
        @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // content 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    // endregion


    /**
     * 上线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/online")
        @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        com.myapi.myapiclientsdk.model.User user = new com.myapi.myapiclientsdk.model.User();
        user.setUsername("test");
        String username = myApiClient.getUserNamebyPost(user);
        if(StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口验证失败！");
        }

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());


        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 下线
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
        @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        com.myapi.myapiclientsdk.model.User user = new com.myapi.myapiclientsdk.model.User();
        user.setUsername("test");
        String username = myApiClient.getUserNamebyPost(user);
        if(StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口验证失败！");
        }

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());


        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 测试调用
     *
     * @param interfaceInfoInvokeReuest
     * @param request
     * @return
     */
    /*@PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeReuest, HttpServletRequest request) {
        if (interfaceInfoInvokeReuest == null || interfaceInfoInvokeReuest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = interfaceInfoInvokeReuest.getId();
        String userRequestParams = interfaceInfoInvokeReuest.getUserRequestParams();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if(oldInterfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }

        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        MyApiClient tempClient = new MyApiClient(accessKey, secretKey);
        Gson gson = new Gson();
        com.myapi.myapiclientsdk.model.User user = gson.fromJson(userRequestParams, com.myapi.myapiclientsdk.model.User.class);
        String userNamebyPost = tempClient.getUserNamebyPost(user);
        return ResultUtils.success(userNamebyPost);
    }*/

    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeReuest, HttpServletRequest request) {
        if (interfaceInfoInvokeReuest == null || interfaceInfoInvokeReuest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = interfaceInfoInvokeReuest.getId();
        String userRequestParams = interfaceInfoInvokeReuest.getUserRequestParams();
        String methodName = interfaceInfoInvokeReuest.getName();

        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if(oldInterfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }

        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        MyApiClient tempClient = new MyApiClient(accessKey, secretKey);

        // 选择要调用的接口方法
        InterfaceMethodEnum methodEnum = InterfaceMethodEnum.getByMethodName(methodName);
        if (methodEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "方法名无效");
        }

        Gson gson = new Gson();
        String result;

        switch (methodEnum) {
            case GET_USER_NAME_BY_POST:
                com.myapi.myapiclientsdk.model.User user = gson.fromJson(userRequestParams, com.myapi.myapiclientsdk.model.User.class);
                result = tempClient.getUserNamebyPost(user);
                break;

            case GET_WEATHER_BY_CITY:
                Map<String, String> params = gson.fromJson(userRequestParams, Map.class);
                String city = params.get("city");
                result = tempClient.getWeatherByCity(city);
                break;

            case CONVERT_CURRENCY:
                Map<String, Object> currencyParams = gson.fromJson(userRequestParams, Map.class);
                String fromCurrency = (String) currencyParams.get("fromCurrency");
                String toCurrency = (String) currencyParams.get("toCurrency");
                double amount = Double.parseDouble(currencyParams.get("amount").toString());
                result = tempClient.convertCurrency(fromCurrency, toCurrency, amount);
                break;

            case GET_CHINESE_RECIPES:
                params = gson.fromJson(userRequestParams, Map.class);
                String query = params.get("query");
                result = tempClient.getChineseRecipes(query);
                break;

            default:
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "未找到对应的接口方法");
        }

        return ResultUtils.success(result);
    }


}
