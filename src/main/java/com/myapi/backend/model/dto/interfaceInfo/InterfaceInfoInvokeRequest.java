package com.myapi.backend.model.dto.interfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 请求参数
     */
    private String userRequestParams;

    private String name;

    private static final long serialVersionUID = 1L;

}