package com.nuc.device.equipment.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.record.domain.DeviceBorrowRecord;
import com.nuc.device.record.service.IDeviceRecordService;
import com.nuc.device.task.exception.RedisOperationException;
import com.nuc.device.task.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuc.device.equipment.mapper.DeviceEquipmentMapper;
import com.nuc.device.equipment.domain.DeviceEquipment;
import com.nuc.device.equipment.service.IDeviceEquipmentService;
import com.nuc.device.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import static com.nuc.device.common.utils.ShiroUtils.getSysUser;

/**
 * 设备信息Service业务层处理
 * 
 * @author mumu
 * @date 2024-04-25
 */
@Service
public class DeviceEquipmentServiceImpl implements IDeviceEquipmentService 
{
    @Autowired
    private DeviceEquipmentMapper deviceEquipmentMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    IDeviceOrderService deviceOrderService;
    @Autowired
    IDeviceRecordService  deviceRecordService;

    /**
     * 查询设备信息
     * 
     * @param equipmentId 设备信息主键
     * @return 设备信息
     */
    @Override
    public DeviceEquipment selectDeviceEquipmentByEquipmentId(Long equipmentId)
    {
        return deviceEquipmentMapper.selectDeviceEquipmentByEquipmentId(equipmentId);
    }

    /**
     * 查询设备信息列表
     * 
     * @param deviceEquipment 设备信息
     * @return 设备信息
     */
    @Override
    public List<DeviceEquipment> selectDeviceEquipmentList(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.selectDeviceEquipmentList(deviceEquipment);
    }

    /**
     * 新增设备信息
     * 
     * @param deviceEquipment 设备信息
     * @return 结果
     */
    @Override
    public int insertDeviceEquipment(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.insertDeviceEquipment(deviceEquipment);
    }

    /**
     * 修改设备信息
     * 
     * @param deviceEquipment 设备信息
     * @return 结果
     */
    @Override
    public int updateDeviceEquipment(DeviceEquipment deviceEquipment)
    {
        return deviceEquipmentMapper.updateDeviceEquipment(deviceEquipment);
    }

    /**
     * 批量删除设备信息
     * 
     * @param equipmentIds 需要删除的设备信息主键
     * @return 结果
     */
    @Override
    public int deleteDeviceEquipmentByEquipmentIds(String equipmentIds)
    {
        return deviceEquipmentMapper.deleteDeviceEquipmentByEquipmentIds(Convert.toStrArray(equipmentIds));
    }

    /**
     * 删除设备信息信息
     * 
     * @param equipmentId 设备信息主键
     * @return 结果
     */
    @Override
    public int deleteDeviceEquipmentByEquipmentId(Long equipmentId)
    {
        return deviceEquipmentMapper.deleteDeviceEquipmentByEquipmentId(equipmentId);
    }

    /**
     * 借用设备
     *
     * @param deviceEquipment 设备信息
     * @param needQuantity 需要数量
     * @param needReason 需要原因
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int borrowDevice(DeviceEquipment deviceEquipment, Integer needQuantity, String needReason,Date deadDate) {
        //扣缓存
        String idleKey = "device:hot:idle";
        String borrowKey = "device:hot:borrow";
        try {
            if (!redisUtil.hasKey(idleKey)) {
                throw new RedisOperationException("key" + idleKey + "不存在");
            }
            if (!redisUtil.hasKey(borrowKey)) {
                throw new RedisOperationException("key" + borrowKey + "不存在");
            }
            if (redisUtil.zGetScore(idleKey, deviceEquipment.getEquipmentId()) < needQuantity) {
                throw new RedisOperationException("redis中" + deviceEquipment.getName() + "设备闲置数量不足");
            }
            //扣缓存
            Double zincrby = redisUtil.zincrby(idleKey, deviceEquipment.getEquipmentId(), -needQuantity);
            if (zincrby < 0) {
                return 0;
            }
            //扣数据库
            DeviceEquipment device = deviceEquipmentMapper.selectDeviceEquipmentByEquipmentId(deviceEquipment.getEquipmentId());
            if (device.getIdleQuantity() < needQuantity) {
                return 0;
            }
            Integer newBorrowQuantity = device.getBorrowedQuantity() + needQuantity;
            Integer newIdleQuantity = device.getIdleQuantity() - needQuantity;
            device.setBorrowedQuantity(newBorrowQuantity);
            device.setIdleQuantity(newIdleQuantity);
            int i = deviceEquipmentMapper.updateDeviceEquipment(device);
            //    加订单
            DeviceOrder deviceOrder = new DeviceOrder();
            deviceOrder.setUserId(getSysUser().getUserId());
            deviceOrder.setEquipmentId(deviceEquipment.getEquipmentId());
            deviceOrder.setBorrowDate(new Date());
            deviceOrder.setDeadDate(deadDate);
            deviceOrder.setBorrowNum(needQuantity);
            deviceOrder.setReason(needReason);
            deviceOrder.setEquipmentName(deviceEquipment.getName());
            deviceOrder.setStatus("未归还");
            int j = deviceOrderService.insertDeviceOrder(deviceOrder);
            if (j<0){
               return 0;
            }
            Long orderId = deviceOrder.getOrderId();
            //加历史记录表
            DeviceBorrowRecord deviceBorrowRecord = new DeviceBorrowRecord();
            deviceBorrowRecord.setOrderId(orderId);
            deviceBorrowRecord.setEquipmentId(device.getEquipmentId());
            deviceBorrowRecord.setEquipmentName(device.getName());
            deviceBorrowRecord.setBorrowNum(needQuantity);
            deviceBorrowRecord.setBorrowDate(deviceOrder.getBorrowDate());
            deviceBorrowRecord.setBorrowUser(getSysUser().getLoginName());
            deviceBorrowRecord.setBorrowReason(needReason);
            deviceBorrowRecord.setBorrowStatus("未归还");
            //    设置归还时间
            deviceBorrowRecord.setDeadLine(deadDate);
            int x = deviceRecordService.addRecord(deviceBorrowRecord);
            //    加缓存
            redisUtil.zincrby(borrowKey, deviceEquipment.getEquipmentId(), needQuantity);
            return i * j * x;

        } catch (RedisOperationException e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 借用数量
     * @return
     */
    @Override
    public List<Map<String,Object>> sumBorrowQuantity() {
        return  deviceEquipmentMapper.sumBorrowQuantity();
    }

    /**
     * 维修数量
     * @return
     */
    @Override
    public List<Map<String,Object>> sumMaintenanceQuantity() {
        return deviceEquipmentMapper.sumMaintenanceQuantity();
    }

    /**
     * 闲置数量
     * @return
     */
    @Override
    public List<Map<String,Object>> sumIdleQuantity() {
        return deviceEquipmentMapper.sumIdleQuantity();
    }

    @Override
    public Integer selectBorrowQuantity() {
        return deviceEquipmentMapper.sumAllBorrowQuantity();
    }

    @Override
    public Integer selectIdleQuantity() {
        return deviceEquipmentMapper.sumAllIdleQuantity();
    }

    @Override
    public Integer selectMaintenanceQuantity() {
        return deviceEquipmentMapper.sumAllMaintenanceQuantity();
    }

    @Override
    public Integer selectTotalQuantity() {
        return deviceEquipmentMapper.sumAllQuantity();
    }


}
