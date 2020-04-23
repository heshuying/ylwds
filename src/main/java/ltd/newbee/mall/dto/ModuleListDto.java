package ltd.newbee.mall.dto;

import lombok.Data;
import ltd.newbee.mall.entity.TbModuleDetail;

import java.util.List;

/**
 * Created by 19012964 on 2020/4/23.
 */
@Data
public class ModuleListDto {
    private String modKey;
    private String modName;
    private String modDesc;

    List<TbModuleDetail> list;
}
