package com.nuc.device.maintenance.mapper;

import java.util.List;
import com.nuc.device.maintenance.domain.DeviceMaintenance;
import com.nuc.device.maintenance.domain.MaintenanceChartDto;
import com.nuc.device.record.domain.DeviceBorrowRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备维护Mapper接口
 * 
 * @author mumu
 * @date 2024-05-07
 */
@Mapper
public interface DeviceMaintenanceMapper 
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
     * 删除设备维护
     * 
     * @param workId 设备维护主键
     * @return 结果
     */
    public int deleteDeviceMaintenanceByWorkId(Long workId);

    /**
     * 批量删除设备维护
     * 
     * @param workIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceMaintenanceByWorkIds(String[] workIds);

    Integer sumMaintenanceQuantity();

    Integer sumWorkQuantity();

    List<DeviceMaintenance> selectRecentWork();

    List<MaintenanceChartDto> selectLineMaintenanceChart();
}
