package com.myapi.backend.service;

import com.myapi.myapicommon.model.entity.InterfaceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // 加载Spring上下文
public class InterfaceInfoServiceTest {

    @Autowired
    private InterfaceInfoService interfaceInfoService;  // 注入Service

    @Test
    public void testInsertAndSelectIsAsync() {
        // Step 1: 创建一个新的 InterfaceInfo 对象
        InterfaceInfo newInterface = new InterfaceInfo();
        newInterface.setName("Test Interface");
        newInterface.setUrl("/test/interface");
        newInterface.setMethod("GET");  // 为 method 字段提供值
        newInterface.setIsAsync(true);  // 设置isAsync为true
        newInterface.setStatus(1); // 假设 status 字段为 byte 类型
        newInterface.setUserId(123L);    // 设置 userId，假设为 Long 类型
        newInterface.setIsDelete(0); // 假设 isDelete 字段为 byte 类型，表示逻辑删除状态

        // Step 2: 插入记录到数据库
        boolean insertResult = interfaceInfoService.save(newInterface);
        assertTrue(insertResult, "插入操作应成功");

        // Step 3: 通过ID查询插入的数据
        InterfaceInfo fetchedInterface = interfaceInfoService.getById(newInterface.getId());
        assertNotNull(fetchedInterface, "查询的记录应存在");
        assertTrue(fetchedInterface.getIsAsync(), "isAsync 字段应为 true");

        // Step 4: 更新 isAsync 字段为 false
        fetchedInterface.setIsAsync(false);
        boolean updateResult = interfaceInfoService.updateById(fetchedInterface);
        assertTrue(updateResult, "更新操作应成功");

        // Step 5: 再次查询，验证更新后的值
        InterfaceInfo updatedInterface = interfaceInfoService.getById(fetchedInterface.getId());
        assertNotNull(updatedInterface, "查询的记录应存在");
        assertFalse(updatedInterface.getIsAsync(), "isAsync 字段应为 false");
    }
}
