package com.nuc.device.device.controller;

import com.nuc.device.device.domin.DeviceEquipment;
import com.nuc.device.device.service.IDeviceEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 类描述
 *
 * @author mumu
 * @date 2024/4/24 11:21
 */
@RestController
@RequestMapping("/device")
public class DeviceEquipmentController {

    @Autowired
        IDeviceEquipmentService deviceEquipmentService;

    @GetMapping("/findAllDevice")
    public List<Map<String, Object>> findAllDevice() {
        List<DeviceEquipment> allDevice = deviceEquipmentService.findAllDevice();
        List<Map<String, Object>> chartData = Date2Echarts(allDevice);
        return chartData;
    }

    private List<Map<String, Object>> Date2Echarts(List<DeviceEquipment> allDevice) {
        List<Map<String, Object>> chartData = new ArrayList<>();
        for (DeviceEquipment equipment : allDevice) {
            // 提取需要的信息
            int borrowedQuantity = equipment.getBorrowedQuantity();
            String name = equipment.getName();

            // 创建适合 ECharts 使用的格式
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("value", borrowedQuantity);
            dataPoint.put("name", name);

            // 添加到列表中
            chartData.add(dataPoint);
        }
        return chartData;
    }
}
