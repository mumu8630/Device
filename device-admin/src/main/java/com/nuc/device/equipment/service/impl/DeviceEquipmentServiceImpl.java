package com.nuc.device.equipment.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuc.device.equipment.mapper.DeviceEquipmentMapper;
import com.nuc.device.equipment.domain.DeviceEquipment;
import com.nuc.device.equipment.service.IDeviceEquipmentService;
import com.nuc.device.common.core.text.Convert;

/**
 * 设备查询Service业务层处理
 * 
 * @author mumu
 * @date 2024-04-24
 */
@Service
public class DeviceEquipmentServiceImpl implements IDeviceEquipmentService 
{
    @Autowired
    private DeviceEquipmentMapper deviceEquipmentMapper;

    /**
     * 查询设备查询
     * 
     * @param equipmentId 设备查询主键
     * @return 设备查询
     */
    @Override
    public DeviceEquipment selectDeviceEquipmentByEquipmentId(Long equipmentId)
    {
        return deviceEquipmentMapper.selectDeviceEquipmentByEquipmentId(equipmentId);
    }

    /**
     * 查询设备查询列表
     * 
     * @param deviceEquipment 设备查询
     * @return 设备查询
     */
    @Override
    public List<DeviceEquipment> selectDeviceEquipmentList(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.selectDeviceEquipmentList(deviceEquipment);
    }

    /**
     * 新增设备查询
     * 
     * @param deviceEquipment 设备查询
     * @return 结果
     */
    @Override
    public int insertDeviceEquipment(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.insertDeviceEquipment(deviceEquipment);
    }

    /**
     * 修改设备查询
     * 
     * @param deviceEquipment 设备查询
     * @return 结果
     */
    @Override
    public int updateDeviceEquipment(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.updateDeviceEquipment(deviceEquipment);
    }

    /**
     * 批量删除设备查询
     * 
     * @param equipmentIds 需要删除的设备查询主键
     * @return 结果
     */
    @Override
    public int deleteDeviceEquipmentByEquipmentIds(String equipmentIds)
    {
        return deviceEquipmentMapper.deleteDeviceEquipmentByEquipmentIds(Convert.toStrArray(equipmentIds));
    }

    /**
     * 删除设备查询信息
     * 
     * @param equipmentId 设备查询主键
     * @return 结果
     */
    @Override
    public int deleteDeviceEquipmentByEquipmentId(Long equipmentId)
    {
        return deviceEquipmentMapper.deleteDeviceEquipmentByEquipmentId(equipmentId);
    }

    @Override
    public List<DeviceEquipment> findAllDevice() {
        return deviceEquipmentMapper.selectAllDevice();
    }
}
