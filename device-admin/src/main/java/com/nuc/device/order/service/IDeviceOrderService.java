package com.nuc.device.order.service;

import java.util.List;
import com.nuc.device.order.domain.DeviceOrder;

/**
 * 订单信息Service接口
 * 
 * @author mumu
 * @date 2024-04-26
 */
public interface IDeviceOrderService 
{
    /**
     * 查询订单信息
     * 
     * @param orderId 订单信息主键
     * @return 订单信息
     */
    public DeviceOrder selectDeviceOrderByOrderId(Long orderId);

    /**
     * 查询订单信息列表
     * 
     * @param deviceOrder 订单信息
     * @return 订单信息集合
     */
    public List<DeviceOrder> selectDeviceOrderList(DeviceOrder deviceOrder);

    /**
     * 新增订单信息
     * 
     * @param deviceOrder 订单信息
     * @return 结果
     */
    public int insertDeviceOrder(DeviceOrder deviceOrder);

    /**
     * 修改订单信息
     * 
     * @param deviceOrder 订单信息
     * @return 结果
     */
    public int updateDeviceOrder(DeviceOrder deviceOrder);

    /**
     * 批量删除订单信息
     * 
     * @param orderIds 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deleteDeviceOrderByOrderIds(String orderIds);

    /**
     * 删除订单信息信息
     * 
     * @param orderId 订单信息主键
     * @return 结果
     */
    public int deleteDeviceOrderByOrderId(Long orderId);

}