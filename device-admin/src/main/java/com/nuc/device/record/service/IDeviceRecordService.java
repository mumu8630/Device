package com.nuc.device.record.service;

import com.nuc.device.record.domain.DeviceBorrowRecord;

import java.util.List;

/**
 * 借用记录的服务接口
 * @author mumu
 * @date 2024/5/1 22:04
 */
public interface IDeviceRecordService {

    List<DeviceBorrowRecord> findRecordList();
    int addRecord(DeviceBorrowRecord record);
    List<DeviceBorrowRecord> findRecentRecordList();

    int returnDeviceByOrderId(Long orderId);

    int updateRecordStatus(Long orderId, String status);

    /**
     * 查询历史借用列表
     *
     * @param deviceBorrowRecord 历史借用
     * @return 历史借用集合
     */
    public List<DeviceBorrowRecord> selectDeviceBorrowRecordList(DeviceBorrowRecord deviceBorrowRecord);

    int updateDeviceBorrowRecord(DeviceBorrowRecord record);
}
