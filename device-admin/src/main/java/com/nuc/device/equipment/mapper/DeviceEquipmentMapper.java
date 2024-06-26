package com.nuc.device.equipment.mapper;

import java.util.List;
import java.util.Map;

import com.nuc.device.equipment.domain.DeviceEquipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 设备信息Mapper接口
 * 
 * @author mumu
 * @date 2024-04-25
 */
@Mapper
public interface DeviceEquipmentMapper 
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
     * 删除设备信息
     * 
     * @param equipmentId 设备信息主键
     * @return 结果
     */
    public int deleteDeviceEquipmentByEquipmentId(Long equipmentId);

    /**
     * 批量删除设备信息
     * 
     * @param equipmentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceEquipmentByEquipmentIds(String[] equipmentIds);


    String selectEquipmentNameByEquipmentId(Long equipmentId);

    String selectTypeNameByEquipmentId(Long equipmentId);


    List<Map<String,Object>>  sumBorrowQuantity();

    List<Map<String,Object>> sumMaintenanceQuantity();

    List<Map<String,Object>> sumIdleQuantity();

    Integer sumAllBorrowQuantity();

    Integer sumAllIdleQuantity();

    Integer sumAllMaintenanceQuantity();

    Integer sumAllQuantity();

    int returnDeviceByEquipmentId(@Param("equipmentId") Long equipmentId, @Param("borrowNum") Integer borrowNum);

    String selectTypeNameByTypeId(Long typeId);
}
