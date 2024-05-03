package com.nuc.device.order.mapper;

import java.util.Date;
import java.util.List;
import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.record.domain.OrderSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单信息Mapper接口
 * 
 * @author mumu
 * @date 2024-04-26
 */
@Mapper
public interface DeviceOrderMapper 
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
     * 删除订单信息
     * 
     * @param orderId 订单信息主键
     * @return 结果
     */
    public int deleteDeviceOrderByOrderId(Long orderId);

    /**
     * 批量删除订单信息
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceOrderByOrderIds(String[] orderIds);

    Long selectMinDeadLine(Long userId);

    OrderSummary sumBorrowQuantity(Long userId);

    OrderSummary sumReturnQuantity(Long userId);

    OrderSummary sumOverdueQuantity(Long userId);

    OrderSummary sumWillOverdueQuantity(Long userId);
}
