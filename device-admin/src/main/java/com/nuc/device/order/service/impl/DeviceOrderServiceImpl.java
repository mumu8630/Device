package com.nuc.device.order.service.impl;

import java.util.Date;
import java.util.List;

import com.nuc.device.equipment.mapper.DeviceEquipmentMapper;
import com.nuc.device.record.domain.OrderSummary;
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

    @Override
    public Long findMinDeadLine(Long userId) {
        return deviceOrderMapper.selectMinDeadLine(userId);
    }

    /**
     * 查询用户借用数量
     *
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public OrderSummary sumBorrowQuantity(Long userId) {
        return deviceOrderMapper.sumBorrowQuantity(userId);
    }

    /**
     * 查询用户归还数量
     *
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public OrderSummary sumReturnQuantity(Long userId) {
        return deviceOrderMapper.sumReturnQuantity(userId);
    }

    /**
     * 查询用户逾期数量
     *
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public OrderSummary sumOverdueQuantity(Long userId) {
        return deviceOrderMapper.sumOverdueQuantity(userId);
    }

    /**
     * 查询用户即将逾期数量
     *
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public OrderSummary sumWillOverdueQuantity(Long userId) {
        return deviceOrderMapper.sumWillOverdueQuantity(userId);
    }

    private String selectTypeNameByEquipmentId(Long equipmentId) {
        return deviceEquipmentMapper.selectTypeNameByEquipmentId(equipmentId);
    }
}
