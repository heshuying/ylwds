package ltd.newbee.mall.entity.order;

import lombok.Data;

@Data
public class CommonResult {

    private String code;

    private String msg;

    public CommonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
