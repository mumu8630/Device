package com.nuc.device.equipment.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuc.device.equipment.mapper.DeviceEquipmentMapper;
import com.nuc.device.equipment.domain.DeviceEquipment;
import com.nuc.device.equipment.service.IDeviceEquipmentService;
import com.nuc.device.common.core.text.Convert;

/**
 * 设备信息Service业务层处理
 * 
 * @author mumu
 * @date 2024-04-25
 */
@Service
public class DeviceEquipmentServiceImpl implements IDeviceEquipmentService 
{
    @Autowired
    private DeviceEquipmentMapper deviceEquipmentMapper;

    /**
     * 查询设备信息
     * 
     * @param equipmentId 设备信息主键
     * @return 设备信息
     */
    @Override
    public DeviceEquipment selectDeviceEquipmentByEquipmentId(Long equipmentId)
    {
        return deviceEquipmentMapper.selectDeviceEquipmentByEquipmentId(equipmentId);
    }

    /**
     * 查询设备信息列表
     * 
     * @param deviceEquipment 设备信息
     * @return 设备信息
     */
    @Override
    public List<DeviceEquipment> selectDeviceEquipmentList(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.selectDeviceEquipmentList(deviceEquipment);
    }

    /**
     * 新增设备信息
     * 
     * @param deviceEquipment 设备信息
     * @return 结果
     */
    @Override
    public int insertDeviceEquipment(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.insertDeviceEquipment(deviceEquipment);
    }

    /**
     * 修改设备信息
     * 
     * @param deviceEquipment 设备信息
     * @return 结果
     */
    @Override
    public int updateDeviceEquipment(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.updateDeviceEquipment(deviceEquipment);
    }

    /**
     * 批量删除设备信息
     * 
     * @param equipmentIds 需要删除的设备信息主键
     * @return 结果
     */
    @Override
    public int deleteDeviceEquipmentByEquipmentIds(String equipmentIds)
    {
        return deviceEquipmentMapper.deleteDeviceEquipmentByEquipmentIds(Convert.toStrArray(equipmentIds));
    }

    /**
     * 删除设备信息信息
     * 
     * @param equipmentId 设备信息主键
     * @return 结果
     */
    @Override
    public int deleteDeviceEquipmentByEquipmentId(Long equipmentId)
    {
        return deviceEquipmentMapper.deleteDeviceEquipmentByEquipmentId(equipmentId);
    }

    @Override
    public List<DeviceEquipment> findHotDevice() {
        return deviceEquipmentMapper.selectHotDevice();
    }
}
