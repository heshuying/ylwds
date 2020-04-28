package com.hailian.ylwmall.dto;

import lombok.Data;

@Data
public class ModuleReq {
    private Long id;

    /**
     * 专区名称
     */
    private String modName;

    /**
     * 专区描述
     */
    private String modDesc;

}
