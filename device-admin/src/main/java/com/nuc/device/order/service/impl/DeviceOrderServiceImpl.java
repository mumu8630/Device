package com.nuc.device.order.service.impl;

import java.util.List;

import com.nuc.device.equipment.mapper.DeviceEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuc.device.order.mapper.DeviceOrderMapper;
import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.common.core.text.Convert;

/**
 * 订单信息Service业务层处理
 * 
 * @author mumu
 * @date 2024-04-26
 */
@Service
public class DeviceOrderServiceImpl implements IDeviceOrderService 
{
    @Autowired
    private DeviceOrderMapper deviceOrderMapper;
    @Autowired
    private DeviceEquipmentMapper deviceEquipmentMapper;

    /**
     * 查询订单信息
     * 
     * @param orderId 订单信息主键
     * @return 订单信息
     */
    @Override
    public DeviceOrder selectDeviceOrderByOrderId(Long orderId)
    {
        return deviceOrderMapper.selectDeviceOrderByOrderId(orderId);
    }

    /**
     * 查询订单信息列表
     * 
     * @param deviceOrder 订单信息
     * @return 订单信息
     */
    @Override
    public List<DeviceOrder> selectDeviceOrderList(DeviceOrder deviceOrder)
    {
        List<DeviceOrder> deviceOrders = deviceOrderMapper.selectDeviceOrderList(deviceOrder);
        deviceOrders.forEach(userDeviceOrder -> {
            userDeviceOrder.setTypeName(selectTypeNameByEquipmentId(userDeviceOrder.getEquipmentId()));
            userDeviceOrder.setEquipmentName(selectEquipmentnameByEquipmentId(userDeviceOrder.getEquipmentId()));
        });
        return deviceOrders;
    }

    /**
     * 新增订单信息
     * 
     * @param deviceOrder 订单信息
     * @return 结果
     */
    @Override
    public int insertDeviceOrder(DeviceOrder deviceOrder)
    {
        return deviceOrderMapper.insertDeviceOrder(deviceOrder);
    }

    /**
     * 修改订单信息
     * 
     * @param deviceOrder 订单信息
     * @return 结果
     */
    @Override
    public int updateDeviceOrder(DeviceOrder deviceOrder)
    {
        return deviceOrderMapper.updateDeviceOrder(deviceOrder);
    }

    /**
     * 批量删除订单信息
     * 
     * @param orderIds 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deleteDeviceOrderByOrderIds(String orderIds)
    {
        return deviceOrderMapper.deleteDeviceOrderByOrderIds(Convert.toStrArray(orderIds));
    }

    /**
     * 删除订单信息信息
     * 
     * @param orderId 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteDeviceOrderByOrderId(Long orderId)
    {
        return deviceOrderMapper.deleteDeviceOrderByOrderId(orderId);
    }

    private String selectEquipmentnameByEquipmentId(Long equipmentId) {
        return deviceEquipmentMapper.selectEquipmentNameByEquipmentId(equipmentId);
    }

    private String selectTypeNameByEquipmentId(Long equipmentId) {
        return deviceEquipmentMapper.selectTypeNameByEquipmentId(equipmentId);
    }
}
