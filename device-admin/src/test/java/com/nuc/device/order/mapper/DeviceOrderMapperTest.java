package com.nuc.device.order.mapper;

import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.task.domin.BorrowDateTimes;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 订单信息Mapper接口
 * @author mumu
 * @date 2024/5/4 12:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DeviceOrderMapperTest {

    @Autowired
    private DeviceOrderMapper deviceOrderMapper;
    @Test
    void selectBorrowTimes() {
        List<BorrowDateTimes> borrowTimes = deviceOrderMapper.getBorrowTimes(1L);
        borrowTimes.forEach(System.out::println);
    }

    @Test
    void selectNewBorrowOrder(){
        System.out.println(deviceOrderMapper.selectNewBorrowOrder(1L));


    }
}