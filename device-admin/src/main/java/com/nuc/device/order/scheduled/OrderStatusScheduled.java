package com.nuc.device.order.scheduled;

import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.record.service.IDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 定时器 更新已逾期设备状态
 *
 * @author mumu
 * @date 2024/5/6 11:33
 */
@Component
public class OrderStatusScheduled {
    @Autowired
    IDeviceOrderService deviceOrderService;
    @Autowired
    IDeviceRecordService deviceRecordService;
    @PostConstruct
    public void init() {
        // 在服务器启动时执行一次
        //assert deviceOrderService != null : "deviceOrderService is null";
        updateOrderStatus();
    }
    @Scheduled(fixedRate = 3600000) // 每隔一个小时执行一次
    public void updateOrderStatus() {
        // TODO 更新已逾期设备状态
        //获取未归还列表
        List<DeviceOrder>  orderList = deviceOrderService.getStatusList();
        for (DeviceOrder order : orderList) {
            //获取当前时间
            long currentTime = System.currentTimeMillis();
            //获取订单结束时间
            long endTime = order.getDeadDate().getTime();
            //判断是否逾期
            if (currentTime > endTime) {
                //逾期
                order.setStatus("已逾期");
                deviceOrderService.updateDeviceOrder(order);
                deviceRecordService.updateRecordStatus(order.getOrderId());
            }
        }
    }

}
