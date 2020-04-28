package com.hailian.ylwmall.dto;

import lombok.Data;

/**
 * Created by 01440590 on 2019/1/22.
 * 分润账号集
 */

@Data
public class RoyaltyInfo {
    private String payee_identity_type;//分账表识类型，默认1
    private String payee_member_id;//分账表识
    private String payee_account_no;//分账收款方账号
    private String amount;//分账金额
}
