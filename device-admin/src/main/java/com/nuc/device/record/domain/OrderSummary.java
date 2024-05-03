package com.nuc.device.record.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于存储workspace区传送的数据 订单数 和设备数
 *
 * @author mumu
 * @date 2024/5/3 22:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummary {
    private Integer orderCount;
    private Integer deviceCount;
}
