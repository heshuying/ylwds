package ltd.newbee.mall.dto;

import lombok.Data;
import ltd.newbee.mall.entity.TbModuleDetail;

@Data
public class ModuleDetailRes extends TbModuleDetail {

    private String goodsName;

    /**
     * 平台利润
     */
    private String profit;

    /**
     * 供货价
     */
    private String sellingPrice;

}
