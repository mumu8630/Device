package com.nuc.device.equipment.service;

import java.util.List;
import com.nuc.device.equipment.domain.DeviceEquipment;

/**
 * 设备信息Service接口
 * 
 * @author mumu
 * @date 2024-04-25
 */
public interface IDeviceEquipmentService 
{
    /**
     * 查询设备信息
     * 
     * @param equipmentId 设备信息主键
     * @return 设备信息
     */
    public DeviceEquipment selectDeviceEquipmentByEquipmentId(Long equipmentId);

    /**
     * 查询设备信息列表
     * 
     * @param deviceEquipment 设备信息
     * @return 设备信息集合
     */
    public List<DeviceEquipment> selectDeviceEquipmentList(DeviceEquipment deviceEquipment);

    /**
     * 新增设备信息
     * 
     * @param deviceEquipment 设备信息
     * @return 结果
     */
    public int insertDeviceEquipment(DeviceEquipment deviceEquipment);

    /**
     * 修改设备信息
     * 
     * @param deviceEquipment 设备信息
     * @return 结果
     */
    public int updateDeviceEquipment(DeviceEquipment deviceEquipment);

    /**
     * 批量删除设备信息
     * 
     * @param equipmentIds 需要删除的设备信息主键集合
     * @return 结果
     */
    public int deleteDeviceEquipmentByEquipmentIds(String equipmentIds);

    /**
     * 删除设备信息信息
     * 
     * @param equipmentId 设备信息主键
     * @return 结果
     */
    public int deleteDeviceEquipmentByEquipmentId(Long equipmentId);

    /**
     * 借用设备
     *
     * @param deviceEquipment 设备信息
     * @param needQuantity 需要数量
     * @param needReason 需要原因
     * @return 结果
     */
    int borrowDevice(DeviceEquipment deviceEquipment, Integer needQuantity, String needReason);
}
