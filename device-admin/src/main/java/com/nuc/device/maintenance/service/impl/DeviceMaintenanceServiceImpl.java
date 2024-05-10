package com.nuc.device.maintenance.service.impl;

import java.util.Date;
import java.util.List;

import com.nuc.device.equipment.domain.DeviceEquipment;
import com.nuc.device.equipment.service.IDeviceEquipmentService;
import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.record.service.IDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuc.device.maintenance.mapper.DeviceMaintenanceMapper;
import com.nuc.device.maintenance.domain.DeviceMaintenance;
import com.nuc.device.maintenance.service.IDeviceMaintenanceService;
import com.nuc.device.common.core.text.Convert;

import static com.nuc.device.common.utils.ShiroUtils.getSysUser;

/**
 * 设备维护Service业务层处理
 * 
 * @author mumu
 * @date 2024-05-07
 */
@Service
public class DeviceMaintenanceServiceImpl implements IDeviceMaintenanceService 
{
    @Autowired
    private DeviceMaintenanceMapper deviceMaintenanceMapper;
    @Autowired
    IDeviceOrderService deviceOrderService;
    @Autowired
    IDeviceRecordService deviceRecordService;
    @Autowired
    IDeviceEquipmentService deviceEquipmentService;

    /**
     * 查询设备维护
     * 
     * @param workId 设备维护主键
     * @return 设备维护
     */
    @Override
    public DeviceMaintenance selectDeviceMaintenanceByWorkId(Long workId)
    {
        return deviceMaintenanceMapper.selectDeviceMaintenanceByWorkId(workId);
    }

    /**
     * 查询设备维护列表
     * 
     * @param deviceMaintenance 设备维护
     * @return 设备维护
     */
    @Override
    public List<DeviceMaintenance> selectDeviceMaintenanceList(DeviceMaintenance deviceMaintenance)
    {
        return deviceMaintenanceMapper.selectDeviceMaintenanceList(deviceMaintenance);
    }

    /**
     * 新增设备维护
     * 
     * @param deviceMaintenance 设备维护
     * @return 结果
     */
    @Override
    public int insertDeviceMaintenance(DeviceMaintenance deviceMaintenance)
    {
        return deviceMaintenanceMapper.insertDeviceMaintenance(deviceMaintenance);
    }

    /**
     * 修改设备维护
     * 
     * @param deviceMaintenance 设备维护
     * @return 结果
     */
    @Override
    public int updateDeviceMaintenance(DeviceMaintenance deviceMaintenance)
    {
        return deviceMaintenanceMapper.updateDeviceMaintenance(deviceMaintenance);
    }

    /**
     * 批量删除设备维护
     * 
     * @param workIds 需要删除的设备维护主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMaintenanceByWorkIds(String workIds)
    {
        return deviceMaintenanceMapper.deleteDeviceMaintenanceByWorkIds(Convert.toStrArray(workIds));
    }

    /**
     * 删除设备维护信息
     * 
     * @param workId 设备维护主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMaintenanceByWorkId(Long workId)
    {
        return deviceMaintenanceMapper.deleteDeviceMaintenanceByWorkId(workId);
    }

    /**
     * 审核通过之后的逻辑处理
     * 状态更新 三张表的数据
     * 库存更新 主要更新主表 equipment表
     * 将 归还数量 ---> 借出数量-  库存数量+
     * 将 维修数量--->维修数量+
     * @param deviceMaintenance
     * @return
     */
    @Override
    public int updateSolve(DeviceMaintenance deviceMaintenance) {
        //前端数据处理
        deviceMaintenance.setMaintenanceStatus("已处理");
        deviceMaintenance.setManageDate(new Date());
        if (deviceMaintenance.getManageName() == null||deviceMaintenance.getManageName().equals("")){
            deviceMaintenance.setManageName(getSysUser().getLoginName());
        }
        DeviceOrder order = deviceOrderService.selectDeviceOrderByOrderId(deviceMaintenance.getOrderId());
        order.setStatus("已审核");
        deviceOrderService.updateDeviceOrder(order);
        deviceRecordService.updateRecordStatus(deviceMaintenance.getOrderId(),"已审核");
        deviceMaintenance.setEquipmentId(order.getEquipmentId());
        updateDeviceMaintenance(deviceMaintenance);

        //后端库存数据处理
        DeviceEquipment deviceEquipment = deviceEquipmentService.selectDeviceEquipmentByEquipmentId(deviceMaintenance.getEquipmentId());
        int returnNum = order.getBorrowNum() - deviceMaintenance.getMaintenanceNum();
        deviceEquipment.setBorrowedQuantity(deviceEquipment.getBorrowedQuantity()-returnNum);
        deviceEquipment.setIdleQuantity(deviceEquipment.getIdleQuantity()+returnNum);
        deviceEquipment.setMaintenanceQuantity(deviceEquipment.getMaintenanceQuantity()+deviceMaintenance.getMaintenanceNum());
        return deviceEquipmentService.updateDeviceEquipment(deviceEquipment);
    }
}
