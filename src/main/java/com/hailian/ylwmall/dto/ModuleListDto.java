package com.hailian.ylwmall.dto;

import lombok.Data;
import com.hailian.ylwmall.entity.TbModuleDetail;

import java.util.List;

/**
 * Created by 19012964 on 2020/4/23.
 */
@Data
public class ModuleListDto {
    private String modKey;
    private String modName;
    private String modDesc;
    private ModuleDetailRes headProd;
    private List<ModuleDetailRes> list;
}
