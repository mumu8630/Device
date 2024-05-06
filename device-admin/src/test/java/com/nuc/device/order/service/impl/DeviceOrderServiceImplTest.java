package com.nuc.device.order.service.impl;

import com.nuc.device.order.mapper.DeviceOrderMapper;
import com.nuc.device.record.domain.OrderSummary;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mumu
 * @date 2024/5/5 15:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DeviceOrderServiceImplTest {

    @Autowired
    DeviceOrderMapper deviceOrderMapper;
    @Test
    void sumWillOverdueQuantity() {
        OrderSummary orderSummary = deviceOrderMapper.sumWillOverdueQuantity(1L);
        String orderListStr = orderSummary.getOrderList();
        List<Long> orderList = Arrays.stream(orderListStr.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        System.out.println(orderList);
    }
}