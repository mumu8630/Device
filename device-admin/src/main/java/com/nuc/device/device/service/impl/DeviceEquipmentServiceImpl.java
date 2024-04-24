package com.nuc.device.device.service.impl;

import com.nuc.device.device.domin.DeviceEquipment;
import com.nuc.device.device.mapper.DeviceEquipmentMapper;
import com.nuc.device.device.service.IDeviceEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 类描述
 *
 * @author mumu
 * @date 2024/4/24 11:21
 */
@Service
public class DeviceEquipmentServiceImpl implements IDeviceEquipmentService {

    @Autowired
    DeviceEquipmentMapper deviceEquipmentMapper;
    @Override
    public List<DeviceEquipment> findAllDevice() {
        return deviceEquipmentMapper.selectALL();
    }
}
