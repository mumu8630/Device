package com.nuc.device.maintenance.service;

import java.util.List;
import com.nuc.device.maintenance.domain.DeviceMaintenance;

/**
 * 设备维护Service接口
 * 
 * @author mumu
 * @date 2024-05-07
 */
public interface IDeviceMaintenanceService 
{
    /**
     * 查询设备维护
     * 
     * @param workId 设备维护主键
     * @return 设备维护
     */
    public DeviceMaintenance selectDeviceMaintenanceByWorkId(Long workId);

    /**
     * 查询设备维护列表
     * 
     * @param deviceMaintenance 设备维护
     * @return 设备维护集合
     */
    public List<DeviceMaintenance> selectDeviceMaintenanceList(DeviceMaintenance deviceMaintenance);

    /**
     * 新增设备维护
     * 
     * @param deviceMaintenance 设备维护
     * @return 结果
     */
    public int insertDeviceMaintenance(DeviceMaintenance deviceMaintenance);

    /**
     * 修改设备维护
     * 
     * @param deviceMaintenance 设备维护
     * @return 结果
     */
    public int updateDeviceMaintenance(DeviceMaintenance deviceMaintenance);

    /**
     * 批量删除设备维护
     * 
     * @param workIds 需要删除的设备维护主键集合
     * @return 结果
     */
    public int deleteDeviceMaintenanceByWorkIds(String workIds);

    /**
     * 删除设备维护信息
     * 
     * @param workId 设备维护主键
     * @return 结果
     */
    public int deleteDeviceMaintenanceByWorkId(Long workId);

    int updateSolve(DeviceMaintenance deviceMaintenance);
}
