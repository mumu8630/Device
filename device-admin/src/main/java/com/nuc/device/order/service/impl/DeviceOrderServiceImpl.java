package com.nuc.device.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nuc.device.common.utils.DateUtils;
import com.nuc.device.equipment.mapper.DeviceEquipmentMapper;
import com.nuc.device.record.domain.OrderSummary;
import com.nuc.device.record.service.IDeviceRecordService;
import com.nuc.device.task.domin.BorrowDateTimes;
import com.nuc.device.task.exception.DBException;
import com.nuc.device.task.exception.RedisOperationException;
import com.nuc.device.task.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuc.device.order.mapper.DeviceOrderMapper;
import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单信息Service业务层处理
 * 
 * @author mumu
 * @date 2024-04-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceOrderServiceImpl implements IDeviceOrderService
{
    @Autowired
    private DeviceOrderMapper deviceOrderMapper;
    @Autowired
    private DeviceEquipmentMapper deviceEquipmentMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    IDeviceRecordService deviceRecordService;

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

    @Override
    public  List<BorrowDateTimes> selectBorrowTimes(Long userId) {
        return deviceOrderMapper.getBorrowTimes(userId);
    }

    /**
     * 用户最新借用
     * @param userId
     * @return
     */
    @Override
    public DeviceOrder selectNewBorrowOrder(Long userId) {
        Long orderId = deviceOrderMapper.selectNewBorrowOrder(userId);
        DeviceOrder order = deviceOrderMapper.selectDeviceOrderByOrderId(orderId);
        return  order;
    }

    @Override
    public DeviceOrder initOrder(Long userId) {
        DeviceOrder deviceOrder = new DeviceOrder();
        deviceOrder.setUserId(userId);
        deviceOrder.setEquipmentId(100L);
        deviceOrder.setBorrowDate(new Date());
        deviceOrder.setStatus("未归还");
        deviceOrder.setReason("初始化");
        deviceOrder.setEquipmentName("初始化令牌");
        deviceOrder.setDeadDate(DateUtils.addDays(new Date(), 365));
        deviceOrderMapper.insertDeviceOrder(deviceOrder);
        return deviceOrder;
    }

    /**
     * 归还设备
     * 订单和历史库 更新
     * 总设备库的 借出数量- 空闲数量+
     * 缓存 借用排行不修改 库存排行修改
     * @param orderIds
     * @return
     */
    @Override
    public int returnDeviceByOrderIds(String orderIds) {
        String idleKey = "device:hot:idle";
        //根据ids查询设备
        String[] orderIdsArr = Convert.toStrArray(orderIds);
        try {
            //不存在key
            if (!redisUtil.hasKey(idleKey)) {
                throw new RedisOperationException("key" + idleKey + "不存在");
            }
            for (String orderId : orderIdsArr) {
                DeviceOrder order = deviceOrderMapper.selectDeviceOrderByOrderId(Long.valueOf(orderId));
                //更新设备缓存
                Double zincrby = redisUtil.zincrby(idleKey, order.getEquipmentId(), order.getBorrowNum());
                if (zincrby == null || zincrby < 0) {
                    throw new RedisOperationException("归还设备-->redis增加数据【失败】");
                }
                //更新订单库
                int res = deviceOrderMapper.returnDeviceByOrderId(Long.valueOf(orderId));
                if (res < 0) {
                   throw new DBException("归还设备-->订单数据库更新数据【失败】");
                }
                //更新历史库
                int i = deviceRecordService.returnDeviceByOrderId(Long.valueOf(orderId));
                if (i < 0) {
                    throw new DBException("归还设备-->历史数据库更新数据【失败】");
                }
                //更新总设备库 借出数量- 空闲数量+
                int j = deviceEquipmentMapper.returnDeviceByEquipmentId(order.getEquipmentId(), order.getBorrowNum());
                if (j < 0) {
                    throw new DBException("归还设备-->设备数据库更新数据【失败】");
                }
                //更新成功
                return 1;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 续借
     * @param deviceOrder
     * @return
     */
    @Override
    public int renewOrder(DeviceOrder deviceOrder) {
        return deviceOrderMapper.updateDeviceOrder(deviceOrder);
    }

    private String selectTypeNameByEquipmentId(Long equipmentId) {
        return deviceEquipmentMapper.selectTypeNameByEquipmentId(equipmentId);
    }
}
