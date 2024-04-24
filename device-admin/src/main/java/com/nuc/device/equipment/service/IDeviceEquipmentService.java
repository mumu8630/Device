package com.nuc.device.equipment.service;

import java.util.List;
import com.nuc.device.equipment.domain.DeviceEquipment;

/**
 * 设备查询Service接口
 * 
 * @author mumu
 * @date 2024-04-24
 */
public interface IDeviceEquipmentService 
{
    /**
     * 查询设备查询
     * 
     * @param equipmentId 设备查询主键
     * @return 设备查询
     */
    public DeviceEquipment selectDeviceEquipmentByEquipmentId(Long equipmentId);

    /**
     * 查询设备查询列表
     * 
     * @param deviceEquipment 设备查询
     * @return 设备查询集合
     */
    public List<DeviceEquipment> selectDeviceEquipmentList(DeviceEquipment deviceEquipment);

    /**
     * 新增设备查询
     * 
     * @param deviceEquipment 设备查询
     * @return 结果
     */
    public int insertDeviceEquipment(DeviceEquipment deviceEquipment);

    /**
     * 修改设备查询
     * 
     * @param deviceEquipment 设备查询
     * @return 结果
     */
    public int updateDeviceEquipment(DeviceEquipment deviceEquipment);

    /**
     * 批量删除设备查询
     * 
     * @param equipmentIds 需要删除的设备查询主键集合
     * @return 结果
     */
    public int deleteDeviceEquipmentByEquipmentIds(String equipmentIds);

    /**
     * 删除设备查询信息
     * 
     * @param equipmentId 设备查询主键
     * @return 结果
     */
    public int deleteDeviceEquipmentByEquipmentId(Long equipmentId);

    List<DeviceEquipment> findAllDevice();
}
