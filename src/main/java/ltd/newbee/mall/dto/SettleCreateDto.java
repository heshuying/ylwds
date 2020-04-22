package ltd.newbee.mall.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by 19012964 on 2020/4/21.
 */
@Data
public class SettleCreateDto {
    /**
     * 待结算列表
     */
    private List<UnSettleListDto> unSettleList;
    private Long supplierId;
    private String supplierName;
    private Date startDate;
    private Date endDate;
}
